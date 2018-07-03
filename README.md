[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c51cb311e2a14f0bbae6e7e292e2c9c3)](https://www.codacy.com/app/sldevand/congesWebService?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=sldevand/congesWebService&amp;utm_campaign=Badge_Grade)

congesWebService
======
**congesWebService** Employee's holidays management Webservice built with Java Spring-boot and Hibernate/MySQL

<div style="color:orange;">
Work in progress... I'm still working on it.
</div>

## Prerequisites
* Install [Java JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Install [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* Install [MySQL](https://dev.mysql.com/downloads/mysql/)
* You must know how to manage a MySQL Database.
* Create a MySQL Database with phpMyAdmin or your favourite database manager software or manually with MySQL prompt.

## Install
You can clone this repository and open it in IntelliJ Idea
```
git clone https://github.com/sldevand/congesWebService
```
Create one application.properties file inside **src/main/resources** and insert these lines :

```
#Rest service
server.port = 9000

#MySQL
spring.jpa.hibernate.ddl-auto=create
spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabasename
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect

#logging
logging.level.org.springframework.data=debug
logging.level.=error
```
## Tests
* Create another database
* Add an **application-TEST.properties** file;
* Copy the **application.properties** content into **application-TEST.properties**
* Change **server.port** and  **spring.datasource.url**

Test with Maven
```
mvn test
```

## Build
Package with Maven
```
mvn package
```

## Run
Run with Java CLI
```
java -jar ./target/congesWebService-1.0-SNAPSHOT.jar
```

## WebService endpoints
### Read full list of employees :
```
GET employees/
```
### Read an employee by matricule :
```
GET employees/{matricule}
```
### Create an employee:
```
POST employees/
```
With Request Headers :
```
"Content-Type":"application/json"
"Accept":"application/json"
```
Body JSON Example:
```
{
  "matricule": "SJ1508669",
  "nom": "Sparrow",
  "prenom": "Jack",
  "naissance": "1977-08-14T22:46:50.125+0000",
  "poste": {
    "id": 1  
  },
  "service": {
    "id": 1  
  },
  "entree": "1970-01-01T00:00:00.000+0000",
  "conges": [],
  "resteConges": 0,
  "adresse": { 
    "libelle": "rue des Marins",
    "complement": "Gallion E",
    "codePostal": 66666,
    "ville": "Tortuga",
    "pays": "Bahamas",
    "valid": true
  },
  "tel": "",
  "email": "Sparrow.Jack@congesapp.fr"
}
```

### Update an employee :
Headers and body as **Create an employee** section
```
PUT employees/
```

### Delete an employee by matricule : 
```
DELETE employees/{matricule}
```

## Built With

* [IntelliJ Idea](https://www.jetbrains.com/idea/) - Java IDE Software

## Author

* **Sébastien Lorrain** - *Initial work* - [congesWebservice](https://github.com/sldevand/congesWebService)

## License 
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Motivation
I want to master Java and Spring boot to improve my skills
