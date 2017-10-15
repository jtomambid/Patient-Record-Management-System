/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Tomambid
 * Created: Oct 9, 2017
 */



create table Personnel(
U_ID serial primary key,
U_Fname varchar(50) not null,
U_Lname varchar(50) not null,
U_MI char(1) not null,
email varchar(50) not null,
gender varchar(10) not null,
username varchar(20) not null,
password varchar(20) not null,
position varchar(15) not null,
image bytea,
CONSTRAINT UC_PersonnelName UNIQUE(U_Fname,U_Lname,U_MI),
CONSTRAINT UC_Personnel UNIQUE (username)
)

select * from personnel

CREATE TABLE Audit (
    audit_id serial primary key,
    dateAudit varchar(50) not null,
    timeAudit  varchar(50) null,
    status varchar(550),
    U_ID int references Personnel(U_ID)
)


ALTER SEQUENCE audit_audit_id_seq RESTART WITH 1