const { SlashCommandBuilder, ActionRowBuilder, ModalBuilder, TextInputBuilder, TextInputStyle } = require('discord.js');

module.exports = {
	data: new SlashCommandBuilder()
		.setName('link')
		.setDescription('A link to submit.'),


	//What should be done if the interaction was a Command by a user in chat input.
	async execute(interaction) {
		
		//Create the modal object.
		
		const modal = new ModalBuilder()
			.setCustomId(interaction.commandName)		//customId should be the same name as the command.	
			.setTitle('Link post form.');
		
		//Build the initial text zones.
		const fields = {
			
			title: new TextInputBuilder()		//First input of text. The title of the post.
				.setCustomId('titleInput')
				.setLabel('Title of the post.')
				.setStyle(TextInputStyle.Short),
		
			link: new TextInputBuilder()		//Second input of text. The link of the post.
				.setCustomId('linkInput')
				.setLabel('Link to the website.')
				.setStyle(TextInputStyle.Short),
			
			tags: new TextInputBuilder()		//Third input of text. The Tags of the post.
				.setCustomId('tagsInput')
				.setLabel('Tags. [Seperate with spaces]')
				.setStyle(TextInputStyle.Paragraph),
			
			description: new TextInputBuilder()		//Fourth input of text. The description of the post.
				.setCustomId('descriptionInput')
				.setLabel('Description of the website.')
				.setStyle(TextInputStyle.Paragraph)
		};
			
		//Assemble the text zones into components.
		
		const firstActionRow = new ActionRowBuilder().addComponents(fields.title);
		const secondActionRow = new ActionRowBuilder().addComponents(fields.link);
		const thirdActionRow = new ActionRowBuilder().addComponents(fields.tags);
		const fourthActionRow = new ActionRowBuilder().addComponents(fields.description);
		
		//Assemble the componets into a modal form.
		
		modal.addComponents(firstActionRow, secondActionRow, thirdActionRow, fourthActionRow);
		
		//Send the modal to the user.
		
		await interaction.showModal(modal);
		
	},
	
	
	//What should be done if the interaction was a ModalSubmit that the user responded to using the Command.
	async respond(interaction) {
		
	}
}