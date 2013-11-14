DROP TABLE projects;
DROP TABLE managementAssignments;
DROP TABLE contributors;
DROP TABLE posts;
DROP TABLE goals;
DROP TABLE workAssignments;
DROP TABLE contactInfo;
DROP TABLE changes;
DROP TABLE commits;

CREATE TABLE commits{
	id INT (NOT NULL), 
	description TEXT,
	time TIME,  	
	commitDate DATE,
	contributorID INT,
	projectID INT
	CONSTRAINT changes_pk PRIMARY KEY(id),
	CONSTRAINT changes_fk FOREIGN KEY(contributorID) REFERENCES contributors(id),
	CONSTRAINT changes_fk FOREIGN KEY(projectID) REFERENCES projects(id)



}
CREATE TABLE changes{
	fileAdjusted VARCHAR(20) (NOT NULL),
	body TEXT,
	checkSum CHAR(10),
	commitID INT (NOT NULL),
	CONSTRAINT changes_pk PRIMARY KEY(commitID, fileAdjusted), 
	CONSTRAINT changes_fk FOREIGN KEY(commitID) REFERENCES commits(id)
}