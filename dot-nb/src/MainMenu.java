/*
    MainMenu.java: The overall UI handler for Dot, gets called from dot.java
    Tan Tran
    David Gaskins
    David Martel
*/

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

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
        SampleQueryMenu sampleQueryMenu = new SampleQueryMenu(LOGGER, connection);
        System.out.println("This is the management console of the DOT issue tracker and source control program.");

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the main menu of what you can do on your project.");
            System.out.println("1. Manage COMMITS of source code.");
            System.out.println("2. Manage POSTS on issues.[child update].");
            System.out.println("3. Manage GOALS[child insertion].");
            System.out.println("4. Manage CONTRIBUTORS.[parent deletion]");
            System.out.println("5. COMMIT changes to the database.");
            System.out.println("6. ROLLBACK changes since the last commit.");
            System.out.println("7. Run SAMPLE QUERIES.");
            System.out.println("8. Exit.");
            String input = userInput.nextLine();
            //check input
            InputChecker in = new InputChecker(input);
            if(in.hasAlpha()){
                System.out.println("That was not an int, returning to goals menu.");
                return;
            }
             input = input.trim();

            // since there isn't an array of statements to execute,
            // all this menu code doesn't need to be in the same place.
            // let's just call static methods for each of the submenus
            // so the main method isn't cluttered with all of them.
            switch (input)
            {
                case "1": // COMMITS
                    commitsMenu.commitsMenu();
                    break;
                case "2":
                    postsMenu.postsMenu();
                    break;
                case "3":
                    goalsMenu.goalsMenu();
                    break;
                case "4":
                    contributorsMenu.contributorsMenu();
                    break;
                case "5":
                    commitMenu();
                    break;
                case "6":
                    rollbackMenu();
                    break;
                case "7":
                    sampleQueryMenu.sampleQueryMenu();
                    break;
                case "8":
                    quitMenu();
                    wantToQuit = true;
                    break;
            }
        }
    }

    private void rollbackMenu()
    {
        try
        {
            connection.rollback();
        }
        catch (SQLException sqe)
        {
            System.out.println("Error. The rollback was unsuccessful.");
        }
        System.out.println("The changes since the last commit have been rolled back.");
    }
    //needs to be public for access from main
    public void commitMenu()
    {
        try
        {
            connection.commit();
        }
        catch (SQLException sqe)
        {
            System.out.println("Error. The commit was not successful.");
        }     
        System.out.println("The transaction has been commited.");
    }
    
    private void quitMenu()
    {

        System.out.println();

        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the quit menu.");
            System.out.println("1. Commit Changes.");
            System.out.println("2. Abort Changes.");
            String input = userInput.nextLine();
            //check input
            InputChecker in = new InputChecker(input);
            if(in.hasAlpha()){
                System.out.println("That was not an int, returning to goals menu.");
            return;
            }
            input = input.trim();

            switch (input)
            {
                case "1": // commit the Transaction
                    commitMenu();
                    wantToQuit = true;
                    break;
                case "2": // rollback the Transaction
                    rollbackMenu();
                    wantToQuit = true;
                    break;
                default:
                    System.out.println("Invalid menu option.");
            }
        }  
    }

}
