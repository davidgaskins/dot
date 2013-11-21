DROP TABLE managementAssignments;
DROP TABLE posts;
DROP TABLE goals;
DROP TABLE workAssignments;
DROP TABLE phoneNumbers;
DROP TABLE contributors;
DROP TABLE changes;
DROP TABLE commits;
DROP TABLE projects;



CREATE TABLE projects(
	title VARCHAR(20) (NOT NULL),
	dateToEnd DATE,
	description TEXT,
	startDate DATE (NOT NULL),
	id INT (NOT NULL),
	CONSTRAINT projects_pk PRIMARY KEY (id),
	CONSTRAINT projects_ck1 UNIQUE (title, startDate)
);
CREATE TABLE contributors(
	id INT (NOT NULL),
	fName VARCHAR(15),
	lName VARCHAR(15),
	email VARCHAR(30) (NOT NULL),
	CONSTRAINT contributors_pk PRIMARY KEY(id),
	CONSTRAINT contributors_ck UNIQUE (email)
);
CREATE TABLE managementAssignments(
	dateStarted DATE (NOT NULL),
	dateToEnd DATE,
	finished BOOLEAN,
	projectID INT (NOT NULL),
	contributorID INT (NOT NULL,
	CONSTRAINT managementAssignments_pk PRIMARY KEY (projectID, contributorID, dateStarted),
	CONSTRAINT managementAssignments_fk FOREIGN KEY (projectID) REFERENCES projects (id) ON DELETE CASCADE,
	CONSTRAINT managementAssignments_fk2 FOREIGN KEY (contributorID) REFERENCES contributors (id) ON DELETE CASCADE
);
CREATE TABLE phoneNumbers(
	contributorID INT (NOT NULL),
	phoneNumber VARCHAR(11) (NOT NULL),
	phoneType VARCHAR(10) (NOT NULL),
	CONSTRAINT phoneNumbers_pk PRIMARY KEY (contributorID, phoneNumber, phoneType),
	CONSTRAINT phoneNumbers_fk FOREIGN KEY (contributorID) REFERENCES contributors (id) ON DELETE CASCADE
);
CREATE TABLE goals(
	id INT (NOT NULL),
	title VARCHAR(20) (NOT NULL),
	description TEXT (NOT NULL),
	priority PRIORITY,
	type TYPE,
	status STATUS,
	dateCreated DATE (NOT NULL),
	dateUpdated DATE,
	dateToEnd DATE,
	projectID INT,
	parentGoalID INT, -- can be null
	CONSTRAINT goals_pk PRIMARY KEY(id),
	CONSTRAINT goals_ck UNIQUE (dateCreated, title, description),
	CONSTRAINT goals_fk FOREIGN KEY (parentGoalID) REFERENCES goals (id) ON DELETE CASCADE
);
CREATE TABLE posts (
	body TEXT,
	datePosted DATE (NOT NULL),
	time TIME (NOT NULL),
	contributorID INT (NOT NULL), --need to change the relational scheme
	goalID INT,
	CONSTRAINT posts_pk PRIMARY KEY (datePosted, time, goalID),
	CONSTRAINT posts_fk FOREIGN KEY (goalID) REFERENCES goals(id) ON DELETE CASCADE
);
CREATE TABLE workAssignments(
	dateStarted DATE (NOT NULL),
	dateToEnd DATE,
	finished BOOLEAN,
	goalID (NOT NULL),
	contributorID (NOT NULL),
	CONSTRAINT workAssignments PRIMARY KEY(dateStarted, goalID, contributorID)
);
CREATE TABLE commits(
	contributorID INT, --should this be not null? update the relational scheme
	projectID INT, 	--should this be not null? 
	commitDate DATE,
	time TIME, 
	description TEXT,
	id INT (NOT NULL), 
	CONSTRAINT changes_pk PRIMARY KEY(id),
	CONSTRAINT changes_fk FOREIGN KEY(contributorID) REFERENCES contributors(id) ON DELETE CASCADE,
	CONSTRAINT changes_fk FOREIGN KEY(projectID) REFERENCES projects(id) ON DELETE CASCADE, 
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