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