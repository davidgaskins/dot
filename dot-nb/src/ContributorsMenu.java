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
public class ContributorsMenu 
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput;
    private String queryOrStatement;
    
    public ContributorsMenu(Logger LOGGER, Connection connection)
    {
        this.userInput = new Scanner(System.in);
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    public void contributorsMenu()
    {
        int option;

        System.out.println();

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the contributor menu.");
            System.out.println("1. VIEW all contributors.");
            System.out.println("2. DELETE a contributor.");
            System.out.println("3. VIEW a contributor's contact information.");
            System.out.println("4. BACK to main menu.");
            
            option = userInput.nextInt();
            String userIn = userInput.nextLine();
            switch (option)
            {
                case 1: // add a contributor
                    contributorsMenuView();
                    break;
                case 2: // edit a contributor
                    contributorsMenuDelete();
                    break;
                case 3: // view a contributor
                    contributorsCIMenuView();
                    break;
                case 4:
                    wantToQuit = true;
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        }

    }
        
    private void contributorsCIMenuView()
    {
        //show the user all of the contributors and ask which for one
        //they would like to see their contact info.
        System.out.println("Enter the id of the contributor that you would like to see the contact information.");
        contributorsMenuView();
        String toView = userInput.nextLine();
        int id = Integer.parseInt(toView);
        String statementString = "SELECT * FROM phoneNumbers " +
                                    "WHERE contributorID = " + id + ";";
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);
            System.out.println("You are viewing the contact information for contributor: " + id);
            //simply print the resulting phoneNumbers
            while (rs.next())
            {
                String contributorID = rs.getString("contributorID");
                int contID = Integer.parseInt(contributorID);
                String phoneNum = rs.getString("phoneNumber");
                String phoneType = rs.getString("phoneType");
                System.out.println("phone type: " +phoneType +"\tphone number: " +phoneNum);
            }
        } catch (SQLException sqe) // @TODO
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
        String toDelete = userInput.nextLine();
        int idToDelete = Integer.parseInt(toDelete);
        String statementString = "DELETE FROM contributors "+
                            "WHERE id = " + idToDelete + ";";
        try // @TODO: make hierarchy of try catches for easier debugging
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(statementString);
            contributorsMenuView();
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error getting retrieving contributors. Error: {0}", sqe.getMessage());
            sqe.printStackTrace();

            System.out.println("There was an error in retrieving the contributors.");
        }
    }
    private void contributorsMenuView(){
        String statementString = "SELECT * FROM contributors;";
        try // @TODO: make hierarchy of try catches for easier debugging
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
            sqe.printStackTrace();
        }

    }
}
