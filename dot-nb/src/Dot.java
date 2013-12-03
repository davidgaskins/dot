import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/* All general TODOs:
1. try catch hierarchy for each jdbc access
2. keep static queries/statements separate, as final strings at beginning of prog like Gitorious example
3. factor jdbc access lines into convenience methods
4. generally no error checking at all
5. throw early catch late
*/

public class Dot {
    
        private final static Logger LOGGER = Logger.getLogger(Dot.class.getName());
        private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
        
        private final Scanner userInput = new Scanner(System.in);
        private Connection connection = null;
        
        private String queryOrStatement; // here so you don't need to keep track of declarations inside try/catch etc
            // it's just the string to store the new query into
        
        public Dot()
        {
            try {
                Class.forName(DB_DRIVER);
            } catch (ClassNotFoundException cnfe) {
                LOGGER.log(Level.SEVERE, "Unable to load JDBC driver, due to error: {0}", cnfe);
                System.exit(1);
            }
            
            System.out.println("DB_DRIVER init succesful.");
        }

	//static User guy;
	//static String workingDirectory

        // @TODO
        // set session transaction isolation level serializable:
        // which locks access to DB whiel other transactions waiting
        // but as soon as you commit or rollback the waiting transactions can start
        
	public static void main(String args[]) throws IOException 
        {
            LOGGER.setLevel(Level.INFO);
            
            Dot dot = new Dot();
            dot.connectToGaskinsDB();
            // dot.initializeMartelDB(); // SUPPOSED to CREATE the database, but we don't have
            // permission to do that on infoserver. waiting on David Gaskins to set up server
            // where we have permission to create db
            
            dot.mainMenu();
	   }
        
        private void initializeMartelDB() throws IOException
        {
            String query = readFile("dot-sql-create.sql");
                        
            System.err.println("About to execute query:\n" + query); // DEBUG
            try
            {
                Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE DATABASE dot2");
                statement.executeQuery(query);
            }
            catch (SQLException sqe)
            {
                LOGGER.log(Level.SEVERE, "Unable to init database due to error {0}", sqe.getMessage());
                sqe.printStackTrace();
            }
        }
        private void connectToMartelDB()
        {
            try {
                String url = "jdbc:mysql://infoserver.cecs.csulb.edu:3306/cecs323m16";
                String username = "cecs323m16";
                String password = "aigoiY";

                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
            } catch (SQLException sqe)
            {
                // this is for LOGGING purposes
                LOGGER.log(Level.SEVERE, "Unable to establish a connection to the database due to error {0}", sqe.getMessage());
                sqe.printStackTrace();
                connection = null;
                
                // this is for the PROGRAM's purpose. i.e., exit because we can't do anything
                System.out.println("Unable to connect to database. Exiting.");
                System.exit(1);
            }            
        }
        
        private void connectToGaskinsDB()
        {
            try {
                String url = "jdbc:mysql://davidgaskins.com:3306/dot";
                String username = "root";
                String password = "#FeqkTlnZ#";

                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
            } catch (SQLException sqe)
            {
                // this is for LOGGING purposes
                LOGGER.log(Level.SEVERE, "Unable to establish a connection to the database due to error {0}", sqe.getMessage());
                sqe.printStackTrace();
                connection = null;
                
                // this is for the PROGRAM's purpose. i.e., exit because we can't do anything
                System.out.println("Unable to connect to database. Exiting.");
                System.exit(1);
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
            GoalsMenu goalsMenu = new GoalsMenu(LOGGER, connection);
            ContributorsMenu contributorsMenu = new ContributorsMenu(LOGGER, connection);
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
                        commitsMenu();
                        break;
                    case 2:
                        postsMenu();
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
        
        
        private void commitsMenu()
        {
                
        }

        private void postsMenu()
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
        
        private void commitMenu()
        {
            System.out.println("The transaction has been commited.");
            //need to add the commit code       
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
        
        // http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
        private static String readFile(String path) 
        throws IOException 
        {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            Charset encoding = Charset.defaultCharset();
            return encoding.decode(ByteBuffer.wrap(encoded)).toString();
        } 
}