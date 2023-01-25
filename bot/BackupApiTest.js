const fakeInteraction = require('./Class/fakeInteractionObject');
const test = require('./Commands/test');

let interaction = new fakeInteraction("427903213136642070", "ExtraPerry#9032");

test.execute(interaction);