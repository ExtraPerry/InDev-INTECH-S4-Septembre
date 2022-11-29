const { SlashCommandBuilder } = require('discord.js');

module.exports = {
	data: new SlashCommandBuilder()
		.setName('ping')
		.setDescription('Replies with latency in ms.'),

	async execute(interaction) {
		await interaction.reply({ content: `Latency is ${interaction.client.ws.ping} ms.` , ephemeral: true });
	},
};