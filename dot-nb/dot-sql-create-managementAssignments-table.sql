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