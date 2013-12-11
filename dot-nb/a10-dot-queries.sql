-- the required sql queries for a10
-- David Gaskins
-- Tan Tran
-- David Martel

-- Query 1
-- List titles of all projects, 
-- and the number of commits they have done on them

SELECT projects.title, COUNT(commits.id) as numCommits
	FROM projects LEFT OUTER JOIN goals ON projects.id = goals.projectID
		INNER JOIN commits ON goals.id = commits.goalID
	GROUP BY projects.title;

-- Query 2
-- List emails of all Contributors who are working on more than one project.

SELECT contributors.email, COUNT(projects.ID) AS numProjects
	FROM contributors INNER JOIN workAssignments ON contributors.ID = workAssignments.contributorID
		INNER JOIN goals ON workAssignments.goalID = goals.ID
		INNER JOIN projects on goals.projectID = projects.ID
	GROUP BY contributors.email
	HAVING COUNT(projects.ID) > 1;

-- Query 3
-- List all contributors assigned to work on bugs, 
-- and number of bugs assigned to them.

SELECT contributors.fName, contributors.lName, contributors.email, COUNT(*) AS numAssignments
	FROM contributors INNER JOIN workAssignments 
		ON contributors.ID = workAssignments.contributorID
		INNER JOIN goals ON workAssignments.goalID = goals.ID
	WHERE goals.type = 'BUG'
	GROUP BY (contributors.ID);

-- Query 4
-- Find the most recent Post(s) of all the Projects.
-- note there could be multiple posts here, because they could have the 
-- same timestamp (unlikely).

SELECT posts.body, goals.title, contributors.email
	FROM posts INNER JOIN goals
		ON posts.goalID = goals.id
		INNER JOIN contributors
		ON posts.contributorID = contributors.id
		WHERE posts.dateAndTime >= ALL (
								SELECT posts.dateAndTime
									FROM posts
								);

-- Query 5
-- Find the name and phone numbers of all Managers of ongoing Projects.

SELECT contributors.fName, contributors.lName, phoneNumbers.phoneType, phoneNumbers.phoneNumber
	FROM projects INNER JOIN managementAssignments
		ON projects.id = managementAssignments.projectID
		INNER JOIN contributors
			ON managementAssignments.contributorID = contributors.id
		INNER JOIN phoneNumbers
			ON contributors.id = phoneNumbers.contributorID
		WHERE projects.id NOT IN(
					SELECT projects.id
					FROM projects
						WHERE projects.dateToEnd > NOW()
					);
-- Query 6
-- List the emails of all Contributors to projects that do not have managers.
SELECT contributors.email, projects.title
FROM contributors INNER JOIN workAssignments 
		ON contributors.ID = workAssignments.contributorID
	INNER JOIN goals
		ON workAssignments.goalID = goals.ID
	INNER JOIN projects
		ON goals.projectID = projects.ID
	WHERE projects.id NOT IN(
						SELECT projects.id
						FROM projects INNER JOIN managementAssignments
						ON projects.id = managementAssignments.projectID);


