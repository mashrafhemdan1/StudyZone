ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
drop database if exists registery;
CREATE DATABASE registery;
USE registery;
Create table registery(
    id VARCHAR(128) NOT NULL,
    token Text NOT NULL, 
    Primary Key(id)
);
Create table account(
    userid VARCHAR(128) NOT NULL,
    FName VARCHAR(12) NOT NULL,
    LName VARCHAR(12) NOT NULL,
    email VARCHAR(25) NOT NULL,
    university VARCHAR(50) NOT NULL,
    #picture IMAGE,
    grade FLOAT,
    Primary Key(userid)
);
drop table if exists pairs;
CREATE TABLE pairs(
    sender_id VARCHAR(128) NOT NULL,
    reciever_id VARCHAR(128) NOT NULL,
    status BOOLEAN NOT NULL,  
    #False means it is still pending if true that means they are actual pairs
    PRIMARY KEY(sender_id, reciever_id)
);

insert into registery values ("1001001001", "JiJiJiJi");

select * from usertoken;

SELECT * FROM sms WHERE sent = false ORDER BY ts LIMIT 1;

select token from registery where id = "";



INSERT INTO account VALUES ("000000", "Kamel", "Hossam", "kamelhossam@aucegypt.edu", "American University in Cairo", 0.8);
INSERT INTO account VALUES ("000001", "Mariam", "Kamel", "mariamkamel@aucegypt.edu", "American University in Cairo", 0.7);
INSERT INTO account VALUES ("000002", "Ismail", "Ibrahim", "ismail@aucegypt.edu", "American University in Cairo", 0.9);
INSERT INTO account VALUES ("000003", "Ibrahim", "Shaheen", "ibrahim@aucegypt.edu", "American University in Cairo", 0.5);
INSERT INTO account VALUES ("000004", "Amal", "Hassan", "amal@aucegypt.edu", "American University in Cairo", 0.3);
INSERT INTO account VALUES ("000005", "Kenzy", "Ashraf", "kenzy@aucegypt.edu", "American University in Cairo", 0.7);
INSERT INTO account VALUES ("000006", "Asmaa", "Hamed", "asmaa@aucegypt.edu", "American University in Cairo", 0.1);
INSERT INTO account VALUES ("000007", "Omar", "Osama", "omar@aucegypt.edu", "American University in Cairo", 0.4);

select * from account;


INSERT INTO pairs VALUES ("000001", "000002", 1);
INSERT INTO pairs VALUES ("000002", "000003", 1);
INSERT INTO pairs VALUES ("000003", "000004", 1);
INSERT INTO pairs VALUES ("000004", "000005", 1);
INSERT INTO pairs VALUES ("000005", "000006", 1);
INSERT INTO pairs VALUES ("000006", "000007", 1);
INSERT INTO pairs VALUES ("000007", "000001", 1);
INSERT INTO pairs VALUES ("000006", "000001", 0);
INSERT INTO pairs VALUES ("000005", "000001", 0);


select * from pairs;
select * from registery;