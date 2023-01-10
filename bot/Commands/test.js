const { SlashCommandBuilder, EmbedBuilder } = require('discord.js');
var DiscordMessage = require('../Class/DiscordMessage');
const postMessage = require('../Functions/postMessage');

module.exports = {
	data: new SlashCommandBuilder()
		.setName('test')
		.setDescription('Send predetermined messages to the api.'),


	//What should be done if the interaction was a Command by a user in chat input.
	async execute(interaction) {
		//The link.
		let link = "https://www.youtube.com/watch?v=POC_npeyL2k";
		
		//The tags.
		let tagListOne = ["intech","indev"];
		let tagListTwo = ["youtube", "indev"];
		let tagListThree = ["intech", "merp", "youtube"];
		let tagListFour = ["tytolis", "merp", "indev", "youtube", "indev"];
		
		//the user that did the command.
		let user = interaction.user;
		
		//Builde the messages.
		let messageOne = new DiscordMessage("First", link, tagListOne, false, "First one.", user.tag, user.id, null, new Date().getUTCMilliseconds());
		let messageTwo = new DiscordMessage("Second", link, tagListTwo, false,"Second one.", user.tag, user.id, null, new Date().getUTCMilliseconds());
		let messageThree = new DiscordMessage("Third", link, tagListThree, false, "Third one.", user.tag, user.id, null, new Date().getUTCMilliseconds());
		let messageFour = new DiscordMessage("Fourth", link, tagListFour, false, "Fourth one.", user.tag, user.id, null, new Date().getUTCMilliseconds());
		
		try{	//Sent predetermined messages to the api.
			setTimeout(function (){
  			// Something you want delayed.
            postMessage(JSON.stringify(messageOne));
			}, 1000); // How long you want the delay to be, measured in milliseconds.
			
			setTimeout(function (){
  			// Something you want delayed.
            postMessage(JSON.stringify(messageTwo));
			}, 2000); // How long you want the delay to be, measured in milliseconds.
			
			setTimeout(function (){
  			// Something you want delayed.
            postMessage(JSON.stringify(messageThree));
			}, 3000); // How long you want the delay to be, measured in milliseconds.
			
			setTimeout(function (){
  			// Something you want delayed.
            postMessage(JSON.stringify(messageFour));
			}, 4000); // How long you want the delay to be, measured in milliseconds.
			
		}catch(error){	//Return this to the user if something went wrong while sending to the api.
			await interaction.reply({
				embeds: [new EmbedBuilder()
					.setColor('DarkRed')
					.setDescription(`Unable to send the contents to the api.`)
					],
				ephemeral: true
			});
			return;
		}
		
		//If everything went correctly send a confirmation message.
		await interaction.reply({
				embeds: [new EmbedBuilder()
					.setColor('Green')
					.setDescription(`Content sent to the api successfully.`)
					],
				ephemeral: true
			});
		
	}
}
