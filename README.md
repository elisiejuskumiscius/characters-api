# characters-api
The Marvel Comics API http://developer.marvel.com/ allows developers to access information about Marvel's vast library of comics. 

1. Served an endpoint at http://localhost:8080/characters that returns all the Marvel character ids only, in a JSON
   array of numbers.
2. Served an endpoint http://localhost:8080/characters/characters/{characterId} that contains the real-time data from the
   Marvel API, but containing only the following information about each character: <i>id, name, description, thumbnail</i>.

You'll need to sign up for Marvel developer API key at http://developer.marvel.com and enter your <b>PRIVATE_KEY</b> and <b>PUBLIC_KEY</b> in order to run the project.
