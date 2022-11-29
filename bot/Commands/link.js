const { SlashCommandBuilder } = require('discord.js');

module.exports = {
	data: new SlashCommandBuilder()
		.setName('link')
		.setDescription('. . .'),

	async execute(interaction) {
		await interaction.reply({ content: `` , ephemeral: false });
	},
};