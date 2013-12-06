

CREATE TABLE goalPriorities
(
	goalPriority CHAR(20) NOT NULL PRIMARY KEY
);
INSERT INTO goalPriorities VALUES
	('CRITICAL'),
	('MEDIUM'),
	('LOW');

CREATE TABLE goalTypes
(
	goalType CHAR(20) NOT NULL PRIMARY KEY
);
INSERT INTO goalTypes VALUES
	('BUG'),
	('IMPROVEMENT'),
	('LONG-TERM');
	
CREATE TABLE goalStatuses
(
	goalStatus CHAR(20) NOT NULL PRIMARY KEY
);
INSERT INTO goalStatuses VALUES
	('OPEN'),
	('CLOSED'),
	('NOFIX'),
	('ASSIGNED'),
	('DUPLICATE'),
	('POSTPONED');