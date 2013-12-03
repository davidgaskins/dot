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
            dot.connectToMartelDB();
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
                String url = "jdbc:mysql://127.0.0.1:3306"; //"jdbc:mysql://infoserver.cecs.csulb.edu:3306/cecs323m16";
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
            int option;

            System.out.println();
            
            boolean wantToQuit = false;
            while (!wantToQuit)
            {
                System.out.println("This is the GOALS menu.");
                System.out.println("1. ADD a goal.");
                System.out.println("2. EDIT a goal.");
                System.out.println("3. VIEW a goal.");
                System.out.println("4. BACK to main menu.");
                option = userInput.nextInt();
                
                switch (option)
                {
                    case 1: // add a goal
                        goalsMenuAdd();
                        break;
                    case 2: // edit a goal
                        goalsMenuEdit();
                        break;
                    case 3: // view a goal
                        // goalsMenuView();
                        break;
                    case 4:
                        wantToQuit = true;
                        break;
                    default:
                        System.out.println("Invalid menu option.");
                }
            }
        }
        
        private void goalsMenuAdd()
        {
            userInput.nextLine();
            
            System.out.println("Enter the TITLE of the goal.");
            String title = userInput.nextLine();
            
            System.out.println("Enter the DESCRIPTION of the goal.");
            String description = userInput.nextLine();
            
            System.out.println("Enter the PRIORITY of the goal.");
            String priority = userInput.nextLine();
            
            System.out.println("Enter the TYPE of the goal.");
            String type = userInput.nextLine();
            
            System.out.println("Enter the STATUS of the goal.");
            String status = userInput.nextLine();
            
            String dateCreated = "January 1 2000";
            
            String dateUpdated = "January 2 2001";
            
            System.out.println("Enter the END DATE of the goal.");
            String dateToEnd = userInput.nextLine();
            
            String projectID = "SampleProject";
            
            String parentGoalID = "1";
            
            queryOrStatement = String.format(
                    "INSERT INTO Goals(title, description, priority, type, status, dateCreated, "
                        + "dateUpdated, dateToEnd, projectID, parentGoalID"
                    + "VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                    
                    title, description, priority, type, status, dateCreated, dateUpdated, dateToEnd, projectID, parentGoalID);
            System.err.println("About to execute query:\n" + queryOrStatement); // DEBUG
            try
            {
                Statement statement = connection.createStatement();
                statement.executeUpdate(queryOrStatement);
            }
            catch (SQLException e)
            {
                System.out.println("There was an error in adding the goal. Check that your input is correct.");
            }
        }
        
        private void goalsMenuEdit()
        {
            userInput.nextLine();
            
            // 1. supposed to prompt for project here. but assuming default project right now.            
            
            // 2. get the list of goals            
            ResultSet rs;
            try
            {
                Statement statement = connection.createStatement();
                rs = statement.executeQuery("SELECT * FROM GOALS ORDER BY ID desc");
            }
            catch (SQLException sqe)
            {
                LOGGER.log(Level.SEVERE, "Error processing result set. Error: {0}", sqe.getMessage());
                System.out.println("There was an error in retrieving the goals.");
                return;
            }

            String columnNames = "ID\t Name\t description\t DateCreated\t DateUpdated\t";
            System.out.println(columnNames);
            try
            {
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String dateCreated = rs.getDate("dateCreated").toString();
                    String dateUpdated = rs.getDate("dateUpdated").toString();
                    String goalRow = String.format("%d\t %s\t %s\t %s\t% s\t",
                            id, title, description, dateCreated, dateUpdated);
                    System.out.println(goalRow);
                }
            }
            catch (SQLException sqe)
            {
                LOGGER.log(Level.SEVERE, "Error processing result set. Error: {0}", sqe.getMessage());
                System.out.println("There was an error in processing the result set.");
                return;
            }
            
            // 3. Prompt for a goal by its ID
            System.out.println("Enter the ID of the goal you want to edit.");
            int id = userInput.nextInt();
            
            // 4. Even though it's inefficient to query one more time,
            // just get the Goal again by its ID
            try
            {
                Statement statement = connection.createStatement();
                rs = statement.executeQuery("SELECT * FROM GOALS WHERE id = " + id);
            }
            catch (SQLException sqe)
            {
                LOGGER.log(Level.SEVERE, "Error retrieving a goal w/ specific ID. Error: {0}", sqe.getMessage());
                System.out.println("There was an error in retrieving the Goal.");
                return;
            }            
            
            try
            { // begin getting attributes of that row
                rs.next();
                // get all the attributes
                String title = rs.getString("title");
                String description = rs.getString("description");
                String priority = rs.getString("priority");
                String type = rs.getString("type");
                String status = rs.getString("status");
                String dateCreated = rs.getDate("dateCreated").toString();
                String dateUpdated = rs.getDate("dateUpdated").toString();
                String dateToEnd = rs.getDate("dateToEnd").toString();
                int projectID = rs.getInt("projectID");
                int parentGoalID = rs.getInt("parentGoalID");
                
                boolean wantToQuit = false;
                while (!wantToQuit)
                { // begin main menu loop
                    System.out.println("Enter the number of the attribute to edit.");
                    // 5. print all the attributes, just line by line
                    System.out.println("These are the attributes you can edit:");
                    System.out.println("1. Title: " + title);
                    System.out.println("2. Description: " + description);
                    System.out.println("3. Priority: " + priority);
                    System.out.println("4. Type: " + type);
                    System.out.println("5. Status: " + status);
                    System.out.println("6. Date to end: " + dateToEnd);
                    System.out.println("Date created: " + dateCreated);
                    System.out.println("Date updated: " + dateUpdated);
                    System.out.println("Parent ID: " + projectID); // just print so they know, can't edit
                    System.out.println("Parent Goal ID: " + parentGoalID);
                    System.out.println("7. Return to main menu.");
                    
                    int option = userInput.nextInt();
                    switch (option)
                    { // begin switch on attribute to edit
                        case 1:
                            System.out.println("Enter the new TITLE.");
                            String newTitle = userInput.nextLine();
                            rs.updateString(title, newTitle);
                            rs.updateRow();
                            System.out.println("Update successful.");
                            break;
                        case 2:
                            System.out.println("Enter the new DESCRIPTION.");
                            String newDescription = userInput.nextLine();
                            rs.updateString(title, newDescription);
                            rs.updateRow();
                            System.out.println("Update successful.");
                            break;
                        case 3: // @TODO: select priority from list
                            System.out.println("Enter the new PRIORITY.");
                            String newPriority = userInput.nextLine();
                            rs.updateString(title, newPriority);
                            rs.updateRow();
                            System.out.println("Update successful.");
                            break;
                        case 4:
                            System.out.println("Enter the new TYPE.");
                            String newType = userInput.nextLine();
                            rs.updateString(title, newType);
                            rs.updateRow();
                            System.out.println("Update successful.");
                            break;
                        case 5:
                            System.out.println("Enter the new STATUS.");
                            String newStatus = userInput.nextLine();
                            rs.updateString(title, newStatus);
                            rs.updateRow();
                            System.out.println("Update successful.");
                            break;
                        case 6:
                            System.out.println("Enter the new END DATE.");
                            String newEndDate = userInput.nextLine();
                            rs.updateString(title, newEndDate);
                            rs.updateRow();
                            System.out.println("Update successful.");
                            break;
                        case 7:
                            System.out.println("Returning to main menu.");
                            wantToQuit = true;
                            break;
                        default:
                            System.out.println("Invalid menu option.");
                            break;
                    } // end switch on attribute to edit
                } // end main menu loop
            } // end getting attributes of row + main menu to edit
            catch (SQLException sqe)
            {
                LOGGER.log(Level.SEVERE, "Error getting attributes from result set. Error: {0}", sqe.getMessage());
                sqe.printStackTrace();
            }
        } // end edit goal menu
        
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
        
        // http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
        private static String readFile(String path) 
        throws IOException 
        {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            Charset encoding = Charset.defaultCharset();
            return encoding.decode(ByteBuffer.wrap(encoded)).toString();
        }
}