# Chat-bot

## Beta version<br><br>

### Before you start : 
Look at application.properties in resources & LoadAtStart class where you can load asks from the txt file<br>
run with "mvn jetty:run"<br>

### Sources :

code :  <br>
https://stackoverflow.com/questions/11016388/send-data-with-jquery-to-an-mvc-controller //David East <br>
https://github.com/rstoyanchev/spring-mvc-chat<br><br>

Asks : <br>
http://www.theticblog.com/2015/10/list-of-1000-random-questions-to-ask-people-on-askfm.html<br>
http://www.saviodsilva.org/<br><br>

I am not author of front-end, just modified it 


How to run with docker : 
- make sure your port 8080 is free
- copy docker-compose.yml file from this repository to your local environment
- locate docker-compose.yml file and run command in same directory : docker-compose up
- get onto localhost:8080/chat
