import fetch from "node-fetch";
const {Client, GatewayIntentBits} = require("discord.js");
// import { Client } from "discord.js";

const BOT = new Client({
    intents: [
        GatewayIntentBits.Guilds,
        GatewayIntentBits.GuildMessages,
        GatewayIntentBits.MessageContent

    ]
});
BOT.login("MTAzNDA4NDUzNTQxNzE3NjA2NA.G4E-c2.hSoIZosWkU70ns5X6PDXof0uy9B_BQ9vaJoS3A");

//connexion a la base de donnee
// const { Sequelize } = require('sequelize');
// const sequelize = new Sequelize('postgres://user:postgres:5432/BotDiscord');

function direHello() {
    let url = "localhost:8080/messages";
    fetch(url)
        .then(response => response.json())
        .then(console.log(response))
};

var callBackGetSuccess = function(data) {
    let element = data.main.temp;
    element.innerHTML = "La temperature est de " + data.main.temp;
    console.log(element);
}

// try {
//     sequelize.authenticate();
//     console.log('Connection has been established successfully.');
// } catch (error) {
//     console.error('Unable to connect to the database:', error);
// }


Client.on("ready", () => {
   console.log("bot op"); 
});

Client.on("messageCreate", message =>{
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





function doSomethingAsync(param, callback){
    // do something with param
    callback(result);
}


doSomethingAsync("tata")
.then(function(result){
    // do something with result
    console.log("toto2")
});
console.log("toto");