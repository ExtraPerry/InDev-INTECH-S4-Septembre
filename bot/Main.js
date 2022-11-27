//Imports
const Discord = require("discord.js");
const config = require('./config.json');
const loadCommands = require('./Loaders/LoadCommands');

//Get Fetch libraries (https).
const fetch = (...args) =>
import('node-fetch').then(({ default: fetch }) => fetch(...args));


//Bot Intents (request perms) & Bot Token & Assign commands & Bot start-up.
const intents = new Discord.IntentsBitField(config.intents);
const client = new Discord.Client(intents);

const token = config.token;
client.login(token);

loadCommands(client);

client.on("ready", async () => {
    console.log(`${client.user.tag} is now Online !`);
});















//Functions.
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