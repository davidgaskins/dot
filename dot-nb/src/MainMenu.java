
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
public class MainMenu 
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput;

    public MainMenu(Logger LOGGER, Connection connection)
    {
        this.userInput = new Scanner(System.in);
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    public void mainMenu()
    {
        GoalsMenu goalsMenu = new GoalsMenu(LOGGER, connection);
        ContributorsMenu contributorsMenu = new ContributorsMenu(LOGGER, connection);
        CommitsMenu commitsMenu = new CommitsMenu(LOGGER, connection);
        PostsMenu postsMenu = new PostsMenu(LOGGER, connection);

        int option;

        System.out.println("This is the management console of the DOT issue tracker and source control program.");

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the main menu of what you can do on your project.");
            System.out.println("1. Manage COMMITS of source code.");
            System.out.println("2. Manage POSTS on issues.");
            System.out.println("3. Manage GOALS.");
            System.out.println("4. Manage CONTRIBUTORS.");
            System.out.println("5. COMMIT changes to the database.");
            System.out.println("6. ROLLBACK changes since the last commit.");
            System.out.println("7. QUIT.");
            option = userInput.nextInt();

            // since there isn't an array of statements to execute,
            // all this menu code doesn't need to be in the same place.
            // let's just call static methods for each of the submenus
            // so the main method isn't cluttered with all of them.
            switch (option)
            {
                case 1: // COMMITS
                    commitsMenu.commitsMenu();
                    break;
                case 2:
                    postsMenu.postsMenu();
                    break;
                case 3:
                    goalsMenu.goalsMenu();
                    break;
                case 4:
                    contributorsMenu.contributorsMenu();
                    break;
                case 5:
                    commitMenu();
                    break;
                case 6:
                    rollbackMenu();
                    break;
                case 7:
                    quitMenu();
                    break;
            }
        }
    }

    private void rollbackMenu()
    {
        System.out.println("The transaction has been rolled back.");
        //need to add the rollback code.
    }

    private void quitMenu()
    {
        int option;

        System.out.println();

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the quit menu.");
            System.out.println("1. Commit Changes.");
            System.out.println("2. Abort Changes.");
            option = userInput.nextInt();

            switch (option)
            {
                case 1: // commit the Transaction
                    commitMenu();
                    break;
                case 2: // rollback the Transaction
                    rollbackMenu();
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        }  
    }

    private void commitMenu()
    {
        System.out.println("The transaction has been commited.");
        //need to add the commit code       
    }
}
