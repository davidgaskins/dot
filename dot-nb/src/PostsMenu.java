
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
    private String queryOrStatement;
    
    public PostsMenu(Logger LOGGER, Connection connection)
    {
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    public void postsMenu()
    {
        int option;

        System.out.println();

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the post menu. Obviously, "
                    + "you cannot ADD posts on behalf of users, "
                    + "but you can 1. moderate posts and 2. view them.");
            System.out.println("1. EDIT (moderate) a post.");
            System.out.println("2. VIEW ALL posts");
            System.out.println("3. VIEW a particular post.");
            System.out.println("4. BACK to main menu.");
            option = userInput.nextInt();

            switch (option)
            {
                case 1: // add a post
                    postMenuEdit();
                    break;
                case 2: //view all posts
                    postMenuView();
                    break;
                case 3: //view all posts
                    postMenuViewAPost();
                    break;
                case 4:
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
        userInput.nextLine();
        // 1. supposed to prompt for project here
        
        // 2. get the list of posts
      
        
        // 3. prompt for a post by its id
        System.out.println("Enter the ID of the post you want to edit.");
        int id = userInput.nextInt();

        // get the post again by its id
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM POSTS WHERE id = " + id);
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error retrieving a post w/ that id. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the post.");
            return;
        }
        
        // retrieved the post. now list attributes to edit
    }
    private void postMenuViewAPost()
    {
        System.out.println("Enter the id of the post that you would like to see.");
        String toView = userInput.nextLine();
        int id = Integer.parseInt(toView);
        String statementString = "SELECT * FROM posts " +
                                    "WHERE id = " + id + ";";
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);
            System.out.println("You are viewing the contact information for contributor: " + id);
            //simply print the resulting post
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
            System.out.println("failed to view contact information for id:"+ id);
        }
    }
    private void postMenuView() 
    {
        String statementString = "SELECT * FROM posts;";
        try // @TODO: make hierarchy of try catches for easier debugging
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(statementString);

            String columnNames = "contributorID\t\t goalID\t\tdataAndTime\tBody";
            System.out.println(columnNames);
            while (rs.next())
            {
                String body = rs.getString("body");
                String dateAndTime = rs.getString("dateAndTime");
                int contributorID = rs.getInt("contributorID");
                int goalID = rs.getInt("goalID");
                String postLine = String.format("%d\t\t %d\t\t %s\t %s", 
                            contributorID, goalID, dateAndTime, body); 
                
                System.out.println(postLine);
           }
        }
        catch (SQLException sqe)
        {
            System.out.println("There was an error in retrieving the posts.");
        }
    }
    
}
