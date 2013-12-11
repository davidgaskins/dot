
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    
    private final Scanner userInput = new Scanner(System.in);
    private String queryOrStatement;
    
    public GoalsMenu(Logger LOGGER, Connection connection)
    {
        this.LOGGER = LOGGER;
        this.connection = connection;
    }

    public void goalsMenu()
    {
        System.out.println();

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the GOALS menu.");
            System.out.println("1. ADD a goal.");
            System.out.println("2. EDIT a goal.");
            System.out.println("3. VIEW a goal.");
            System.out.println("4. BACK to main menu.");
            String input = userInput.nextLine();
            InputChecker in = new InputChecker(input);
            if(in.hasAlpha()){
                System.out.println("That was not an int, try again.");
                continue;
            }
            input = input.trim();
            switch (input)
            {
                case "1": // add a goal
                    goalsMenuAdd();
                    break;
                case "2": // edit a goal
                    goalsMenuEdit();
                    break;
                case "3": // view a goal
                    goalsMenuView();
                    break;
                case "4":
                    wantToQuit = true;
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }

    public void goalsMenuAdd()
    {
        
        System.out.println("Enter the ID of the project this goal belongs to.");
        ProjectsMenu projectsMenu = new ProjectsMenu(LOGGER, connection);
        projectsMenu.projectsMenuView();
        
        //take in and check input
        String input = userInput.nextLine();
        //check input
        InputChecker in = new InputChecker(input);
        if(in.hasAlpha()){
            System.out.println("That was not an int, returning to goals menu.");
            return;
        }
        int projectID = Integer.parseInt(input);
        
        System.out.println("Enter the ID of the goal this goal is a subgoal of.");
        goalsMenuView();
        
        input = userInput.nextLine();
        //check input
        in = new InputChecker(input);
        if(in.hasAlpha()){
            System.out.println("That was not an int, returning to goals menu.");
            return;
        }
        int parentGoalID = Integer.parseInt(input);
           
        System.out.println("Enter the TITLE of the goal.");
        String title = userInput.nextLine();

        System.out.println("Enter the DESCRIPTION of the goal.");
        String description = userInput.nextLine();

        // @TODO: error checking on enums
        System.out.println("Enter the PRIORITY of the goal.");
        String priority = userInput.nextLine();

        System.out.println("Enter the TYPE of the goal.");
        String type = userInput.nextLine();

        System.out.println("Enter the STATUS of the goal.");
        String status = userInput.nextLine();
        
        // Make a calendar, which has time zone and encoding info.
        // Get the current time for the calendar, which returns util.Date
        // Use getTime() on util.Date to get a milliseconds value
        // Use that milliseconds value to make a sql.Date
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.util.Date utilDate = calendar.getTime();
        java.sql.Date dateCreated = new java.sql.Date(utilDate.getTime());
	// Since we're just creating, updated time is created time.
	// clone to make sure they're different objects
        java.sql.Date dateUpdated = (java.sql.Date) dateCreated.clone();
        
        SimpleDateFormat format = new SimpleDateFormat("MM dd yyyy"); // format parses the input
        java.sql.Date dateToEnd = null;
        System.out.println("Enter the END DATE (no time) of the goal. Format is mm dd yyy");
        try {
            java.util.Date parsed = format.parse( userInput.nextLine() );
            dateToEnd = new java.sql.Date(parsed.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(GoalsMenu.class.getName()).log(Level.SEVERE, "Error parsing date", ex);
            System.out.println("Error. The date cannot be parsed. Exiting to Goals menu.");
            return;
        }
        

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO goals(" 
                    + title + ", " 
                    + description + ", " 
                    + priority + ", " 
                    + type + ", " 
                    + status + ", " 
                    + "?" + ", " // dateCreated
                    + "?" + ", " // dateUpdated
                    + "?" + ", " // dateToEnd
                    + projectID + ", "
                    + parentGoalID);
            preparedStatement.setDate(1, dateCreated, calendar);
            preparedStatement.setDate(2, dateUpdated, calendar);
            preparedStatement.setDate(3, dateToEnd, calendar);
            preparedStatement.executeUpdate();

        } catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error adding goal. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in adding the goal.");
            return;
        }
        System.out.println("The goal was added successfully.");
    }

    public void goalsMenuEdit()
    {
        ResultSet rs;

        // 1. supposed to prompt for project here. but assuming default project right now.            

        // 2. get the list of goals         
        goalsMenuView();

        // 3. Prompt for a goal by its ID
        System.out.println("Enter the ID of the goal you want to edit.");
        String input = userInput.nextLine();
        //check input
        InputChecker in = new InputChecker(input);
        if(in.hasAlpha()){
            System.out.println("That was not an int, returning to goals menu.");
            return;
        }
        int id = Integer.parseInt(input);

        // 4. Even though it's inefficient to query one more time,
        // just get the Goal again by its ID
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM goals WHERE id = " + id);
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error retrieving a goal w/ specific ID. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the Goal.");
            return;
        }            
        System.out.println("Retrieved results.");
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
                input = userInput.nextLine();
                //check input
                in = new InputChecker(input);
                if(in.hasAlpha()){
                    System.out.println("That was not an int, returning to goals menu.");
                    return;
                }
                input = input.trim();
                switch (input)
                { // begin switch on attribute to edit
                    case "1":
                        System.out.println("Enter the new TITLE.");
                        String newTitle = userInput.nextLine();
                        rs.updateString(title, newTitle);
                        try
                        {
                            rs.updateRow();
                        }
                        catch (SQLException sqe)
                        {
                            System.out.println("The update was unsuccessful.");
                        }
                        System.out.println("Update successful.");
                        break;
                    case "2":
                        System.out.println("Enter the new DESCRIPTION.");
                        String newDescription = userInput.nextLine();
                        rs.updateString(title, newDescription);
                        try
                        {
                            rs.updateRow();
                        }
                        catch (SQLException sqe)
                        {
                            System.out.println("The update was unsuccessful.");
                        }
                        System.out.println("Update successful.");
                        break;
                    case "3": // @TODO: select priority from list
                        System.out.println("Enter the new PRIORITY.");
                        String newPriority = userInput.nextLine();
                        rs.updateString(title, newPriority);
                        try
                        {
                            rs.updateRow();
                        }
                        catch (SQLException sqe)
                        {
                            System.out.println("The update was unsuccessful.");
                        }
                        System.out.println("Update successful.");
                        break;
                    case "4":
                        System.out.println("Enter the new TYPE.");
                        String newType = userInput.nextLine();
                        rs.updateString(title, newType);
                        try
                        {
                            rs.updateRow();
                        }
                        catch (SQLException sqe)
                        {
                            System.out.println("The update was unsuccessful.");
                        }
                        System.out.println("Update successful.");
                        break;
                    case "5":
                        System.out.println("Enter the new STATUS.");
                        String newStatus = userInput.nextLine();
                        rs.updateString(title, newStatus);
                        try
                        {
                            rs.updateRow();
                        }
                        catch (SQLException sqe)
                        {
                            System.out.println("The update was unsuccessful.");
                        }
                        System.out.println("Update successful.");
                        break;
                    case "6":
                        System.out.println("Enter the new END DATE.");
                        String newEndDate = userInput.nextLine();
                        rs.updateString(title, newEndDate);
                        try
                        {
                            rs.updateRow();
                        }
                        catch (SQLException sqe)
                        {
                            System.out.println("The update was unsuccessful.");
                        }
                        System.out.println("Update successful.");
                        break;
                    case "7":
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
        }
    }

    public void goalsMenuView()
    {
        ResultSet rs;
        // 1. get the goals
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM goals ORDER BY id desc");
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error processing result set. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the goals.");
            return;
        }            
        System.out.println("Retrieved goals successfully.");
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
                String goalRow = String.format("%4d\t"
                        + "%20s\t"
                        + "%20s\t"
                        + "%s\t"
                        + "%s\t",
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
