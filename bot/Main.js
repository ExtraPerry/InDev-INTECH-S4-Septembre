/**
 * In'Dev - Console log types.
 *      - S >>> Setup.
 *      - R >>> Response.
 *      - W >>> Warning.
 *      - E >>> Error.
 */

//imports
const { Client, GatewayIntentBits, Collection, Events } = require("discord.js");
const fs = require("fs");
const path = require("path");

const { token, clientId, guildId } = require('./config.json');
const commandsPath = path.join(__dirname, 'commands');
const commandFiles = fs.readdirSync(commandsPath).filter(file => file.endsWith('.js'));
const deployCommands = require("./Loaders/deployCommands");

//Client init.
const client = new Client({     //Assign intents to the client as well as intialise it.
    intents: [
        GatewayIntentBits.Guilds,
        GatewayIntentBits.GuildMessages,
        GatewayIntentBits.MessageContent,
        GatewayIntentBits.GuildPresences
    ]
});

client.login(token);        //Assign the token to the client for login.

//Deploy slash commands onto the server.
deployCommands(clientId, guildId, token, commandFiles);
console.log(`In'DevS >>> Slash command succesfully deployed to server.`)

//Commands init for the client (bot).
client.commands = new Collection();     //Create a collection to store all the commands inside of the client object.

for (const file of commandFiles) {
    const filePath = path.join(commandsPath, file);
    const command = require(filePath);

    if ('data' in command && 'execute' in command) {    //Add command to collection if it has the needed properties.
        console.log(`In'DevS >>> Attempting to load command module ${file} ...`);
        client.commands.set(command.data.name, command);
        console.log(`In'DevS >>> The command module ${file} has been loaded succesfully.`);
    }else{      //If the command doesn't have the needed properties ignore.
        console.log(`In'DevW >>> The command module ${file} at ${filePath} is missing "data" or "execute" properties !`);
    }
}

//Client start.
client.on("ready", () => {      //Startup log message.
    console.log(`In'DevS >>> ${client.user.tag} is now Online !`);
})

//Client interaction response.
client.on(Events.InteractionCreate, async interaction => {
	if (!interaction.isChatInputCommand()) return;      //If the interaction is a chat input ignore.

	const command = interaction.client.commands.get(interaction.commandName);   //Match interation command to client command, null (false) if no match is found.

	if (!command) {     //Check if the command is valid.
		console.log(`In'DevE >>> No command matching ${interaction.commandName} was found for ${interaction.user.id}.`);
		return;
	}

	try {       //Attempt to execute the command.
		await command.execute(interaction, client);
        console.log(`In'DevR >>> Replied to ${interaction.user.tag} for "${interaction.commandName}" command.`);
	} catch (error) {       //Return an error message if something went wrong.
		console.error(error);
		await interaction.reply({ content: 'There was an error while executing this command!', ephemeral: true });
	}
});
