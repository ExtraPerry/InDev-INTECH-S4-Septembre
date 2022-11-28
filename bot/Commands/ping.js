const { SlashCommandBuilder } = require('discord.js');

module.exports = {
	data: new SlashCommandBuilder()
		.setName('ping')
		.setDescription('Replies with Pong & latency in ms.'),

	async execute(interaction, client) {
		await interaction.reply(`Pong > ${client.ws.ping} ms.`);
	},
};