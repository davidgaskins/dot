DROP TABLE managementAssignments;
DROP TABLE posts;
DROP TABLE goals;
DROP TABLE workAssignments;
DROP TABLE phoneNumbers;
DROP TABLE contributors;
DROP TABLE changes;
DROP TABLE commits;
DROP TABLE projects;


--the following four tables create our enumerated data types
CREATE TABLE type(
	name ENUM('BUG', 'IMPROVEMENT', 'LONG-TERM')
);
CREATE TABLE status(
	name ENUM('OPEN', 'CLOSED', 'NOFIX', 'ASSIGNED', 'DUPLICATE', 'POSTPONED')
);
CREATE TABLE priority(
	name ENUM('CRITICAL', 'MEDIUM', 'LOW')
);
CREATE TABLE phType(
	name ENUM('CELL', 'HOME', 'WORK')
)
--the following 9 tables are for our data
CREATE TABLE projects(
	title VARCHAR(20) (NOT NULL),
	dateToEnd TIMESTAMP,
	description TEXT,
	dateStarted DATE (NOT NULL),
	id INT (NOT NULL) AUTO_INCREMENT,
	CONSTRAINT projects_pk PRIMARY KEY (id),
	CONSTRAINT projects_ck1 UNIQUE (title, startDate),
	CONSTRAINT valid_data_range CHECK (dateToEnd >= dateStarted)
);
CREATE TABLE contributors(
	id INT (NOT NULL) AUTO_INCREMENT,
	fName VARCHAR(15),
	lName VARCHAR(15),
	email VARCHAR(30) (NOT NULL),
	CONSTRAINT contributors_pk PRIMARY KEY(id),
	CONSTRAINT contributors_ck UNIQUE (email)
);
CREATE TABLE managementAssignments(
	dateStarted TIMESTAMP (NOT NULL),
	dateToEnd TIMESTAMP,
	finished BOOLEAN,
	projectID INT (NOT NULL),
	contributorID INT (NOT NULL) ,
	CONSTRAINT managementAssignments_pk PRIMARY KEY (projectID, contributorID, dateStarted),
	CONSTRAINT managementAssignments_fk FOREIGN KEY (projectID) REFERENCES projects (id) ON DELETE CASCADE,
	CONSTRAINT managementAssignments_fk2 FOREIGN KEY (contributorID) REFERENCES contributors (id) ON DELETE CASCADE
);
CREATE TABLE phoneNumbers(
	contributorID INT (NOT NULL),
	phoneNumber VARCHAR(11) (NOT NULL),
	phoneType phType,
	CONSTRAINT phoneNumbers_pk PRIMARY KEY (contributorID, phoneNumber, phoneType),
	CONSTRAINT phoneNumbers_fk FOREIGN KEY (contributorID) REFERENCES contributors (id) ON DELETE CASCADE
);
CREATE TABLE goals(
	id INT (NOT NULL) AUTO_INCREMENT,
	title VARCHAR(20) (NOT NULL),
	description TEXT (NOT NULL),
	priority PRIORITY,
	type TYPE,
	status STATUS,
	dateCreated TIMESTAMP(NOT NULL),
	dateUpdated TIMESTAMP,
	dateToEnd TIMESTAMP,
	projectID INT,
	parentGoalID INT, -- can be null because it doesn't have to have a parent
	--do we need do have this be a foreign key that references itself?
	CONSTRAINT goals_pk PRIMARY KEY(id),
	CONSTRAINT goals_ck UNIQUE (dateCreated, title, description),
	CONSTRAINT goals_fk FOREIGN KEY (parentGoalID) REFERENCES goals (id) ON DELETE CASCADE
);
CREATE TABLE posts (
	body TEXT,
	dateAndTime TIMESTAMP(NOT NULL),
	contributorID INT (NOT NULL), --need to change the relational scheme
	goalID INT,
	CONSTRAINT posts_pk PRIMARY KEY (datePosted, time, goalID),
	CONSTRAINT posts_fk FOREIGN KEY (goalID) REFERENCES goals(id) ON DELETE CASCADE
);
CREATE TABLE workAssignments(
	dateStarted TIMESTAMP (NOT NULL),
	dateToEnd TIMESTAMP,
	finished BOOLEAN,
	goalID (NOT NULL),
	contributorID (NOT NULL),
	CONSTRAINT workAssignments PRIMARY KEY(dateStarted, goalID, contributorID)
);
CREATE TABLE commits(
	contributorID INT, --should this be not null? update the relational scheme
	goalID INT, 	--should this be not null? 
	commitDate TIMESTAMP,
	description TEXT,
	id INT (NOT NULL) AUTO_INCREMENT, 
	CONSTRAINT changes_pk PRIMARY KEY(id),
	CONSTRAINT changes_fk FOREIGN KEY(contributorID) REFERENCES contributors(id) ON DELETE CASCADE,
	CONSTRAINT changes_fk FOREIGN KEY(goalID) REFERENCES goalss(id) ON DELETE CASCADE, 
	CONSTRAINT changes_ck UNIQUE (commitDate, time, description)
);
CREATE TABLE changes(
	fileAdjusted VARCHAR(20) (NOT NULL),
	bodyOfDiff TEXT,
	checkSum CHAR(10),
	commitID INT (NOT NULL),
	CONSTRAINT changes_pk PRIMARY KEY(commitID, fileAdjusted), 
	CONSTRAINT changes_fk FOREIGN KEY(commitID) REFERENCES commits(id) ON DELETE CASCADE
);