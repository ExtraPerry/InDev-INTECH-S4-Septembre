// const fetch = require('node-fetch');
const fetch = (...args) =>
import('node-fetch').then(({ default: fetch }) => fetch(...args));
//declarations relatives a discord
const {Client, GatewayIntentBits} = require("discord.js");
const client = new Client({
    intents: [
        GatewayIntentBits.Guilds,
        GatewayIntentBits.GuildMessages,
        GatewayIntentBits.MessageContent
    ]
});
const discordModals = require('discord-modals'); // Define the discord-modals package!
discordModals(client); // discord-modals needs your client in order to interact with modals
client.login("MTAzNDA4NDUzNTQxNzE3NjA2NA.G4E-c2.hSoIZosWkU70ns5X6PDXof0uy9B_BQ9vaJoS3A");
const { Modal } = require('discord-modals'); // Modal class

const modal = new Modal() // We create a Modal
	.setCustomId('modal-customid')
	.setTitle('Modal')
    .addComponents(
        new TextInputComponent()
        
    );

client.on("ready", () => {
    console.log("bot op"); 
});

function direHello(){
    fetch('http://localhost:8080/messages')
    .then(res => res.text())
    .then(body => console.log(body));
}
function poster(){
    fetch('http://localhost:8080/addMessage', { method: 'POST', headers:{'content-type': 'application/json'}, body: JSON.stringify('a=1') })
    .then(res => res.text()) // expecting a json response
    .then(text => console.log(text));
}

client.on("messageCreate", message =>{
    if (message.author.bot) return;
    if (message.content.includes('https://' || 'http://')){

    }
    switch (message.content) {
        case 'ping':
            message.reply("pong");
            break;
        case 'help':
            message.channel.send("Commandes disponibles :\n ping : repond 'pong'");
            break;
        case 'hello':
            direHello();
            break;
        case 'post':
            poster();
            break;
        case 'modal':
            discordModals.showModal(modal);
    }
    console.log(message.content);

});
