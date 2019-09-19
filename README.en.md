# REST service for tweet sentiment analysis

*Read this document in: [Spanish](README.md)*

Application is divided in two REST services. Out of certain topics, the application searchs messages on Twitter about them. Finally, the sentiment of these messages are analyzed.

Thus, we have one REST service in charge of search topics and messages about them, as well ass save them in the database, and another service in charge of the sentiment analysis of text.

The topics are searched using the tool [ArchMS](http://sele.inf.um.es/archms/), created by the University of Murcia, where we obtain medical information.

The comments about the topics are searched using [Twitter4j](http://twitter4j.org/en/), a non-official Java library, which allows an easy access to the Twitter's API.

The text sentiment analysis has been implemented though multiple algorithms:

* Based on our own dictionary.
* Using the library of [Apache OpenNLP](https://opennlp.apache.org/).
* Using the library of [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/).

Both services work independently. The text sentiment analysis service can be easily used by other services, though REST requests.

The comment extractor service allows the register of users. These users can add and remove other favourite people, whose comments will have priority above others.

## Starting 🚀

These instructions allows you to get a copy of the project in your local machine.

### Pre requirements 📋

The project has been written in Java.

Both services are deployed using Tomcat.

In short:

```bash
Java
Tomcat
```

## Built with 🛠️

Tools used for the project building

System

* [Maven](https://maven.apache.org/) - Dependency manager
* [ArchMS](http://sele.inf.um.es/archms/) - Tool created by the University of Murcia, used to get the topics.

Social media

* [Twitter4j](http://twitter4j.org/en/) - Library used to access Twitter

Sentiment analyzer algorithms

* [Apache OpenNLP](https://opennlp.apache.org/) - Library used for the text sentiment analysis
* [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/) - Library used for the text sentiment analysis

Database

* [MySQL](https://www.oracle.com/es/mysql/) - Database used to connect to ArchMS
* [Neo4j](https://neo4j.com/) - Database used for the persistence of information

Test

* [JUnit](https://junit.org) - Code test
* [REST-assured](http://rest-assured.io/) - Rest services test
* [Swagger](https://swagger.io/) - Interactive interface of REST services

Interface

* [HTML5](https://es.wikipedia.org/wiki/HTML) - Interface created using HTML
* [CSS3](https://www.w3schools.com/css/) - Interface decorated using HTML
* [JavaScript](https://www.javascript.com/) - Interface created using JS
* [AngularJS](https://angularjs.org/) - JS framework

## Authors ✒️

* **José Fernándo Fernández Espín** - *Inicial development*
* **Diego Valera** - *Development and subsequent completion* - [Di3GO95](https://github.com/Di3GO95/)

## License 📄

This project is created uned the MIT license - look the file [LICENSE.md](LICENSE.md) for details.
