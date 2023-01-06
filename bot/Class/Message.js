//Message.js
module.exports = 
		//Class - This class is only needed to store specified information and then serialise it.
		class Message {
			//Constructor
			constructor(title, link, tags, description, userId, messageId, time){
				//Attributes.
				this.title = title;	//String - text.
				this.link = link;	//String - URL link as text.
				this.tags = tags;	//String - List of tag (a tag is text more importantly one word).
				this.description = description; //String - text.
				this.userId = userId;	//String - The discord snowflake of the userId in text.
				this.messageId = messageId;	//String - The discord snowflake of the messageId in text.
				this.time = time;	//Date or TimeStamp - The time at which the Message Class was sent to the Api.
			}
}

/**
	Context ==>
	
	Discord Snowflake : https://discord.js.org/#/docs/discord.js/main/typedef/Snowflake
	Date : https://developer.mozilla.org/fr/docs/Web/JavaScript/Reference/Global_Objects/Date
	TimeStamp : https://docs.oracle.com/javase/8/docs/api/java/sql/Timestamp.html
*/