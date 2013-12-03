
import java.sql.Connection;
import java.util.Scanner;
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
            System.out.println("This is the post menu.");
            System.out.println("1. ADD a post.");
            System.out.println("2. VIEW a post.");
            System.out.println("3. BACK to main menu.");
            option = userInput.nextInt();

            switch (option)
            {
                case 1: // add a post
                    postMenuAdd();
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

    private void postMenuAdd() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void postMenuView() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
