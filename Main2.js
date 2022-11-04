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
client.login("MTAzNDA4NDUzNTQxNzE3NjA2NA.G4E-c2.hSoIZosWkU70ns5X6PDXof0uy9B_BQ9vaJoS3A");

client.on("ready", () => {
    console.log("bot op"); 
});

function direHello(){
    fetch('https://github.com/')
    .then(res => res.text())
    .then(body => console.log(body));
}

client.on("messageCreate", message =>{
    if (message.author.bot) return;
    switch (message.content) {
        case 'ping':
            message.reply("pong");
            message.channel.send("pongito");
            break;
        case 'help':
            message.channel.send("Commandes disponibles :\n ping : repond 'pong'");
            break;
        case 'hello':
            direHello();
            break;
    }
    console.log(message);
});

