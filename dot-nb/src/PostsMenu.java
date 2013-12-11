
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
public class PostsMenu 
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput = new Scanner(System.in);
    
    public PostsMenu(Logger LOGGER, Connection connection)
    {
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    public void postsMenu()
    {

        System.out.println();

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the post menu. Obviously, "
                    + "you cannot ADD posts on behalf of users, "
                    + "but you can 1. moderate posts and 2. view them.");
            System.out.println("1. EDIT (moderate) a post.");
            System.out.println("2. VIEW ALL posts on all projects.");
            System.out.println("3. BACK to main menu.");
            String input = userInput.nextLine();
            //check input
            InputChecker in = new InputChecker(input);
            if(in.hasAlpha()){
                System.out.println("That was not an int, returning to posts menu.");
                return;
            }
            input = input.trim();
            
            switch (input)
            {
                case "1": // add a post
                    postMenuEdit();
                    break;
                case "2": //view all posts
                    postMenuView();
                    break;
                case "3":
                    wantToQuit = true;
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        }
    }

    private void postMenuEdit() 
    {
        ResultSet rs;
        postMenuView();
        System.out.println("Enter the ID of the post you want to edit.");
        String input = userInput.nextLine();
        //check input
        InputChecker in = new InputChecker(input);
        if(in.hasAlpha()){
            System.out.println("That was not an int, returning to posts menu.");
            return;
        }
        int id = Integer.parseInt(input);
        
        System.out.println("Enter the new body of the post (all on one line).");
        String newBody = userInput.nextLine();
        String statementString = "UPDATE posts " + 
                                    "SET body = \'" + newBody+"\' " 
                                        + "WHERE id = "+ id;
        
        try
        {
            Statement statement = connection.createStatement();
            statement.executeUpdate(statementString);
            //print out the database to show that it has been properly updated
            postMenuView();
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error retrieving a post w/ that id. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the post.");
            return;
        }
        
        // retrieved the post. now list attributes to edit
    }
    
    private void postMenuView() 
    {
        String statementString = "SELECT * FROM posts";
        try // @TODO: make hierarchy of try catches for easier debugging
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);

            String columnNames = "id\t\t contributorID\t goalID\t\tdataAndTime\t\tBody";
            System.out.println(columnNames);
            while (rs.next())
            {
                int id = rs.getInt("id");
                String body = rs.getString("body");
                String dateAndTime = rs.getString("dateAndTime");
                int contributorID = rs.getInt("contributorID");
                int goalID = rs.getInt("goalID");
                String postLine = String.format("%d\t\t%d\t\t %d\t\t %s\t %s", 
                            id, contributorID, goalID, dateAndTime, body); 
                
                System.out.println(postLine);
           }
        }
        catch (SQLException sqe)
        {
            System.out.println("There was an error in retrieving the posts.");
        }
    }
    
}
