# Article consumer

Spring Boot microservice that reads RSS with Java, via Spring Integration

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
```
Download and install Java https://www.java.com/en/download/
```
```
Download and install Maven https://maven.apache.org/install.html
```
```
Download and install MySql Server https://dev.mysql.com/downloads/mysql/
```

### Installing

A step by step series of examples that tell you how to get a development env running

1. Create a new Schema inside MySQL Server, by executing the following query
```
CREATE DATABASE IF NOT EXISTS `news_feed`
```
In order to create the database manually, the script file is located at
```
news-feed\src\main\resources\static\create_scripts.sql
```
However, spring.jpa.hibernate.ddl-auto=create property from application.properties will generate the tables for you


2. Clone this repository
```
git clone https://github.com/shara-nam/java
```

## Running the application

Download the project's dependencies and install them into your local repository. Execute the following command inside the project's root folder:

```
mvn clean install
```
This will also generate the news-feed-0.0.1-SNAPSHOT.jar into the target folder.

Option 1: You can run the application from an IDE. However, you first need to import your project. 

To generate the files needed for an IntelliJ IDEA Project setup, you only need to execute the main plugin goal, which is idea:idea like so
```
mvn idea:idea
```
To generate the files needed for an Eclipse Project setup
```
mvn eclipse:eclipse
```

Option 2:  Alternatively, navigate to the generated target folder and run the application
```
java -jar news-feed-0.0.1-SNAPSHOT.jar
```

The Spring Integration configuration file is located at
```
news-feed\src\main\resources\config\rss-inbound.xml
```
## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Alexandru Buhu** - *Initial work* - [AlexBuhu](https://github.com/alex-buhu)

See also the list of [contributors](https://github.com/shara-nam/java/graphs/contributors) who participated in this project.

## License

This project is licensed under the GNU General Public License - see the [LICENSE.md](LICENSE.md) file for details