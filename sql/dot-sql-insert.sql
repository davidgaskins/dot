INSERT INTO projects (title, dateToEnd, description, dateStarted) VALUES
	('dot', '2013-12-9 12:23:55', 'an issue tracker that also tracks source code ', '2013-10-19 12:23:55'),
	('eugene', '2013-11-20 12:23:55', 'a 2d side scrolling game implented for CECS 343', '2013-9-19 12:23:55'),
	('caffeine complex', '2013-5-10 12:23:55', 'a 2d graphics engine implemented in C++ for CECS 343', '2013-2-19 12:23:55'),
	('manhatten project', '1945-7-20 12:23:55', 'the American attempt to develop a nuclear bomb', '1942-11-19 12:23:55'),
	('amazon prime air', '2015-7-20 12:23:55', 'attempt to get amazon products from order to delivery in 30 minutes', '2013-11-19 12:23:55');
INSERT INTO contributors (fName,lName, email) VALUES
	('tan', 'tran', 'tantran@student.csulb.edu' ),
	('david', 'gaskins', 'davidgaskins@student.csulb.edu' ),
	('david', 'martel', 'davidmartel@student.csulb.edu' ),
	('david', 'nuon', 'davidnuon@student.csulb.edu' ),
	('david', 'nguyen', 'davidnguyen@student.csulb.edu' );
INSERT INTO managementAssignments VALUES
	--started, end, finished, proj, cont
	('2013-10-19 12:23:55', '2013-11-19 12:23:55', FALSE, 1, 1),
	('2013-9-17 12:23:55', '2013-12-2 12:23:55', TRUE, 2, 2),
	('2013-2-19 12:23:55', '2013-3-19 12:23:55', TRUE, 3, 3),
	('2013-3-19 12:23:55', '2013-4-19 12:23:55', TRUE, 3, 4),
	('2013-4-19 12:23:55', '2013-5-10 12:23:55', TRUE, 3, 5);
INSERT INTO phoneNumbers VALUES	
	(1, '17147858750', 'CELL'),
	(1, '17143790719', 'WORK'),
	(1, '17145550123', 'HOME'),
	(2, '17148985087', 'WORK'),
	(2, '17145955646', 'CELL');
INSERT INTO goals (title, description, priority, type, status, dateCreated, dateUpdated, dateToEnd, projectID, parentGoalID) VALUES
	('sql insert statements', 'required sql insert statements for this project', 'CRITICAL', 'IMPROVEMENT', 'CLOSED', '2013-11-14 12:23:55', '2013-11-19 12:23:55', '2013-12-2 12:23:55', 1, NULL),	
	('sequence diagrams', 'sequence diagrams required for binder ', 'CRITICAL', 'IMPROVEMENT', 'CLOSED', '2013-12-2 12:23:55', '2013-12-2 12:23:55', '2013-11-16 12:23:55', 2, NULL),
	('documentation', 'the API documentation', 'CRITICAL', 'IMPROVEMENT', 'CLOSED', '2013-12-2 12:23:55', '2013-12-2 12:23:55', '2013-11-17 12:23:55', 2, NULL),
	('documentation', 'API documenation for caffeine complex', 'CRITICAL', 'IMPROVEMENT', 'CLOSED', '2013-5-10 12:23:55', '2013-3-10 12:23:55', '2013-2-29 12:23:55', 3, NULL),
	('back end', 'backend complete for caffeine complex', 'CRITICAL', 'IMPROVEMENT', 'OPEN', '2013-4-10 12:23:55', '2013-4-9 12:23:55', '2013-2-20 12:23:55', 3, NULL);
INSERT INTO posts VALUES
	('we need to add real data', '2013-12-2 12:23:55', 1, 1),
	('do not forget that we need two sequence diagrams', '2013-11-15 12:24:55', 2, 2),
	('we will be using perl', '2013-11-14 12:25:55', 3, 3),
	('we will be using python', '2013-11-14 12:26:55', 4, 4),
	('changed to being implemented in c++', '2013-3-1 12:27:55', 5, 5);
INSERT INTO workAssignments VALUES
--the first three values represent us working on this sql insert statement goal
	('2013-11-17 12:23:55', '2013-12-5 12:30:55', TRUE, 1, 1),
	('2013-11-17 12:23:55', '2013-12-5 12:23:55', TRUE, 1, 2),
	('2013-11-17 12:23:55', '2013-12-5 12:23:55', TRUE, 1, 3),
--this row represents david g working on sequence diagrams for his 343 project
	('2013-11-15 12:23:55', '2013-12-2 12:23:55', TRUE, 2, 2),
--this row represents david g working on documenation for his 343 project
	('2013-11-14 12:23:55', '2013-12-2 12:23:55', TRUE, 3, 2);
INSERT INTO commits (contributorID, goalID, commitDate, description) VALUES
	(1, 1, '2013-11-18 12:23:55', 'unfinshed syntax for projects insertion'),
	(1, 1, '2013-11-19 12:23:55', 'finished syntax for projects insertion'),
	(1, 1, '2013-11-24 12:23:55', 'unfinished syntax for phoneNumbers, goals posts insertion'),
	(1, 1, '2013-11-29 12:23:55', 'finished syntax for phoneNumbers insertion'),
	(1, 1, '2013-12-4 12:23:55', 'finished all the rest of the syntax insertion');
INSERT INTO changes VALUES
--for the body of the diffs, for readablity reasons  we have extracted lines from diffs and only used those 
--this shows the progress of the dot-sql-insert.sql document
	('dot-sql-insert.sql', '<INSERT INTO projects VALUES >INSERT INTO projects(title, dateToEnd, description, dateStarted) VALUES  ', 'ae03lje9v0', 1),
	('dot-sql-insert.sql', '<--started, end, finished, proj, cont > ', 'ae03lje9v1', 2),
	('dot-sql-insert.sql', '<INSERT INTO commits VALUES >INSERT INTO commits  ( contributorID, goalID, commitDate, description) VALUES ', 'ae03lje9v2', 3),
	('dot-sql-insert.sql', '<INSERT INTO goals VALUES >INSERT INTO goals (title, description, priority, type, status, dateCreated, dateUpdated, dateToEnd, projectID, parentGoalID) VALUES', 'ae03lje9v3', 4),
	('dot-sql-insert.sql', '<INSERT INTO contributors VALUES >INSERT INTO contributors (fName,lName, email) VALUES', 'ae03lje9v4', 5);