--DROP TABLE managementAssignments;
--DROP TABLE posts;
--DROP TABLE goals;
--DROP TABLE workAssignments;
--DROP TABLE phoneNumbers;
--DROP TABLE contributors;
--DROP TABLE changes;
--DROP TABLE commits;
--DROP TABLE projects;

--runs up to goals
--droping tables order needs to be changed



--the following 9 tables are for our data
CREATE TABLE projects(
	title VARCHAR(20) NOT NULL,
	dateToEnd TIMESTAMP,
	description TEXT,
	dateStarted DATE NOT NULL,
	id INT NOT NULL AUTO_INCREMENT,
	CONSTRAINT projects_pk PRIMARY KEY (id),
	
	-- The client may start a Project, not complete it, and start a new one later
	CONSTRAINT projects_ck1 UNIQUE (title, dateStarted),
	CONSTRAINT valid_data_range CHECK (dateToEnd >= dateStarted)
);
CREATE TABLE contributors(
	id INT NOT NULL AUTO_INCREMENT,
	fName VARCHAR(15),
	lName VARCHAR(15),
	email VARCHAR(30) NOT NULL, -- Contributors must be able to be contacted thru email
	CONSTRAINT contributors_pk PRIMARY KEY(id),
	
	-- Assume no Contributors share email
	CONSTRAINT contributors_ck UNIQUE (email)
);
CREATE TABLE managementAssignments(
	dateStarted TIMESTAMP NOT NULL,
	dateToEnd TIMESTAMP,
	
	-- If a Project is still being worked on past its dateFinished,
	-- its managers (the Contributors in this ManagementAssignment) are not finished
	finished BOOLEAN, 
	
	projectID INT NOT NULL,
	contributorID INT NOT NULL ,
	CONSTRAINT managementAssignments_pk PRIMARY KEY (projectID, contributorID, dateStarted),
	CONSTRAINT managementAssignments_fk FOREIGN KEY (projectID) REFERENCES projects (id) ON DELETE CASCADE,
	CONSTRAINT managementAssignments_fk2 FOREIGN KEY (contributorID) REFERENCES contributors (id) ON DELETE CASCADE
);
CREATE TABLE phoneNumbers(
	contributorID INT NOT NULL,
	
	-- Assume format "(123) 456-7890 x1234"
	phoneNumber CHAR(18) NOT NULL,
	phoneType ENUM('CELL', 'HOME', 'WORK'),
	
	CONSTRAINT phoneNumbers_pk PRIMARY KEY (contributorID, phoneNumber, phoneType),
	CONSTRAINT phoneNumbers_fk FOREIGN KEY (contributorID) REFERENCES contributors (id) ON DELETE CASCADE
);
CREATE TABLE goals(
	id INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(20) NOT NULL,
	description TEXT NOT NULL,
	priority ENUM('CRITICAL', 'MEDIUM', 'LOW'),
	type ENUM('BUG', 'IMPROVEMENT', 'LONG-TERM'),
	status ENUM('OPEN', 'CLOSED', 'NOFIX', 'ASSIGNED', 'DUPLICATE', 'POSTPONED'),
	dateCreated TIMESTAMP NOT NULL,
	dateUpdated TIMESTAMP,
	dateToEnd TIMESTAMP,
	projectID INT,
	parentGoalID INT, -- can be null because it doesn't have to have a parent
	--do we need do have this be a foreign key that references itself?
	CONSTRAINT goals_pk PRIMARY KEY(id),
	
	-- A Goal may be created, not completed, then raised again later
	CONSTRAINT goals_ck UNIQUE (dateCreated, title),
	CONSTRAINT goals_fk FOREIGN KEY (parentGoalID) REFERENCES goals (id) ON DELETE CASCADE
);
CREATE TABLE posts (
	body TEXT,
	dateAndTime TIMESTAMP NOT NULL,
	contributorID INT NOT NULL, --need to change the relational scheme
	goalID INT,
	CONSTRAINT posts_pk PRIMARY KEY (datePosted, time, goalID),
	CONSTRAINT posts_fk FOREIGN KEY (goalID) REFERENCES goals(id) ON DELETE CASCADE
);
CREATE TABLE workAssignments(
	dateStarted TIMESTAMP NOT NULL,
	dateToEnd TIMESTAMP,
	
	-- A work assignment may be past its dateToEnd, but still not finished
	finished BOOLEAN,
	
	goalID NOT NULL,
	contributorID NOT NULL,
	CONSTRAINT workAssignments PRIMARY KEY(dateStarted, goalID, contributorID)
);
CREATE TABLE commits(
	contributorID INT, --should this be not null? update the relational scheme
	goalID INT, 	--should this be not null? 
	commitDate TIMESTAMP,
	description TEXT,
	id INT NOT NULL AUTO_INCREMENT, 
	CONSTRAINT changes_pk PRIMARY KEY(id),
	CONSTRAINT changes_fk FOREIGN KEY(contributorID) REFERENCES contributors(id) ON DELETE CASCADE,
	CONSTRAINT changes_fk FOREIGN KEY(goalID) REFERENCES goalss(id) ON DELETE CASCADE, 

	-- Two Contributors may make Commits at the same date+time, but their commits
	-- will have different descriptions
	CONSTRAINT changes_ck UNIQUE (commitDate, time, description)
);
CREATE TABLE changes(
	fileAdjusted VARCHAR(20) NOT NULL,
	bodyOfDiff TEXT,
	checkSum CHAR(10),
	commitID INT NOT NULL,
	CONSTRAINT changes_pk PRIMARY KEY(commitID, fileAdjusted), 
	CONSTRAINT changes_fk FOREIGN KEY(commitID) REFERENCES commits(id) ON DELETE CASCADE
);
