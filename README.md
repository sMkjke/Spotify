# Spotify Music Advisor

 About
 
 Modern world spoils us with a wide variety of music: pop-lovers, rockers, rappers and club enthusiasts alike face a nearly infinite choice. Fortunately, there are programs that can help us find something we like and not get lost in this variety: in this project, you will write a program like this. Create a personal music advisor that makes preference-based suggestions and even shares links to new releases and featured playlists. Together with Spotify, your music advisor will be a powerful guide to the world of music.
 
  * -access argument should provide authorization server path. The default value should be https://accounts.spotify.com .
  * -resource argument should provide api server path. The default value should be https://api.spotify.com .
  * -page. If ths argument isn't set, you should use the default value 5.

### Available commands:
  *     HELP
  *     EXIT
  *     FEATURED
  *     NEW
  *     CATEGORIES
  *     PLAYLISTS
  *     NEXT
  *     PREVIOUS

### Built With
 
 * [Maven](https://maven.apache.org/) - Dependency Management
 
### Getting Started
```
  mvn exec:java -Dexec.mainClass="com.github.smkjke.spotify.Main"
```
###### Author
 
 * **Mikhail Voronov** - [github](https://github.com/sMkjke)
