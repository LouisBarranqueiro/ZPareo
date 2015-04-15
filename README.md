# ZPareo #

A simple school management web application written in Java and JavaScript for a small project in university

- Author : Louis Barranqueiro
- Version : 1.1

## Features

3 interfaces: administrators, teachers and student

Administrator :  
- user management (adminstrators, teachers and students)
- subject management
- group management

Teacher :  
- Test and score management

Student :  
- gradebook consultation

#### Requirements ####

- A Java application server (using GlashFish Server v4.0.0)
- A MysSQL server v5.5.38
- Maven v3.0.5
- Node v0.10.35

Node is required to manage npm dependencies with npm.  
Grunt is requires to build assets (js and scss) of the project.

#### Procedure ####

1. Run : ```git clone https://github.com/LouisBarranqueiro/ZPareo.git```
2. Run the SQL script ```database/creation_database.sql``` and  ```database/datasets_database.sql```
3. Install npm dependencies: ```npm install```
4. Install grunt-cli: ```npm install grunt-cli -g```
5. Install grunt: ```npm install grunt -g```
6. Start your java application server
7. Run a Maven install ```mvn clean``` and ```mvn install```
8. Enjoy :) and start coding

## Documentation ##

if you have any questions, create an issue. It will be a pleasur to help you! :)

#### Login id ####

Login : admin@zpareo.com  
Password : adminzpareo

## Contribution guidelines ##

- Use JSF primefaces Framework to replace all javascript functions to manage java beans with AJAX
- Improve JavaScript code structure
- Implement a pagination for each listing view
- Add more data for each object (teacher, students, administrators, etc...)
- Writing tests

## Licence ##

This project is under MIT License.