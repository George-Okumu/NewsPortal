SET MODE PostgreSQL;

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
 employeeName VARCHAR,
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