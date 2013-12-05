CREATE TABLE contributors(
	id INT NOT NULL AUTO_INCREMENT,
	fName VARCHAR(15),
	lName VARCHAR(15),
	email VARCHAR(30) NOT NULL, 

	CONSTRAINT contributors_pk PRIMARY KEY(id),
	--  Contributors must be able to be contacted thru email
	--  Commit, Post, ManagementAssignment, and WorkAssignment
	--  all reference Contributor. Use surrogate key "id" to 
	--  reduce memory usage, and becaue CK email may change
	--  Assume no Contributors share email
	CONSTRAINT contributors_ck UNIQUE (email)
);