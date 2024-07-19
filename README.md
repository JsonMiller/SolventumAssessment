Solventum Assessment (URL Shortener)

Hello and welcome to my project!

To start the server (assuming you have docker setup) you can just run `docker compose up` and it'll spin up the service
in a container.

Once it's running you can `POST` to `http://localhost:8080/encode` with a JSON body that contains a url property.

The url property needs to be a valid url. 

The response should be a JSON body with an encodedUrl and the decodedUrl.

After that you can `POST` to `http://localhost:8080/decode` with a JSON body that contains a url property.

The url property needs to be a valid shortened url. 

The response will be a JSON body with an encodedUrl and the decodedUrl.

As an added bonus you can navigate to the encodedUrl and it should redirect you to the decodedUrl.

The shortened url is built using properties in the `application.properties` file so if you want to configure
the pattern of the shortened url you can do so there. There are also some tests specified in the tests directory.

For this project I prioritized performance over storage space so I used a bidirectional map in the Repository it
basically just sets up 2 hash maps so either direction should have fast lookups.


Also I really wanted to include jetbrains .http files for endpoint tests, but I only have the community edition and they 
aren't supported there...