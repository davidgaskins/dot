
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
            System.out.println("2. VIEW a post.");
            System.out.println("3. BACK to main menu.");
            option = userInput.nextInt();

            switch (option)
            {
                case 1: // add a post
                    postMenuEdit();
                    break;
                case 2: //view a post
                    postMenuView();
                    break;
                case 3:
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
        postMenuView();
        
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

    private void postMenuView() 
    {
        
    }
    
}
