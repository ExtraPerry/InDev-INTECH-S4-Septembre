const fetch = (...args) =>
import('node-fetch').then(({ default: fetch }) => fetch(...args));

//declarations relatives a discord.js
const {Client, GatewayIntentBits} = require("discord.js");
const client = new Client({
    intents: [
        GatewayIntentBits.Guilds,
        GatewayIntentBits.GuildMessages,
        GatewayIntentBits.MessageContent
    ]
});
const discordModals = require('discord-modals'); 
// discord-modals a besoin de connaître le client
discordModals(client); 
// renseignement du token du bot
client.login("MTAzNDA4NDUzNTQxNzE3NjA2NA.G4E-c2.hSoIZosWkU70ns5X6PDXof0uy9B_BQ9vaJoS3A");

// au démarrage du bot
client.on("ready", () => {
    console.log("bot opérationnel"); 
});
// exemple de fonnction qui fait une requette GET
function exempleGet(){
    fetch('http://localhost:8080/messages')
    .then(res => res.text())
    .then(body => console.log(body));
}
// exemplpe de fonction qui fait une requette POST
function exemplePost(){
    fetch('http://localhost:8080/addMessage', { method: 'POST', headers:{'content-type': 'application/json'}, body: JSON.stringify('a=1') })
    .then(res => res.text()) // attend une reponse json
    .then(text => console.log(text));
}

client.on("messageCreate", message =>{
    // sort de la fonction si l'auteur du message est le bot
    if (message.author.bot) return;

    switch (message.content) {
        case 'ping':
            message.reply("pong");
            break;
        case 'help':
            message.channel.send("Commandes disponibles :\n ping : repond 'pong'");
            break;
        case 'hello':
            exempleGet();
            break;
        case 'post':
            exemplePost();
            break;
    }
    console.log(message.content);

});
