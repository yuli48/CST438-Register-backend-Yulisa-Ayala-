INSERT INTO student VALUES 
(1,'test','test@csumb.edu',NULL,0),
(2,'david','dwisneski@csumb.edu',NULL,0),
(3,'tom', 'trebold@csumb.edu',NULL, 0);

INSERT INTO course VALUES 
(2020,'Fall',30157,1,'BUS 203 - Financial Accounting','We 6:00PM - 7:20PM','506','112','cchou@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',30163,1,'BUS 306 - Fundamentals of Marketing','Mo 11:00AM - 11:50AM','Library','1180','anariswari@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',30291,1,'BUS 304 - Business Communication, Pro-seminar & Critical Thinking','Mo 8:00AM - 9:50AM','506','108','kposteher@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',31045,1,'CST 363 - Introduction to Database Systems','MoWe 4:00PM - 5:50PM','506','104','dwisneski@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',31249,1,'CST 237 - Intro to Computer Architecture','TuTh 2:00PM - 3:50PM','506','104','sislam@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',31253,1,'BUS 307 - Finance','We 2:00PM - 3:50PM','506','112','hwieland@csumb.edu','2020-08-24','2020-12-13'),
(2020,'Fall',31747,1,'CST 238 - Introduction to Data Structures','Mo 2:00PM - 2:50PM','506','117','jgross@csumb.edu','2020-08-24','2020-12-13'),
(2021,'Fall',40442,1,'CST 363 - Introduction to Database Systems','MoWe 4:00PM - 5:50PM','506','104','dwisneski@csumb.edu', '2021-08-24', '201-12-13'),
(2021,'Fall',40443,1,'CST 438 - Software Engineering','TuTh 10:00AM-11:50AM','506','104','dwisneski@csumb.edu', '2021-08-24','2021-12-13')
;

insert into enrollment values 
(1, 1, 2020, 'Fall', 30157, null), 
(2, 1, 2020, 'Fall', 30163, null),
(3, 1, 2020, 'Fall', 31045, null),
(4, 2, 2020, 'Fall', 31045, null),
(5, 3, 2020, 'Fall', 31045, null),
(6, 3, 2021, 'Fall', 40443, null);

insert into user_table
(alias, email, password, role) values 
('user', 'user@csumb.edu', '$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue','STUDENT'),
('admin', 'admin@csumb.edu', '$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW' , 'ADMIN'),
('david', 'dwisneski@csumb.edu', '$2a$10$nJGqojB/5Uq1/tgip4zbduNi3pxmuz1cyRAFijkjQCqK4QhTM7eTq','ADMIN'),
('test', 'test@csumb.edu', '$2a$10$D12l/1y2vilIEYFZ7MIxeeNMWTbk3x9TBIigIuQsVDbSmNy13mXV6','STUDENT'),
('tom', 'trebold@csumb.edu', '$2a$10$KkJUIPevNiv8Iq7Bh3KCgeMjujd3YrKaedHopsWHcc2Fc3OxzoQKe','STUDENT');