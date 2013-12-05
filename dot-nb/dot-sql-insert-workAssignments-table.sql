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