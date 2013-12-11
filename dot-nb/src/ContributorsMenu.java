/*
Contributors Menu.java: The UI for what you can do with contributors in the database
David Martel
Tan Tran
David Gaskins
*/
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ContributorsMenu 
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput;
    
    public ContributorsMenu(Logger LOGGER, Connection connection)
    {
        this.userInput = new Scanner(System.in);
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    public void contributorsMenu()
    {
        String input;
        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the contributor menu.");
            System.out.println("1. VIEW all contributors.");
            System.out.println("2. DELETE a contributor.");
            System.out.println("3. VIEW a contributor's contact information.");
            System.out.println("4. BACK to main menu.");
            //check input
            input = userInput.nextLine();
            InputChecker in = new InputChecker(input);
            if(in.hasAlpha()){
                System.out.println("That was not an int, try again.");
                continue;
            }
            input = input.trim();
            switch (input)
            {
                case "1": // add a contributor
                    contributorsMenuView();
                    break;
                case "2": // edit a contributor
                    contributorsMenuDelete();
                    break;
                case "3": // view a contributor
                    contributorsMenuViewContactInfo();
                    break;
                case "4":
                    wantToQuit = true;
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        }

    }
        
    private void contributorsMenuViewContactInfo()
    {
        //show the user all of the contributors and ask which for one
        //they would like to see their contact info.
        contributorsMenuView();
        System.out.println("Enter the id of the contributor that you would like to see the contact information of.");
        String input = userInput.nextLine();
        InputChecker in = new InputChecker(input);
        if(in.hasAlpha()){
            System.out.println("That was not an int, returning to contributors menu");
            return;
        }
        input = input.trim();
        String statementString = "SELECT * FROM phoneNumbers " +
                                    "WHERE contributorID = " + input;
        try
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);
            System.out.println("You are viewing the contact information for contributor: " + input);
            //simply print the resulting phoneNumbers
            while (rs.next())
            {
                String contributorID = rs.getString("contributorID");
                int contID = Integer.parseInt(contributorID);
                String phoneNum = rs.getString("phoneNumber");
                String phoneType = rs.getString("phoneType");
                System.out.println("phone type: " +phoneType +"\tphone number: " +phoneNum);
            }
        } catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error getting getting contact info. Error: {0}", sqe.getMessage());
            sqe.printStackTrace();

            System.out.println("There was an error in retrieving contact info.");
        }
    }
    
    private void contributorsMenuDelete(){
        //show the user all of the contributors, and ask which one 
        //that they would like to delete
        System.out.println("Enter the id of the contributor that you would like to delete.");
        contributorsMenuView();
        String input = userInput.nextLine();
        InputChecker in = new InputChecker(input);
        if(in.hasAlpha()){
            System.out.println("That was not an int, returning to contributors menu");
            return;
        }
        input = input.trim();
        String statementString = "DELETE FROM contributors "+
                            "WHERE id = " + input;
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(statementString);
            contributorsMenuView();
        }
        catch (SQLException sqe)
        {
            System.out.println("This contributor can't be deleted, its a parent to a commit [on delete NO ACTION]");
            //LOGGER.log(Level.SEVERE, "Error getting retrieving contributors. Error: {0}", sqe.getMessage());
            //sqe.printStackTrace();
        }
    }
    private void contributorsMenuView(){
        String statementString = "SELECT * FROM contributors";
        try
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);

            String columnNames = "ID\t\t fName\t\t lName\t\temail";
            System.out.println(columnNames);
            while (rs.next())
            {
                int id = rs.getInt("id");
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                String email = rs.getString("email");
                String contributorRow = String.format("%d\t\t %s\t\t %s\t %s\t",
                        id, fName, lName, email);
                System.out.println(contributorRow);
            }
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error getting retrieving contributors. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the contributors.");
        }

    }
}
