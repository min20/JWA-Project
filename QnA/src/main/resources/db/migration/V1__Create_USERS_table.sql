DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
	userId		varchar(20)		NOT NULL,
	password	varchar(20)		NOT NULL,
	name		varchar(50)		NOT NULL,
	email		varchar(100),
	PRIMARY KEY (userId) );
	
INSERT INTO USERS VALUES('min20', '1234', '이경민', 'min20@nhnnext.org');
