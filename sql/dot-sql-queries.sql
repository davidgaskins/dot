-- 7. Get the name of each file for a commit
SELECT fileAdjusted
FROM Commits INNER JOIN Changes ON Commits.ID = Changes.commitID;

-- 8a. Get the body of a file from scratch
SELECT Changes.body
FROM Commits INNER JOIN Changes ON Commits.ID = Changes.commitID
WHERE fileAdjusted = --In java, choose the file name--
ORDER BY Commits.Date ASC, Commits.Time ASC;
-- Then, in Java, call diff on an empty file with successive rows in this list

-- 8b. Get the body of a file from scratch
SELECT Changes.body
FROM Commits INNER JOIN Changes ON Commits.ID = Changes.commitID
WHERE Commits.Date > --File's date--
	AND fileAdjusted = '__the_required_file__'

ORDER BY Commits.Date ASC, Commits.Time ASC;
-- Then, call diff on the Java client program's version of this file


-- 9a. Who’s working on the GOAL I’m working on
SELECT fName, lName
FROM Contributors
	INNER JOIN WorkAssignment ON Contributors.ID = WorkAssignment.ContributorID
	INNER JOIN Goals ON WorkAssignment.GoalID = Goals.ID
WHERE Goals.ID = --In java, choose the goal id you want--

-- 9b. Who’s working on the PROJECT I’m working on
SELECT fName, lName
FROM Contributors
	INNER JOIN WorkAssignment ON Contributors.ID = WorkAssignment.ContributorID
	INNER JOIN Goals ON WorkAssignment.GoalID = Goals.ID
	INNER JOIN Projects ON Goals.ProjectID = Projects.ID
WHERE Project.ID = --In java, choose the project id you want--

