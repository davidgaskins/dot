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