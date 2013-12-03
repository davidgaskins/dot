import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author 009677081
 */
public class ContributorsMenu 
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput = new Scanner(System.in);
    private String queryOrStatement;
    
    public ContributorsMenu(Logger LOGGER, Connection connection)
    {
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    private void contributorsMenu()
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
                                    "WHERE id = " + id;
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);
            //simply print the resulting phoneNumbers
            while (rs.next())
            {
                String line = rs.getMessage();
                System.out.println(line);
            }
        } catch (SQLException sqe) // @TODO
        {
            ;
        }
    }
    
    private void contributorsMenuDelete(){
        //show the user all of the contributors, and ask which one 
        //that they would like to delete
        System.out.println("Enter the id of the contributor that you would like to delete.");
        contributorsMenuView();
        String toDelete = userInput.nextLine();                      
        String statementString = "DELETE FROM contributors"+
                            "WHERE id = " + toDelete;
        try // @TODO: make hierarchy of try catches for easier debugging
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);

            //simply print out the result, which should show that the row was deleted
            while (rs.next())
            {
                String line = rs.getMessage();
                System.out.println(line);
            }
        }
        catch (SQLException e)
        {
            System.out.println("There was an error in retrieving the contributors.");
        }
    }
    private void contributorsMenuView(){
        String statementString = "SELECT * FROM contributors";
        try // @TODO: make hierarchy of try catches for easier debugging
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);

            String columnNames = "ID\t fName\t lName\t email";
            System.out.println(columnNames);
            while (rs.next())
            {
                int id = rs.getInt("id");
                String fName = rs.getString("fName");
                String lName = rs.getString("lName");
                String email = rs.getString("email");
                String contributorRow = String.format("%d\t %s\t %s\t %s\t",
                        id, fName, lName, email);
                System.out.println(contributorRow);
            }
        }
        catch (SQLException sqe)
        {
            System.out.println("There was an error in retrieving the contributors.");
        }

    }

}