const fs = require("fs");

module.exports = async () => {
	
	fs.readdirSync("./Commands").filter(f => f.endsWith(".js")).then(async file => {	//Scan the "Cammands" file directory for files with the "".js" extension.
	
		//For Each file scanned . . .
		let command = require(`../Commands/${file}.js`);	//Get the current scanned file's (module) information.
		if (!command.name || typeof command.name !== "string" ) throw new TypeError(`The command file module "${file}" has no defined name.`); //Check if the module has a "name" defined.
		
		bot.commands.set(command.name, command); //Load the command into the list of commands the bot will have.
		console.log(`Command module ${file} was loaded succesfully.`);
	})
}