SET MODE PostgreSQL;

CREATE DATABASE news_portal;
\c news_portal;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 departmentName VARCHAR,
 description VARCHAR,
 numberOfEmployees INTEGER
);

CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
 title VARCHAR,
 content VARCHAR,
 departmentID INTEGER
);

CREATE TABLE IF NOT EXISTS employees (
 id int PRIMARY KEY auto_increment,
 UserName VARCHAR,
 position VARCHAR,
 role VARCHAR,
 email VARCHAR,
 phoneNumber VARCHAR,
 departmentId INTEGER

);

 CREATE TABLE IF NOT EXISTS departments_employees (
 id int PRIMARY KEY auto_increment,
 employeeid INTEGER,
  departmentid INTEGER
);

CREATE DATABASE news_portal_test WITH TEMPLATE news_portal;