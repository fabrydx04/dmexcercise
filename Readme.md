########## TODO  ################
######## services unit tests
#################################

This api fetchs new feeds in background using a secondary thread in a configurable interval and then it stores in the database the newest 10 feeds per pull. 
In addition, it exposes an endpoint to consume the feeds.


Consumers can get feeds here http://localhost:8080/v1/api/feeds
Also I included swagger. You can test the api here http://localhost:8080/swagger-ui.html

you can configure the fetch time interval and timeout values in the application.properties file

I Used a H2 (in memory) database, so you don't need to install anything, just run the command 'mvn spring-boot:run' and it will start an in memory database and JPA will create the SQL schema for you 
if you want to see the database structure, you can do it here: http://localhost:8080/h2-console/
- Driver class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- password: password

I implemented the comparable interface in Feed entity and then I override the compareTo method to allow order feeds by publishedDate
Feeds are added to a TreeSet which automatically order the feeds according to compareTo method

I save the newest 10 feeds per fetch, I don't know if it is ok. Requeriments are not clear in this point.

I created a FeedConsumerService interface which extends callable and it have a method 'Call' that returns a collection of feeds.
if you want to add more origin sources, you just have to implement that interface on your new service ensuring the homogeneity of the feeds stored in the database. 
Then you should add more threads to the poolExecutor (ExecutorServiceConfig) and use it
If you have many source origins, you can create a factory which returns an object which implements FeedConsumerService 



Fabricio Alan Garcia
