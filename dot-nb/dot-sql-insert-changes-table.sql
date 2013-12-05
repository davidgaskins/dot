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