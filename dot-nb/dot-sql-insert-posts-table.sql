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