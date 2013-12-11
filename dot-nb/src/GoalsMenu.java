/*
Goals Menu.java handles UI for the GoalsMenu
Tan Tran
David Gaskins
David Martel
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("2. VIEW a goal.");
            System.out.println("3. BACK to main menu.");
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
                case "2": // view a goal
                    goalsMenuView();
                    break;
                case "3":
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
        System.out.println("Enter the PRIORITY of the goal: CRITICAL, MEDIUM, LOW.");
        String priority = userInput.nextLine();

        System.out.println("Enter the TYPE of the goal: BUG, IMPROVEMENT, LONG-TERM.");
        String type = userInput.nextLine();

        System.out.println("Enter the STATUS of the goal: OPEN, CLOSED, NOFIX, ASSIGNED, DUPLICATE, POSTPONED.");
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
        System.out.println("Enter the END DATE (no time) of the goal. Format is mm dd yyyy");
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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO goals"
                    + "(title, description, priority, type, status, "
                    + "dateCreated, dateUpdated, dateToEnd, projectID, parentGoalID) "
                    + "VALUES("
                    + "'" + title + "', " 
                    + "'" + description + "', " 
                    + "'" + priority + "', " 
                    + "'" + type + "', " 
                    + "'" + status + "', " 
                    + "?" + ", " // dateCreated
                    + "?" + ", " // dateUpdated
                    + "?" + ", " // dateToEnd
                    + projectID + ", "
                    + parentGoalID
                    + ");");
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

    public void goalsMenuView()
    {
        ResultSet rs;
        // 1. get the goals
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM goals ORDER BY id ASC");
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
