
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 009677081
 */
public class SampleQueryMenu
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput;

    private static final String query1 = "SELECT projects.title, COUNT(commits.ID) as numCommits\n"
            + "FROM projects LEFT OUTER JOIN goals ON projects.id = goals.projectID\n"
                + "INNER JOIN commits ON goals.id = commits.goalID\n"
            + "GROUP BY projects.title";
    private static final String query2 = "SELECT contributors.email, COUNT(projects.ID) AS numProjects\n" 
            + "FROM contributors INNER JOIN workAssignments ON contributors.ID = workAssignments.contributorID\n" 
                + "INNER JOIN goals ON workAssignments.goalID = goals.ID\n" 
                + "INNER JOIN projects on goals.projectID = projects.ID\n" 
            + "GROUP BY contributors.email\n" 
            + "HAVING COUNT(projects.ID > 1)";
    private static final String query3 = 
            "SELECT contributors.fName, contributors.lName, contributors.email, "
            + "COUNT(*) AS numAssignments\n" 
            + "FROM contributors \n" 
                + "INNER JOIN workAssignments ON contributors.ID = workAssignments.contributorID\n" 
                + "INNER JOIN goals ON workAssignments.goalID = goals.ID\n" 
            + "WHERE goals.type = ‘BUG’\n" 
            + "GROUP BY (contributors.ID)";
    private static final String query4 = 
            "SELECT posts.body, goals.title, contributors.email\n" +
                "FROM posts INNER JOIN goals\n" +
                    "ON posts.goalID = goals.id\n" +
                "INNER JOIN contributors\n" +
                    "ON posts.contributorID = contributors.id\n" +
            "WHERE posts.dateAndTime >= ALL (\n" +
                "SELECT posts.dateAndTime\n" +
                    "FROM posts)";
    private static final String query5 = 
            "SELECT contributors.fName, contributors.lName, contributors.email, "
                + "phoneNumbers.phoneType, phoneNumbers.phoneNumber\n"
            + "FROM projects\n"
            + "INNER JOIN managementAssignments\n"
                + "ON projects.id = managementAssignments.projectID\n"
            + "INNER JOIN contributors\n"
                + "ON managementAssignments.contributorID = contributors.id\n"
            + "INNER JOIN phoneNumbers\n"
                + "ON contributors.id = phoneNumbers.contributorID\n"
            + "WHERE projects.id NOT IN(\n"
                + "SELECT projects.id\n"
                + "FROM projects\n"
                + "WHERE projects.dateToEnd < NOW()\n";
    private static final String query6 = "SELECT contributors.email, projects.title\n" +
        "FROM contributors \n" +
            "INNER JOIN workAssignments \n" +
                "ON contributors.ID = workAssignments.contributorID\n" +
            "INNER JOIN goals\n" +
                "ON workAssignments.goalID = goals.ID\n" +
            "INNER JOIN projects\n" +
                "ON goals.projectID = projects.ID\n" +
        "WHERE projects.id NOT IN(\n" +
            "SELECT projects.id\n" +
            "FROM projects INNER JOIN managementAssignments\n" +
                "ON projects.id = managementAssignments.projectID)";
    
    public SampleQueryMenu(Logger LOGGER, Connection connection)
    {
        this.userInput = new Scanner(System.in);
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    public void query1()
    {
        ResultSet rs = null;
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query1);
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error: {0}", sqe.getMessage());
            System.out.println("There was an error executing the query.");
            return;
        }            
        String columnNames = "Title" + "\t" + "numCommits";
        System.out.println(columnNames);
        try {
            while ( rs.next())
            {
                String title = rs.getString("title");
                String count = rs.getString("numCommits");
                System.out.println(title + "\t" + count);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SampleQueryMenu.class.getName()).log(Level.SEVERE, "Error executing query", ex);
            System.out.println("There was an error executing the query.");
        }
    }
    public void query4(){
        
    }
    public void query5(){
        
    }
    public void query6(){
        
    }
    public void query3()
    {
        ResultSet rs = null;
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query3);
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error retrieving a goal w/ specific ID. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the Goal.");
            return;
        }            
        String columnNames = "fName" + "\t" + "lName" + "\t" + "email";
        System.out.println(columnNames);
        try {
            while ( rs.next())
            {
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                String email = rs.getString("email");
                System.out.println(fName + "\t" + lName + "\t" + email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SampleQueryMenu.class.getName()).log(Level.SEVERE, "Error executing query", ex);
            System.out.println("There was an error executing the query.");
        }
    }
    
    public void query2()
    {
        ResultSet rs = null;
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(query2);
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error retrieving a goal w/ specific ID. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the Goal.");
            return;
        }            
        String columnNames = "fName" + "\t" + "lName" + "\t" + "email";
        System.out.println(columnNames);
        try {
            while ( rs.next())
            {
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                String email = rs.getString("email");
                System.out.println(fName + "\t" + lName + "\t" + email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SampleQueryMenu.class.getName()).log(Level.SEVERE, "Error executing query", ex);
            System.out.println("There was an error executing the query.");
        }
    }
    
    public void sampleQueryMenu()
    {
        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the sample query menu.");
            
            System.out.println("1. List titles of all projects, ordering by commits done on it, least to greatest.");
            System.out.println("2. List emails of all Contributors who are working on more than one project.");
            System.out.println("3. List all contributors assigned to work on bugs, and number of bugs assigned to them.");
            System.out.println("4. Find the most recent Post(s) of all Projects.");
            System.out.println("5. Find the name and phone numbers of all Managers of ongoing Projects.");
            System.out.println("6. List the emails of all Contributors to projects that do not have managers.");
            int option = userInput.nextInt();
            switch (option)
            {
                case 1:
                    query1();
                    break;
                case 2:
                    query2();
                    break;
                case 3:
                    query3();
                    break;
                case 4:
                    query4();
                    break;
                case 5:
                    query5();
                    break;
                case 6:
                    query6();
                    break;
                case 7:
                    wantToQuit = true;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }
}
