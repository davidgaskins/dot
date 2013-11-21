INSERT INTO projects (	title, dateToEnd, description, dateStarted) VALUES
	('proj1', '2013-11-20 12:23:55', 'this is project 1', '2013-11-19 12:23:55'),
	('proj2', '2013-11-20 12:23:55', 'this is project 2', '2013-11-19 12:23:55'),
	('proj3', '2013-11-20 12:23:55', 'this is project 3', '2013-11-19 12:23:55'),
	('proj4', '2013-11-20 12:23:55', 'this is project 4', '2013-11-19 12:23:55'),
	('proj5', '2013-11-20 12:23:55', 'this is project 5', '2013-11-19 12:23:55');
INSERT INTO contributors VALUES
	(NULL, 'david', 'gaskins', 'davidgaskins@student.csulb.edu' ),
	(NULL, 'david', 'martel', 'davidmartel@student.csulb.edu' ),
	(NULL, 'tan', 'tran', 'tantran@student.csulb.edu' ),
	(NULL, 'david', 'nuon', 'davidnuon@student.csulb.edu' ),
	(NULL, 'david', 'nguyen', 'davidnguyen@student.csulb.edu' );
INSERT INTO managementAssignments VALUES
	('2013-11-18 12:23:55', '2013-11-19 12:23:55', 'FALSE', 1, 1),
	('2013-11-17 12:23:55', '2013-11-18 12:23:55', 'FALSE', 1, 1),
	('2013-11-16 12:23:55', '2013-11-17 12:23:55', 'FALSE', 1, 1),
	('2013-11-15 12:23:55', '2013-11-16 12:23:55', 'FALSE', 1, 1),
	('2013-11-14 12:23:55', '2013-11-15 12:23:55', 'FALSE', 1, 1);
INSERT INTO phoneNumbers VALUES	
	(1, '17147858750', 'CELL'),
	(1, '17147858751', 'CELL'),
	(1, '17147858752', 'CELL'),
	(1, '17147858753', 'CELL'),
	(1, '17147858754', 'CELL');
INSERT INTO goals VALUES
	(NULL, 'goal1', 'text for goal1', 'CRITICAL', 'IMPROVEMENT', 'OPEN', '2013-11-14 12:23:55', '2013-11-14 12:23:55', '2013-11-15 12:23:55', 1, 1),	
	(NULL, 'goal2', 'text for goal2', 'CRITICAL', 'IMPROVEMENT', 'OPEN', '2013-11-14 12:23:55', '2013-11-14 12:23:55', '2013-11-15 12:23:55', 1, 1),
	(NULL, 'goal3', 'text for goal3', 'CRITICAL', 'IMPROVEMENT', 'OPEN', '2013-11-14 12:23:55', '2013-11-14 12:23:55', '2013-11-15 12:23:55', 1, 1),
	(NULL, 'goal4', 'text for goal4', 'CRITICAL', 'IMPROVEMENT', 'OPEN', '2013-11-14 12:23:55', '2013-11-14 12:23:55', '2013-11-15 12:23:55', 1, 1),
	(NULL, 'goal5', 'text for goal5', 'CRITICAL', 'IMPROVEMENT', 'OPEN', '2013-11-14 12:23:55', '2013-11-14 12:23:55', '2013-11-15 12:23:55', 1, 1);
INSERT INTO posts VALUES
	('text for post1', '2013-11-14 12:23:55', 1, 1),
	('text for post2', '2013-11-14 12:23:55', 1, 1),
	('text for post3', '2013-11-14 12:23:55', 1, 1),
	('text for post4', '2013-11-14 12:23:55', 1, 1),
	('text for post5', '2013-11-14 12:23:55', 1, 1);
INSERT INTO workAssignments VALUES
	('2013-11-18 12:23:55', '2013-11-19 12:23:55', 'FALSE', 1, 1),
	('2013-11-17 12:23:55', '2013-11-18 12:23:55', 'FALSE', 1, 1),
	('2013-11-16 12:23:55', '2013-11-17 12:23:55', 'FALSE', 1, 1),
	('2013-11-15 12:23:55', '2013-11-16 12:23:55', 'FALSE', 1, 1),
	('2013-11-14 12:23:55', '2013-11-15 12:23:55', 'FALSE', 1, 1);
INSERT INTO commits VALUES
	(1, 1, '2013-11-18 12:23:55', 'text for commit1', NULL),
	(1, 1, '2013-11-19 12:23:55', 'text for commit2', NULL),
	(1, 1, '2013-11-20 12:23:55', 'text for commit3', NULL),
	(1, 1, '2013-11-21 12:23:55', 'text for commit4', NULL),
	(1, 1, '2013-11-22 12:23:55', 'text for commit5', NULL);
INSERT INTO changes VALUES
	('file1', )