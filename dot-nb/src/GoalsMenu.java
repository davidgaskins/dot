
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 009677081
 */
public class GoalsMenu 
{
    Connection connection;
    private static Logger LOGGER;
    
    public GoalsMenu(Logger LOGGER, Connection connection)
    {
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    private final Scanner userInput = new Scanner(System.in);
    private String queryOrStatement;

    public void goalsMenu()
    {
        int option;

        System.out.println();

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the GOALS menu.");
            System.out.println("1. ADD a goal.");
            System.out.println("2. EDIT a goal.");
            System.out.println("3. VIEW a goal.");
            System.out.println("4. BACK to main menu.");
            option = userInput.nextInt();

            switch (option)
            {
                case 1: // add a goal
                    goalsMenuAdd();
                    break;
                case 2: // edit a goal
                    goalsMenuEdit();
                    break;
                case 3: // view a goal
                    goalsMenuView();
                    break;
                case 4:
                    wantToQuit = true;
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }

    public void goalsMenuAdd()
    {
        userInput.nextLine();

        System.out.println("Enter the TITLE of the goal.");
        String title = userInput.nextLine();

        System.out.println("Enter the DESCRIPTION of the goal.");
        String description = userInput.nextLine();

        System.out.println("Enter the PRIORITY of the goal.");
        String priority = userInput.nextLine();

        System.out.println("Enter the TYPE of the goal.");
        String type = userInput.nextLine();

        System.out.println("Enter the STATUS of the goal.");
        String status = userInput.nextLine();

        String dateCreated = "January 1 2000";

        String dateUpdated = "January 2 2001";

        System.out.println("Enter the END DATE of the goal.");
        String dateToEnd = userInput.nextLine();

        String projectID = "SampleProject";

        String parentGoalID = "1";

        queryOrStatement = String.format(
                "INSERT INTO Goals(title, description, priority, type, status, dateCreated, "
                    + "dateUpdated, dateToEnd, projectID, parentGoalID"
                + "VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",

                title, description, priority, type, status, dateCreated, dateUpdated, dateToEnd, projectID, parentGoalID);
        System.err.println("About to execute query:\n" + queryOrStatement); // DEBUG
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryOrStatement);
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error adding goal. Error: {0}", sqe.getMessage());
            sqe.printStackTrace();
        }
    }

    public void goalsMenuEdit()
    {
        ResultSet rs;
        userInput.nextLine();

        // 1. supposed to prompt for project here. but assuming default project right now.            

        // 2. get the list of goals         
        goalsMenuView();

        // 3. Prompt for a goal by its ID
        System.out.println("Enter the ID of the goal you want to edit.");
        int id = userInput.nextInt();

        // 4. Even though it's inefficient to query one more time,
        // just get the Goal again by its ID
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM GOALS WHERE id = " + id);
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error retrieving a goal w/ specific ID. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the Goal.");
            return;
        }            

        try
        { // begin getting attributes of that row
            rs.next();
            // get all the attributes
            String title = rs.getString("title");
            String description = rs.getString("description");
            String priority = rs.getString("priority");
            String type = rs.getString("type");
            String status = rs.getString("status");
            String dateCreated = rs.getDate("dateCreated").toString();
            String dateUpdated = rs.getDate("dateUpdated").toString();
            String dateToEnd = rs.getDate("dateToEnd").toString();
            int projectID = rs.getInt("projectID");
            int parentGoalID = rs.getInt("parentGoalID");

            boolean wantToQuit = false;
            while (!wantToQuit)
            { // begin main menu loop
                System.out.println("Enter the number of the attribute to edit.");
                // 5. print all the attributes, just line by line
                System.out.println("These are the attributes you can edit:");
                System.out.println("1. Title: " + title);
                System.out.println("2. Description: " + description);
                System.out.println("3. Priority: " + priority);
                System.out.println("4. Type: " + type);
                System.out.println("5. Status: " + status);
                System.out.println("6. Date to end: " + dateToEnd);
                System.out.println("Date created: " + dateCreated);
                System.out.println("Date updated: " + dateUpdated);
                System.out.println("Parent ID: " + projectID); // just print so they know, can't edit
                System.out.println("Parent Goal ID: " + parentGoalID);
                System.out.println("7. Return to main menu.");

                int option = userInput.nextInt();
                switch (option)
                { // begin switch on attribute to edit
                    case 1:
                        System.out.println("Enter the new TITLE.");
                        String newTitle = userInput.nextLine();
                        rs.updateString(title, newTitle);
                        rs.updateRow();
                        System.out.println("Update successful.");
                        break;
                    case 2:
                        System.out.println("Enter the new DESCRIPTION.");
                        String newDescription = userInput.nextLine();
                        rs.updateString(title, newDescription);
                        rs.updateRow();
                        System.out.println("Update successful.");
                        break;
                    case 3: // @TODO: select priority from list
                        System.out.println("Enter the new PRIORITY.");
                        String newPriority = userInput.nextLine();
                        rs.updateString(title, newPriority);
                        rs.updateRow();
                        System.out.println("Update successful.");
                        break;
                    case 4:
                        System.out.println("Enter the new TYPE.");
                        String newType = userInput.nextLine();
                        rs.updateString(title, newType);
                        rs.updateRow();
                        System.out.println("Update successful.");
                        break;
                    case 5:
                        System.out.println("Enter the new STATUS.");
                        String newStatus = userInput.nextLine();
                        rs.updateString(title, newStatus);
                        rs.updateRow();
                        System.out.println("Update successful.");
                        break;
                    case 6:
                        System.out.println("Enter the new END DATE.");
                        String newEndDate = userInput.nextLine();
                        rs.updateString(title, newEndDate);
                        rs.updateRow();
                        System.out.println("Update successful.");
                        break;
                    case 7:
                        System.out.println("Returning to main menu.");
                        wantToQuit = true;
                        break;
                    default:
                        System.out.println("Invalid menu option.");
                        break;
                } // end switch on attribute to edit
            } // end main menu loop
        } // end getting attributes of row + main menu to edit
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error getting attributes from result set. Error: {0}", sqe.getMessage());
            sqe.printStackTrace();
        }
    }

    public void goalsMenuView()
    {
        ResultSet rs;
        // 1. get the goals
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM GOALS ORDER BY ID desc");
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error processing result set. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the goals.");
            return;
        }            

        // 2. print out the goals
        String columnNames = "ID\t Name\t description\t DateCreated\t DateUpdated\t";
        System.out.println(columnNames);
        try
        {
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String dateCreated = rs.getDate("dateCreated").toString();
                String dateUpdated = rs.getDate("dateUpdated").toString();
                String goalRow = String.format("%d\t %s\t %s\t %s\t% s\t",
                        id, title, description, dateCreated, dateUpdated);
                System.out.println(goalRow);
            }
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error processing result set. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in processing the result set.");
            return;
        }
    }
}
