import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dot {
        private final static Logger LOGGER = Logger.getLogger(Dot.class.getName());
        private final static String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";
        private Scanner userInput = new Scanner(System.in);
        private Connection connection = null;

	//static User guy;
	//static String workingDirectory

        // @TODO
        // set session transaction isolation level serializable:
        // which locks access to DB whiel other transactions waiting
        // but as soon as you commit or rollback the waiting transactions can start
        
	public static void main(String args[]) 
        {
            LOGGER.setLevel(Level.INFO);
            
            Dot dot = new Dot();
            dot.connectToGaskinsDB();
            dot.mainMenu();
	}
        
        
        private void connectToGaskinsDB()
        {
            try {
                String url = "jdbc:mysql://davidgaskins.com:688/
                String username = "server";
                String password = "#FeqkTlnZ#";

                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
            } catch (SQLException sqe)
            {
                LOGGER.log(Level.SEVERE, "Unable to establish a connection to the database due to error {0}", sqe.getMessage());
                connection = null;
            }
        }
        
//        private void connectToDB()
//        {
//            System.out.println("Enter the username.");
//            String username = userInput.nextLine();
//            System.out.println("Enter the password.");
//            String password = userInput.nextLine();
//            
//            connection = DriverManager.getConnection(, username, password)
//        }
        
        private void mainMenu()
        {
            int option;
            
            boolean wantToQuit = false;
            System.out.println("Welcome to the management console of the DOT issue tracker and source control program.");
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
                        commitsMenu();
                        break;
                    case 2:
                        postsMenu();
                        break;
                    case 3:
                        goalsMenu();
                        break;
                    case 4:
                        contributorsMenu();
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
        
        
        private void commitsMenu()
        {
                
        }

        private void postsMenu()
        {
                
        }
        
        private void goalsMenu()
        {
            boolean wantToQuit = false;
            while (!wantToQuit)
            {
                System.out.println("This is the GOALS menu.");
                System.out.println("1. ADD a goal.");
                System.out.println("2. EDIT a goal.");
                System.out.println("3. VIEW a goal.");
                System.out.println("4. BACK to main menu.");
                int option = userInput.nextInt();
                
                switch (option)
                {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        }
        
        private void contributorsMenu()
        {
                
        }
        
        private void commitMenu()
        {
                
        }
        
        private void rollbackMenu()
        {
                
        }
        
        private void quitMenu()
        {
                
        }
}