const { SlashCommandBuilder, ActionRowBuilder, ModalBuilder, TextInputBuilder, TextInputStyle } = require('discord.js');

module.exports = {
	data: new SlashCommandBuilder()
		.setName('link')
		.setDescription('A link to submit.'),

	async execute(interaction) {
		
		//Create the modal object.
		
		const modal = new ModalBuilder()
			.setCustomId('linkPostModal')
			.setTitle('Link post form.');
		
		//Build the initial text zones.
		
		const title = new TextInputBuilder()		//First input of text. The link.
			.setCustomId('titleInput')
			.setLabel('Title of the post.')
			.setStyle(TextInputStyle.Short);
		
		const link = new TextInputBuilder()		//Second input of text. The link.
			.setCustomId('linkInput')
			.setLabel('Link to the website.')
			.setStyle(TextInputStyle.Short);
			
		const tags = new TextInputBuilder()		//Third input of text. The Tags.
			.setCustomId('tagsInput')
			.setLabel('Tags. [Seperate with spaces]')
			.setStyle(TextInputStyle.Paragraph);
			
		const description = new TextInputBuilder()		//Fourth input of text. The description.
			.setCustomId('descriptionInput')
			.setLabel('Description of the website.')
			.setStyle(TextInputStyle.Paragraph);
			
		//Assemble the text zones into components.
		
		const firstActionRow = new ActionRowBuilder().addComponents(title);
		const secondActionRow = new ActionRowBuilder().addComponents(link);
		const thirdActionRow = new ActionRowBuilder().addComponents(tags);
		const fourthActionRow = new ActionRowBuilder().addComponents(description);
		
		//Assemble the componets into a modal form.
		
		modal.addComponents(firstActionRow, secondActionRow, thirdActionRow, fourthActionRow);
		
		//Part where you handle the modal's response :D
		
		await interaction.showModal(modal);
		
	},
};