DROP TABLE managementAssignments;
DROP TABLE posts;
DROP TABLE goals;
DROP TABLE workAssignments;
DROP TABLE contactInfo;
DROP TABLE contributors;
DROP TABLE changes;
DROP TABLE commits;
DROP TABLE projects;



CREATE TABLE projects(
	id INT (NOT NULL),
	projectedEndDate DATE,
	description TEXT,
	startDate DATE,
	title VARCHAR(20),
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
	projectedEndDate DATE,
	finished BOOLEAN,
	projectID INT (NOT NULL),
	workerID INT (NOT NULL,
	CONSTRAINT managementAssignments_pk PRIMARY KEY (projectID, workerID, dateStarted),
	CONSTRAINT managementAssignments_fk FOREIGN KEY (projectID) REFERENCES projects (id),
	CONSTRAINT managementAssignments_fk2 FOREIGN KEY (workerID) REFERENCES workers (id)
);
CREATE TABLE contactInfo(
	contributorID INT (NOT NULL),
	phoneNumber VARCHAR(11) (NOT NULL),
	phoneType VARCHAR(10) (NOT NULL),
	CONSTRAINT contactInfo_pk PRIMARY KEY (contributorID, phoneNumber, phoneType),
	CONSTRAINT contactInfo_fk FOREIGN KEY (contributorID) REFERENCES contributors (id)
);
CREATE TABLE goals(
	priority PRIORITY,
	type TYPE,
	status STATUS,
	dateCreated DATE (NOT NULL),
	dateUpdate DATE,
	title VARCHAR(20) (NOT NULL),
	description TEXT (NOT NULL),
	projectedEndDate DATE,
	projectID INT,
	id INT (NOT NULL),
	parentGoalID INT,
	CONSTRAINT goals_pk PRIMARY KEY(id),
	CONSTRAINT goals_ck UNIQUE (dateCreated, title, description),
	CONSTRAINT goals_fk FOREIGN KEY (parentGoalID) REFERENCES goals (id)
);
CREATE TABLE posts (
	datePosted DATE (NOT NULL),
	body TEXT,
	time TIME (NOT NULL),
	workerID INT (NOT NULL),
	goalID INT,
	CONSTRAINT posts_pk PRIMARY KEY (datePosted, time, goalID),
	CONSTRAINT posts_fk FOREIGN KEY (goalID) REFERENCES goals(id)
);
CREATE TABLE workAssignments(
	dateStarted DATE (NOT NULL),
	projectedEndDate DATE,
	finished BOOLEAN,
	goalID (NOT NULL),
	workerID (NOT NULL),
	CONSTRAINT workAssignments PRIMARY KEY(dateStarted, goalID, workerID)
);
CREATE TABLE commits(
	id INT (NOT NULL), 
	description TEXT,
	time TIME,  	
	commitDate DATE,
	contributorID INT,
	projectID INT
	CONSTRAINT changes_pk PRIMARY KEY(id),
	CONSTRAINT changes_fk FOREIGN KEY(contributorID) REFERENCES contributors(id),
	CONSTRAINT changes_fk FOREIGN KEY(projectID) REFERENCES projects(id)
);
CREATE TABLE changes(
	fileAdjusted VARCHAR(20) (NOT NULL),
	body TEXT,
	checkSum CHAR(10),
	commitID INT (NOT NULL),
	CONSTRAINT changes_pk PRIMARY KEY(commitID, fileAdjusted), 
	CONSTRAINT changes_fk FOREIGN KEY(commitID) REFERENCES commits(id)
);