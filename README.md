# songs-trend
SongsTrend is application that retrieves data from [SoundCloud](https://soundcloud.com/), stores it in database and exposes the word trends from the songs' titles via endpoint.

# Storing API Key
For storing credentials and sensitive keys the application is using [HashiCorp's Vault](https://www.vaultproject.io/) and [Spring Vault](https://spring.io/projects/spring-vault) as an abstraction for it. And this is where api key for SoundCloud is stored.

In order to use it, a vault server should be running and a secret with key ```cloudApiKey``` under ```secret/songs-trend``` should be created. Depending on your vault server configuration you may have to change the configuration file ```main/java/resources/vault-config.properties```.

# Framework
Application is build with [Spring Framework](https://spring.io/)

# Database
Application uses SQL in-memory database [H2](https://www.h2database.com/html/main.html).

## How to access database
Once the application is started, [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/) endpoint can be accessed in order to see H2 console. Once accessed, it will requre credentials. Those can be found in `application.properties` under `resources` folder in the main source folder.

# Daily Scheduler
Every day at 12h (timezone dependent on where the application will be deployed) a scheduler(`WordTrendsTask`) will start downloading tracks from SoundCloud via endpoint [https://api.soundcloud.com/tracks](https://api.soundcloud.com/tracks). Then the titles are analyzed, split into words, words are count and then information is stored into the database.

# How to run
From the root of the project build with [maven](http://maven.apache.org/):

```mvn clean package```

After that run the generated `jar` file. From `target` folder execute:

```java -jar song-trend-0.0.1-SNAPSHOT.jar```

# How to run tests
Run with maven:

```mvn clean verify```

# API
After running the application an endpoint for getting word trends is exposed:
```
Method: GET
Endpoint: /word
Query Params: 
    word - word to search from;
    from - date from in format yyyyMMdd;
    to - date to in format yyyyMMdd;
Response: JSON 
{"word":"<word>","timeSeries":
  [{"count":<count>,"date":"<date yyyyMMdd>"},
   {"count":<count>,"date":"<date yyyyMMdd>"}]}
```


## Example
Request:

```http://localhost:8080/word?word=snippet&from=20200404&to=20200501```

Response:

```{"word":"snippet","timeSeries":[{"count":1,"date":"20200420"}]}```
