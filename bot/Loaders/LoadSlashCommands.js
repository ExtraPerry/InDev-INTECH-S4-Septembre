const Discord = require("discord.js");

module.exports = async client => {
	
	let commands = [];
	
	bot.commands.forEach(command => {
		
		let slashcommand = new Discord.SlashCommandBuilder()										//Generate a "Slash" command.
		.setName(command.name)																		//It's Name.
		.setDescription(command.description)														//It's Description.
		.setDMPermission(command.dm)																//Does it work in DM's ?
		.setDefaultMemberPermissions(command.permission === "None" ? null : command.permission)		//Who can use this command ?
		
		
		if(command.options?.length >= 1) {
			for(let i = 0; i < command.options.length; i++) {
				slashcommand[`add${command.options[i].type.slice(0 ,1).toLowerCase() + command.options[i].type.slice(1, command.options[i].type.length)}Option`](option => option.setName(command.options[i]).description(command.options[i].description).setRequired(command.options[i].required)))
			}
		}
		
		await command.push(slashcommand)
		
	}
}

//Y'a un truc avec les acolade qui bug . . . a voir plus tard :/