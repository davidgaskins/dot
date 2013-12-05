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