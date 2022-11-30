const { SlashCommandBuilder, ActionRowBuilder, ModalBuilder, TextInputBuilder, TextInputStyle, EmbedBuilder } = require('discord.js');
const postMessage = require('../Functions/postMessage');

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
				.setCustomId('title')
				.setLabel('Title of the post.')
				.setStyle(TextInputStyle.Short)
				.setRequired(true),
		
			link: new TextInputBuilder()		//Second input of text. The link of the post.
				.setCustomId('link')
				.setLabel('Link to the website.')
				.setStyle(TextInputStyle.Short)
				.setRequired(true),
			
			tags: new TextInputBuilder()		//Third input of text. The Tags of the post.
				.setCustomId('tags')
				.setLabel('Tags. [Seperate with spaces]')
				.setStyle(TextInputStyle.Paragraph)
				.setRequired(true),
			
			description: new TextInputBuilder()		//Fourth input of text. The description of the post.
				.setCustomId('description')
				.setLabel('Description of the website.')
				.setStyle(TextInputStyle.Paragraph)
				.setRequired(true)
		};
			
		//Assemble the text zones into ActionRows.
		
		const firstActionRow = new ActionRowBuilder().addComponents(fields.title);
		const secondActionRow = new ActionRowBuilder().addComponents(fields.link);
		const thirdActionRow = new ActionRowBuilder().addComponents(fields.tags);
		const fourthActionRow = new ActionRowBuilder().addComponents(fields.description);
		
		//Add the ActionsRows into a modal form.
		
		modal.addComponents(firstActionRow, secondActionRow, thirdActionRow, fourthActionRow);
		
		//Send the modal to the user.
		
		await interaction.showModal(modal);
		
	},
	
	
	//What should be done if the interaction was a ModalSubmit that the user responded to using the Command.
	async respond(interaction) {
		
		//Retrieve the data from the modal form.
		const title = interaction.fields.getTextInputValue('title');
		const link = interaction.fields.getTextInputValue('link');
		const tags = interaction.fields.getTextInputValue('tags').split(' ');
		const description = interaction.fields.getTextInputValue('description');
		
		const user = interaction.user;
		
		//Prepare text for the embed (if needed).
		let embedTags = '';
		for(const tag of tags){
			if(embedTags !== '') embedTags += ' | ';
			embedTags += tag;
		}
		
		//Build an Embed for the user to see what they submitted.
		const embed = new EmbedBuilder()
			.setColor('Green')
			.setAuthor({ name: `${user.username}`, iconURL: `${user.displayAvatarURL()}`})
			.setThumbnail('https://s3-eu-west-1.amazonaws.com/assets.atout-on-line.com/images/ingenieur/Logos_Ecoles/2018_2021/intech_300.jpg')
			.setTitle(link)
			.setURL(link)
			.setFields(
				{ name: 'Title :', value: `${title}`},
				{ name: 'Description :', value: `${description}`},
				{ name: 'Tags :', value: `${embedTags}` } 
			)
			.setFooter({ text: 'In\'Dev', iconURL: 'https://cdn.discordapp.com/attachments/1027155649223729210/1047527511711563887/index.png' })
			.setTimestamp();
		
		//Prepare text for the json (if needed).
		let jsonTags = '';
		for(const tag of tags){
			if(jsonTags !== '') jsonTags += ', ';
			jsonTags += `"${tag}"`;
		}
		
		//Build a JSON to send to the API.
		const json = `{"title":"${title}","link":"${link}","tags":[${jsonTags}],"description":"${description}","userId":"${user.id}","timeStamp":"${Date()}"}`;
		//Note Date.now() is time in ms since Jan 1, 1970, 00:00:00 UTC.
		
		console.log(json);
		
		//Send to user data retrived.
		await interaction.reply({ embeds: [embed] });
		
	}
}