/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Tomambid
 * Created: Oct 9, 2017
 */
drop table Personnel

create table Personnel(
U_ID serial primary key,
U_Fname varchar(50) not null,
U_Lname varchar(50) not null,
U_MI char(1) not null,
Email varchar(50) not null,
gender varchar(10),
username varchar(20) not null,
password varchar(20) not null,
position varchar(10) not null,
Image bytea,
CONSTRAINT UC_PersonnelName UNIQUE(U_Fname,U_Lname,U_MI),
CONSTRAINT UC_Personnel UNIQUE (username)
)

INSERT INTO Personnel(U_Fname,U_Lname,U_MI,Email,gender,username,password,position,image) 
VALUES('James','Tomambid','P','shenztomambid@gmail.com','Male','admin','admin123','Midwife',null)

create table Patient(
P_ID serial primary key,
P_Fname varchar(50),
P_Lname varchar(50),
P_MI char(1),
P_Age varchar(10),
P_Gender varchar(10),
P_Contact varchar(11),
P_Birthdate varchar(50),
p_Address varchar(550),
p_Occupation varchar(50),
philHealthNo varchar(12),
attendant varchar(50),
service_avail varchar(50),
diagnosis varchar(550)
)

drop table Patient

ALTER SEQUENCE patient_p_id_seq RESTART WITH 1
ALTER SEQUENCE medicine_med_id_seq RESTART WITH 1
drop table Patient
delete from medicine

create table Medicine(
Med_ID serial primary key,
Med_Name varchar(50)
)

Insert into Medicine(Med_Name) VALUES ('NONE');
Insert into Medicine (Med_Name) VALUES ('Oxytocin');
Insert into Medicine (Med_Name) VALUES ('Amoxicillin');
Insert into Medicine (Med_Name) VALUES ('Mefenamic');

select * from medicine

create table Contraceptive(
Con_ID serial primary key,
Con_Name varchar(50)
)

Insert into Contraceptive(con_name) VALUES ('NONE');
Insert into Contraceptive (con_name) VALUES ('Trust Pills');
Insert into Contraceptive (con_name) VALUES ('Trust Condom');

create table Medicine_Avail(  
MA_ID serial primary key,
p_name varchar(50),
med_name varchar(50),
con_name varchar(50)
)





