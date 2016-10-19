DROP TABLE m8 cascade constraints;
DROP TABLE message cascade constraints;
DROP TABLE schoolclass cascade constraints;
DROP TABLE files cascade constraints;
DROP TABLE period cascade constraints;
DROP TABLE lesson cascade constraints;
DROP TABLE school cascade constraints;
DROP TABLE subject cascade constraints;
DROP TABLE teacher cascade constraints;
DROP TABLE teacher_trains_subject cascade constraints;

CREATE TABLE school(
  ID INTEGER PRIMARY KEY,
  identifier VARCHAR2(100)
);

CREATE TABLE m8(
  ID INTEGER PRIMARY KEY,
  schoolID INTEGER,
  vorname VARCHAR2(100),
  nachname VARCHAR2(100),
  email VARCHAR2(100),
  password VARCHAR2(100),
  hasVoted NUMBER(1,0),
  votes INTEGER
);

CREATE TABLE message(
  ID INTEGER PRIMARY KEY,
  mateID INTEGER,
  text VARCHAR2(150),
  sendDate DATE,
  FOREIGN KEY (mateID) REFERENCES m8(ID)
);

CREATE TABLE schoolClass(
  ID INTEGER PRIMARY KEY,
  schoolID INTEGER,
  president INTEGER,
  president_deputy INTEGER,
  name VARCHAR2(10),
  room VARCHAR2(10),
  FOREIGN KEY (schoolID) REFERENCES school(ID),
  FOREIGN KEY (president) REFERENCES m8(ID),
  FOREIGN KEY (president_deputy) REFERENCES m8(ID)
);



CREATE TABLE files(
  ID INTEGER PRIMARY KEY,
  schoolClassID INTEGER,
  name VARCHAR2(20),
  contentPath VARCHAR2(100),
  uploadDate DATE,
  FOREIGN KEY (schoolClassID) REFERENCES schoolclass(ID)
);

CREATE TABLE period(
  id INTEGER PRIMARY KEY,
  schoolClassID INTEGER,
  periodNumber INTEGER,
  fromTime DATE,
  toTime DATE,
  FOREIGN KEY (schoolClassID) REFERENCES schoolClass(ID)
);

CREATE TABLE teacher(
  ID INTEGER PRIMARY KEY,
  identifier VARCHAR2(25)
);

CREATE TABLE subject(
  ID INTEGER PRIMARY KEY,
  identifier VARCHAR2(25)
);


CREATE TABLE teacher_trains_subject(
  subjectID INTEGER,
  teacherID INTEGER,
  FOREIGN KEY (subjectID) REFERENCES subject(ID),
  FOREIGN KEY (teacherID) REFERENCES teacher(ID),
  CONSTRAINT pk_teacher_trains_subject PRIMARY KEY (subjectID, teacherID)
);


CREATE TABLE lesson(
  ID INTEGER PRIMARY KEY,
  periodID INTEGER,
  subjectID INTEGER,
  teacherID INTEGER,
  weekday VARCHAR2(15),
  room VARCHAR2(20),
  FOREIGN KEY (periodID) REFERENCES period(ID),
  FOREIGN KEY (subjectID, teacherID) REFERENCES teacher_trains_subject(subjectID, teacherID)
);


INSERT INTO school VALUES(1, 'HTL Villach');

INSERT INTO m8 VALUES(1,1, 'Lukas', 'Kirchbaumer', '4test@danke.com', 'fkjfki3lrsdi', 0, NULL);
INSERT INTO m8 VALUES(2,1, 'Max', 'Haider', '4test@danke.com', 'sdf32r', 0, NULL);
INSERT INTO m8 VALUES(3,1, 'Sasa', 'KSon', '4test@danke.com', 'asdfsadf', 0, NULL);
INSERT INTO m8 VALUES(4,1, 'Sunny', 'Sunmountner', '4test@danke.com', 'aha', 0, NULL);

INSERT INTO message VALUES(1, 1 , 'This is great!', TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO schoolclass VALUES(1, 1,1,2, '5BHIFS', '304');
INSERT INTO schoolclass VALUES(2, 1,3,4, '4BHIFS', '306');

INSERT INTO period VALUES(1, 1, 1, TO_DATE('2000/01/01 08:00:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2000/01/01 08:50:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO period VALUES(2, 1, 2, TO_DATE('2000/01/01 08:50:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2000/01/01 09:40:00', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO teacher VALUES(1, 'ORG');
INSERT INTO teacher VALUES(2, 'REL');

INSERT INTO subject VALUES(1, 'POS1');
INSERT INTO subject VALUES(2, 'DBI2');

INSERT INTO teacher_trains_subject VALUES(1, 1);
INSERT INTO teacher_trains_subject VALUES(2, 1);
INSERT INTO teacher_trains_subject VALUES(2, 2);

INSERT INTO lesson VALUES(1, 1, 1, 1, 'Monday', '404');
INSERT INTO lesson VALUES(2, 2, 2, 1, 'Monday', '407');

COMMIT;
