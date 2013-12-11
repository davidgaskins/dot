-- Sample queries for DOT by David Gaskins, David Martel, Tan Tran
-- 1. Each query must retrieve information from multiple tables (at least three) and should rename columns when necessary so that they are more descriptive than the name of the column in the table (when the column name is not descriptive).
	-- Guideline followed for all queries
-- 2. At least two queries must use outer joins appropriately.
	-- See: a) and 
-- 3. At least three of the queries must aggregate values for groups of rows. 
	-- One of these must be combined with the use of outer joins and aggregate a column that has some NULLs as a result of the outer joins. 
		-- See: a)
	-- One of these three queries must also use the HAVING clause appropriately.
		-- See: (b)
	-- Third query: see (c)
-- 4. Three of the queries must demonstrate different aspects of subqueries (e.g. one to find the entity that has the minimum (or maximum) value of a certain property, another to find the entity that satisfies all of a certain criteria -- e.g. using Universal quantifier, etc.). Vary your use of subqueries such that one query can find minimum (or maximum) of something, and the others must be used to meet criteria different from this.
	-- See: (d), (e), (f)
-- 5. At least one of the queries must use set difference in a non-trivial way. Recall that MySQL does not directly support the set difference operator, so you must do this via a subquery combined with the NOT IN operator or with the use of outer joins.
	-- See: (e), (f)
-- In Java program, editing a Post is child updating to Contributors
-- Inserting a Goal is child insertion to Projects
-- Deleting a Contributor is parent deletion (Contributor is parent to Posts, Commits, WorkAssignments, ManagementAssignments). 
-- Since ON DELETE NO UPDATE, Dot program will forbid a delete if Contributor is parent to anything, but allow it otherwise.

-- a) Get the titles of all projects and the number of commits ever done to each, including projects
-- with no commits
SELECT projects.title, COUNT(commits.ID) as numCommits
FROM projects LEFT OUTER JOIN goals ON projects.id = goals.projectID
INNER JOIN commits ON goals.id = commits.goalID
GROUP BY projects.title;

-- b) Get the emails of Contributors who have worked on one or more projects, past and present
	SELECT contributors.email, COUNT(projects.ID) AS numProjects
	FROM contributors INNER JOIN workAssignments ON contributors.ID = workAssignments.contributorID
    		INNER JOIN goals ON workAssignments.goalID = goals.ID
    		INNER JOIN projects on goals.projectID = projects.ID
	GROUP BY contributors.email
	HAVING COUNT(projects.ID > 1);

-- c) Get the names and emails of all Contributors who have worked on one or more bugs, past and present
SELECT contributors.fName, contributors.lName, contributors.email, COUNT(*) AS numAssignments
FROM contributors 
INNER JOIN workAssignments ON contributors.ID = workAssignments.contributorID
	INNER JOIN goals ON workAssignments.goalID = goals.ID
WHERE goals.type = 'BUG'
GROUP BY (contributors.ID)

-- d) Get the most recent post(s) and retrieve the post's text body, its title, and the poster's email
SELECT posts.body, goals.title, contributors.email
	FROM posts INNER JOIN goals
		ON posts.goalID = goals.id
	INNER JOIN contributors
		ON posts.contributorID = contributors.id
	WHERE posts.dateAndTime >= ALL (
		SELECT posts.dateAndTime
			FROM posts
);

-- e) Get the names and phone numbers of all Contributors who have managed,or are managing, projects that are ongoing
SELECT contributors.fName, contributors.lName, contributors.email, phoneNumbers.phoneType, phoneNumbers.phoneNumber
	FROM projects 
		INNER JOIN managementAssignments
			ON projects.id = managementAssignments.projectID
		INNER JOIN contributors
			ON managementAssignments.contributorID = contributors.id
		INNER JOIN phoneNumbers
			ON contributors.id = phoneNumbers.contributorID
	WHERE projects.id NOT IN(
			SELECT projects.id
			FROM projects
			WHERE projects.dateToEnd < NOW()
		);

-- f) Get the emails of all Contributors who are working on Projects that have never had Managers
SELECT contributors.email, projects.title
	FROM contributors 
		INNER JOIN workAssignments 
			ON contributors.ID = workAssignments.contributorID
		INNER JOIN goals
			ON workAssignments.goalID = goals.ID
		INNER JOIN projects
			ON goals.projectID = projects.ID
		WHERE projects.id NOT IN(
			SELECT projects.id
			FROM projects INNER JOIN managementAssignments
					ON projects.id = managementAssignments.projectID
);

