CREATE TABLE goalPriorities
(
	goalPriority CHAR(20) NOT NULL PRIMARY KEY
);
INSERT INTO goalPriorities VALUES
	('Critical'),
	('Medium'),
	('Low');

CREATE TABLE goalTypes
(
	goalType CHAR(20) NOT NULL PRIMARY KEY
);
INSERT INTO goalTypes VALUES
	('Bug'),
	('Improvement'),
	('Long-term');
	
CREATE TABLE goalStatuses
(
	goalStatus CHAR(20) NOT NULL PRIMARY KEY
);
INSERT INTO goalStatuses VALUES
	('Open'),
	('Closed'),
	('Nofix');
	('Assigned');
	('Duplicate');
	('Postponed');