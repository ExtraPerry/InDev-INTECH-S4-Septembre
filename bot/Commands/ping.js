const Discord = require("discord.js");

module.exports = {

	name: "ping",
	description: "Shows latency between the user and the bot.",
	permission: "None",
	dm: true,
	
	async run(client, message) {
		
		await message.reply(`Ping : ${client.ws.ping} !`);
	}
	
}