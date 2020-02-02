# Stock Brokerage and Exchange Application - Setup Guide
Implementing a Responsive Web Site and Scalable Web Application based on the Service Oriented Architecture implemented using Web Services

### Getting Started
These instructions will get you a copy of the application up and running on your local machine for development and testing purposes.

### Softwares/SDKs
#### Required Tools and Softwares
1. Java 8 or later
2. MySQL 5.7
3. Apache Tomcat 9.0
4. Eclipse 2019-09 version
5. RabbitMQ
6. Memcache
7. Any OS, MacOS/Windows

#### Recommended Tools and Softwares
1. MySQL Workbench 6.3.10
2. Postman REST Client

### End to End Setup Instructions
Once you get all the required softwares and if possible the additional tools installed, follow the setup guidelines for each of the below sections

#### Database
* Run the SQL files from the folders below, on the MySQL Database. This will setup the datamodel and also insert sample seed data for the application to begin
     * ``https://github.com/amtul-nazneen/stock-brokerage-webapp/tree/master/sqlDump/mySQL/datamodel``
     * ``https://github.com/amtul-nazneen/stock-brokerage-webapp/tree/master/sqlDump/mySQL/seedData``
* Make sure you update the config properties with your MySQL ``root username and password`` here: ``https://github.com/amtul-nazneen/stock-brokerage-webapp/blob/master/src/app/config/Config.java``

#### Tomcat
* After downloading the Apache Tomcat zip, make three copies of the tomcat folder [Ex: tomcat1, tomcat2, tomcat3]
* Make the below changes to each of their config folders
* Tomcat 1
     * Setup it on port ``8443``
     * Replace its server.xml content with the attached server1.xml file content 
     * Create a jks file and add the ``server2.crt`` file in the keystore
     * Refer to script.txt in the attached folder for the keystore and keytool commands
     * Sample ``server1.jks`` and ``server2.crt`` are provided in the folder
* Tomcat 2
     * Setup it on port ``9443``
     * Replace its server.xml content with the attached server2.xml file content 
     * Create a jks file and add the ``server3.crt`` file in the keystore
     * Refer to script.txt in the attached folder for the keystore and keytool commands
     * Sample ``server2.jks`` and ``server3.crt`` are provided in the folder
* Tomcat 3
     * Setup it on port ``7443``
     * Replace its server.xml content with the attached server1.xml file content 
     * Create a jks file in the keystore
     * Refer to script.txt in the attached folder for the keystore and keytool commands
     * Sample ``server3.jks`` is provided in the folder

     
### Cache
* Install MemCache for your OS and once done, make sure that its running on ``host:127.0.01, port:11211``
* In the event that your host and port details are different, make sure you update this config file ``https://github.com/amtul-nazneen/stock-brokerage-website/blob/master/src/app/config/Config.java``


### Queue
* Install RabbitMQ for your OS and once done, make sure that its running on ``host:127.0.01, port:11211``
* In the event that your host is different, make sure you update this config file ``https://github.com/amtul-nazneen/stock-brokerage-webapp/blob/master/src/app/config/Config.java with the RabbitMQ Host Details``
* If you want to change the Queue details for Asynchronous Buy and Sell, you can update the details here: ``https://github.com/amtul-nazneen/stock-brokerage-webapp/blob/master/src/app/config/Constants.java``

#### Applications
* Download the latest code for all three applications from the links below or from E-Learning
     * ``https://github.com/amtul-nazneen/stock-brokerage-website``
     * ``https://github.com/amtul-nazneen/stock-brokerage-webapp``
     * ``https://github.com/amtul-nazneen/stock-exchange-webapp``
* Open each of the applications in a separate Eclipse instance
* Setup the following applications in Eclipse with the following servers
     * ``stock-brokerage-website: tomcat1``
     * ``stock-brokerage-webapp: tomcat2``
     * ``stock-exchange-webapp: tomcat3``

### Misc Configurations
* Below are the list of all configuration files that are used in the project. If any changes, make sure to update these
     * ``https://github.com/amtul-nazneen/stock-brokerage-website/tree/master/src/app/config``
     * ``https://github.com/amtul-nazneen/stock-brokerage-webapp/tree/master/src/app/config``
     * ``https://github.com/amtul-nazneen/stock-exchange-webapp/blob/master/src/app/config``

#### Running the application
* Deploy each of the applications from Eclipse onto the Apache Tomcat server
* Start the MemCache and RabbitMQ services
* Make sure that your MySQL service is running
* Go to the url of the stock-brokerage-website and login with any user
     * To get a user detail to login, use the query from ``'SELECT USERNAME,PWD FROM STOCKS.WPL_USER'``;
* The environment is ready for testing

### Verification
* Most of the functionalities output can be viewed from the UI itself
* But for specifics, the database can be queried
* Logs are enabled on each server which is displayed in the Eclipse Console

### Authors
* **Amtul Nazneen** - [amtul-nazneen](https://github.com/amtul-nazneen)
* **Hemanjeni Kundem** - [hemanjeni](https://github.com/hemanjeni)

