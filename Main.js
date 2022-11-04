import fetch from "node-fetch";
//declarations relatives a discord.
const {Client, GatewayIntentBits} = require("discord.js");
const client = new Client({
    intents: [
        GatewayIntentBits.Guilds,
        GatewayIntentBits.GuildMessages,
        GatewayIntentBits.MessageContent

    ]
});
client.login("MTAzNDA4NDUzNTQxNzE3NjA2NA.G4E-c2.hSoIZosWkU70ns5X6PDXof0uy9B_BQ9vaJoS3A");


function direHello() {
    let url = "localhost:8080/messages";
    fetch(url)
        .then(response => response.json())
        .then(console.log(response))
};
function poster(){
    fetch('localhost:8080/', { method: 'POST', body: 'a=1' })
    .then(res => res.json()) // expecting a json response
    .then(json => console.log(json));
}

var callBackGetSuccess = function(data) {
    let element = data.main.temp;
    element.innerHTML = "La temperature est de " + data.main.temp;
    console.log(element);
}


client.on("ready", () => {
   console.log("bot op"); 
});

client.on("messageCreate", message =>{
    if (message.author.bot) return;
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