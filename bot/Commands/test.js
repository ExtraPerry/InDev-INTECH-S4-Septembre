const { SlashCommandBuilder, EmbedBuilder } = require('discord.js');
var DiscordMessage = require('../Class/DiscordMessage');
const postMessage = require('../Functions/postMessage');
var randomWords = require('random-words');

module.exports = {
	data: new SlashCommandBuilder()
		.setName('test')
		.setDescription('Send predetermined messages to the api.'),


	//What should be done if the interaction was a Command by a user in chat input.
	async execute(interaction) {
		//The link.
		let linkList = [
			"https://www.youtube.com/watch?v=POC_npeyL2k",
			"https://www.youtube.com/watch?v=zfbe76IFxD8",
			"https://www.youtube.com/watch?v=POC_npeyL2k",
			"https://www.youtube.com/watch?v=9Ga30AoIh8o",
			"https://www.youtube.com/watch?v=pJrbpTVfJr8",
			"https://www.youtube.com/watch?v=3UHwENNWcNE",
			"https://www.youtube.com/watch?v=flZjvWKz_WE",
			"https://www.youtube.com/watch?v=5fp_pnhKPMY",
			"https://www.youtube.com/shorts/1kqJonG_lt4",
			"https://www.google.com",
			"https://spring.io/projects/spring-boot",
			"https://www.java.com",
			"https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html",
			"https://developer.mozilla.org/fr/docs/Web/JavaScript",
			"https://nodejs.org",
			"https://vitejs.dev/",
			"https://www.youtube.com",
			"https://discord.com/developers/docs/intro",
			"https://discord.js.org/#/docs/discord.js/main/general/welcome",
			
		];
		
		//The tags.
		let tagList = [
			"intech",
			"indev",
			"youtube",
			"merp",
			"java",
			"fr",
			"en",
			"vr",
			"free",
			"suchi",
			"chat",
			"chien",
			"serpent",
			"sergal",
			"ingenieur",
			"javascript",
			"springboot",
			"jpa",
			"sql",
			"mariadb",
			"pc",
			"laptop",
			"telephone",
			"console",
			"table",
			"chaise",
			"bureau",
			"projecteur",
			"tablet",
			"oop",
			"script",
			"fin"
		];
		
		//the user that did the command.
		let user = interaction.user;
		
		//Create random numbers.
		let nbMessages = Math.floor(Math.random() * 542) + 1;
		
		//Builde the messages.
		let timeout = 100;
		
		for(let i = 1; i < nbMessages; i++){
			let linkIndex = Math.floor(Math.random() * linkList.length) + 1;
			let nbTags = Math.floor(Math.random() * 6) + 1;
			
			let tempTags = [];
			for(let i = 0; i < nbTags; i++){
				let randomTag = Math.floor(Math.random() * tagList.length) + 1;
				tempTags[i] = tagList[randomTag - 1];
			}
			
			let message = new DiscordMessage(
				randomWords({min: 1, max: 5, join: ' '}),
				linkList[linkIndex - 1],
				tempTags,
				false,
				randomWords({min: 10, max: 15, join: ' '}),
				user.tag,
				user.id,
				new Date().getUTCMilliseconds()
			);
			
			try{
				setTimeout(function (){
  				// Something you want delayed.
            	postMessage(JSON.stringify(message));
				}, timeout); // How long you want the delay to be, measured in milliseconds.
				timeout += 100;
				
			}catch(error){	//Return if an error happens.
				console.error('oops');
			}
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
