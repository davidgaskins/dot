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