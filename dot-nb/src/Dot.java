import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Dot {
        private final static Logger LOGGER = Logger.getLogger(Dot.class.getName());
        private final static String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";
        private final static String DB_URL = "jdbc:mysql://davidgaskins.com";
        

    
    
	//static User guy;
	//static String workingDirectory

    
        // @TODO
        // turn off autocommit: set autocommit = 0; as a sql statement
    
        // @TODO
        // set session transaction isolation level serializable:
        // which locks access to DB whiel other transactions waiting
        // but as soon as you commit or rollback the waiting transactions can start
	public static void main(String args[]) 
        {
            LOGGER.setLevel(Level.INFO);
            
            Dot dot = new Dot();
            dot.mainMenu();
	}
        
        private void getConnectionData()
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the username.");
            String username = scanner.nextLine();
            System.out.println("Enter the password.");
            String password = scanner.nextLine();
        }
        
        private void mainMenu()
        {
            Scanner scanner = new Scanner(System.in);
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
                option = scanner.nextInt();
                
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