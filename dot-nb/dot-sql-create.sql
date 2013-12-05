DROP TABLE posts;
DROP TABLE changes;
DROP TABLE commits;
DROP TABLE managementAssignments;
DROP TABLE workAssignments;
DROP TABLE goals;
DROP TABLE phoneNumbers;
DROP TABLE contributors;
DROP TABLE projects;


-- the following 9 tables are for our data
CREATE TABLE projects(
	title VARCHAR(20) NOT NULL,
	dateToEnd TIMESTAMP,
	description TEXT,
	dateStarted TIMESTAMP NOT NULL,
	id INT NOT NULL AUTO_INCREMENT,
	
	--  ManagementAssignment and Goal both reference Project.
	--  Use surrogate key "id" to reduce memory usage, and
	--  because part of Project's title may change
	CONSTRAINT projects_pk PRIMARY KEY (id),
	
	--  The client may start a Project, not complete it, and start a new one later
	CONSTRAINT projects_ck1 UNIQUE (title, dateStarted),
	
	--  To prevent unwanted changes when they are too late,
	--  this constraint forces the client to update a Project's
	--  due date when it is overdue.
	CONSTRAINT valid_date_range CHECK (dateToEnd >= dateStarted)
);
CREATE TABLE contributors(
	id INT NOT NULL AUTO_INCREMENT,
	fName VARCHAR(15),
	lName VARCHAR(15),
	email VARCHAR(30) NOT NULL, 
	CONSTRAINT contributors_pk PRIMARY KEY(id),
	--  Contributors must be able to be contacted through email
	--  Commit, Post, ManagementAssignment, and WorkAssignment
	--  all reference Contributor. Use surrogate key "id" to 
	--  reduce memory usage, and becaue CK email may change
	--  Assume no Contributors share email
	CONSTRAINT contributors_ck UNIQUE (email)
);
CREATE TABLE managementAssignments(
	dateStarted TIMESTAMP NOT NULL,
	dateToEnd TIMESTAMP,
	
	--  If a Project is still being worked on past its dateFinished,
	--  its managers (the Contributors in this ManagementAssignment) are not finished
	finished BOOLEAN, 
	
	projectID INT NOT NULL,
	contributorID INT NOT NULL ,
	
	--  A Contributor may Manage a Project in many time periods
	CONSTRAINT managementAssignments_pk PRIMARY KEY (projectID, contributorID, dateStarted),
	
	--  Deleting a Project is a serious action, but it means the client
	--  wants to delete all of its history, including this managementAssignment
	CONSTRAINT managementAssignments_fk FOREIGN KEY (projectID) 
		REFERENCES projects (id) ON DELETE CASCADE,
		
	--  Same for contributor: delete all of a contributor's history with the project
	CONSTRAINT managementAssignments_fk2 FOREIGN KEY (contributorID) 
		REFERENCES contributors (id) ON DELETE CASCADE
);
CREATE TABLE phoneNumbers(
	contributorID INT NOT NULL,
	
	--  Assume format "(123) 456-7890 x1234"
	phoneNumber CHAR(18) NOT NULL,
	phoneType ENUM('CELL', 'HOME', 'WORK'),
	
	--  Each Contributor has many phone numbers
	CONSTRAINT phoneNumbers_pk PRIMARY KEY (contributorID, phoneNumber),
	
	--  If a Contributor is deleted, the Project and its Contributors
	--  do not need to contact him anymore. This implies that client
	--  only deletes Contributor for a serious reason, such as banning
	--  or an accidentally created Contributor
	CONSTRAINT phoneNumbers_fk FOREIGN KEY (contributorID) 
		REFERENCES contributors (id) ON DELETE CASCADE
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
	parentGoalID INT, --  can be null because top level Goals don't need have to have a parent
	
	--  Goal can reference many parent Goals, so reduce memory usage by using ID
	CONSTRAINT goals_pk PRIMARY KEY(id),
	
	--  A Goal may be created, not completed, then raised again later
	CONSTRAINT goals_ck UNIQUE (dateCreated, title),
	
	--  If a Goal is deleted, all of the subGoals that contribute to its
	--  completion do not matter either
	CONSTRAINT goals_fk FOREIGN KEY (parentGoalID) 
		REFERENCES goals (id) ON DELETE CASCADE
);
CREATE TABLE posts (
	body TEXT,
	dateAndTime TIMESTAMP NOT NULL,
	contributorID INT NOT NULL, 
	goalID INT,
	
	--  Two Posts may be posted by different Contributors at the same time on the same goal
	CONSTRAINT posts_pk PRIMARY KEY (dateAndTime, goalID, contributorID),
	
	--  If a Goal is deleted, no discussion needs to be made about how to solve it. 
	--  This implies that client only deletes Goal for a serious reason, such as
	--  the fact that it is now completely irrelevant
	CONSTRAINT posts_fk FOREIGN KEY (goalID) 
		REFERENCES goals(id) ON DELETE CASCADE,
		
	CONSTRAINT posts_fk FOREIGN KEY (contributorID) 
		REFERENCES contributors(id) ON DELETE CASCADE
);
CREATE TABLE workAssignments(
	dateStarted TIMESTAMP NOT NULL,
	dateToEnd TIMESTAMP,
	
	--  A work assignment may be past its dateToEnd, but still not finished
	finished BOOLEAN,
	
	goalID INT NOT NULL,
	contributorID INT NOT NULL,
	
	--  A Contributor may work on a Goal in many time periods
	CONSTRAINT workAssignments_pk PRIMARY KEY(dateStarted, goalID, contributorID)
);
CREATE TABLE commits(
	contributorID INT NOT NULL,
	goalID INT NOT NULL, 
	commitDate TIMESTAMP,
	description TEXT,
	id INT NOT NULL AUTO_INCREMENT, 
	
	--  One Contributor may reference many Commits, so reduce memory usage using surrogate key ID
	CONSTRAINT changes_pk PRIMARY KEY(id),
	
	--  Do not delete a Contributor if he has made Commits
	CONSTRAINT changes_fk1 FOREIGN KEY(contributorID) 
		REFERENCES contributors(id) ON DELETE NO ACTION,
		
	--  Do not delete a Goal if Commits have been made to it
	CONSTRAINT changes_fk2 FOREIGN KEY(goalID) 
		REFERENCES goals(id) ON DELETE NO ACTION, 

	-- it is impossible for the same contributor to make a commit at precisely the same time

	CONSTRAINT changes_ck UNIQUE (commitDate, contributorID)
);
CREATE TABLE changes(
	fileAdjusted VARCHAR(20) NOT NULL,
	bodyOfDiff TEXT,
	checkSum CHAR(10),
	commitID INT NOT NULL,
	
	--  One Commit makes Changes to many files
	CONSTRAINT changes_pk PRIMARY KEY(commitID, fileAdjusted), 
	
	--  Commit can only be deleted if it is the latest Commit.
	--  In that case, delete its changes too.
	CONSTRAINT changes_fk FOREIGN KEY(commitID) 
		REFERENCES commits(id) ON DELETE CASCADE
);
