insert into Modality (ID, DESIGNATION, MINPARTICIPANTS, MINTIME, MODALITYTYPE) values (1,'Futebol 11', 22, 105, 'COLLECTIVE')
insert into Modality (ID, DESIGNATION, MINPARTICIPANTS, MINTIME, MODALITYTYPE) values (2,'Futsal' ,10, 80, 'COLLECTIVE')
insert into Modality (ID, DESIGNATION, MINPARTICIPANTS, MINTIME, MODALITYTYPE) values (3,'Rugby', 30, 80, 'COLLECTIVE')
insert into Modality (ID, DESIGNATION, MINPARTICIPANTS, MINTIME, MODALITYTYPE) values (4,'Andebol', 14, 60, 'COLLECTIVE')

insert into User (ID, DTYPE, ADDRESS, NAME, NIF, ESTATUTO, NHOURSRESERVATED, BIRTHDAY) values (1, 'IndividualUser',  'Rua dos Bolos' ,'Xico Velho', 168027852, NULL , NULL ,'1980-12-31')
insert into User (ID, DTYPE, ADDRESS, NAME, NIF, ESTATUTO, NHOURSRESERVATED, BIRTHDAY) values (2, 'IndividualUser',  'Rua dos Bolos' ,'Xico Novo', 168027852, NULL , NULL ,'2010-12-31')
insert into User(ID, DTYPE, ADDRESS, NAME, NIF, ESTATUTO, NHOURSRESERVATED, BIRTHDAY) values (3, 'ClubUser', 'Travessa das Gazelas', 'Os Leoes', 502618418, 'BRONZE', 0 , NULL)

INSERT INTO Installation(ID, MAXSTOCKING, NAME, PRICEHOUR) VALUES (1,40,'Estadio de Honra - Campo de Jogo',250)
INSERT INTO Installation_schedules(SCHEDULES_KEY, DAYTYPE, ENDTIME, STARTTIME, Installation_ID) VALUES (0,'WORKINGDAY','1970-01-01 23:30:00','1970-01-01 07:00:00',1)
INSERT INTO Installation_schedules(SCHEDULES_KEY, DAYTYPE, ENDTIME, STARTTIME, Installation_ID) VALUES (1,'NON_WORKINGDAY','1970-01-01 23:00:00','1970-01-01 08:00:00',1)
INSERT INTO Installation_Modality(Installation_ID, modalities_ID) VALUES (1,1)
INSERT INTO Installation_Modality(Installation_ID, modalities_ID) VALUES (1,2)
INSERT INTO Installation_Modality(Installation_ID, modalities_ID) VALUES (1,3)
INSERT INTO Installation(ID, MAXSTOCKING, NAME, PRICEHOUR) VALUES (2,40,'Estádio da Luz',250)
INSERT INTO Installation_schedules(SCHEDULES_KEY, DAYTYPE, ENDTIME, STARTTIME, Installation_ID) VALUES (0,'WORKINGDAY','1970-01-01 23:30:00','1970-01-01 07:00:00',2)
INSERT INTO Installation_schedules(SCHEDULES_KEY, DAYTYPE, ENDTIME, STARTTIME, Installation_ID) VALUES (1,'NON_WORKINGDAY','1970-01-01 23:00:00','1970-01-01 08:00:00',2)
INSERT INTO Installation_Modality(Installation_ID, modalities_ID) VALUES (2,1)
INSERT INTO Installation_Modality(Installation_ID, modalities_ID) VALUES (2,3)


