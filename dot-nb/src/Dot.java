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


public class Dot {
    
        private final static Logger LOGGER = Logger.getLogger(Dot.class.getName());
        private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
        
        private final Scanner userInput = new Scanner(System.in);
        private Connection connection = null;

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
                LOGGER.log(Level.SEVERE, "Unable to establish a connection to the database due to error {0}", sqe.getMessage());
                sqe.printStackTrace();
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
                        goalsMenuView();
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
            
            String statementString = String.format(
                    "INSERT INTO Goals(title, description, priority, type, status, dateCreated, "
                        + "dateUpdated, dateToEnd, projectID, parentGoalID"
                    + "VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                    
                    title, description, priority, type, status, dateCreated, dateUpdated, dateToEnd, projectID, parentGoalID);
            System.err.println("About to execute query:\n" + statementString); // DEBUG
            try
            {
                Statement statement = connection.createStatement();
                statement.executeUpdate(statementString);
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
            String statementString = "SELECT * FROM GOALS ORDER BY ID desc";
            try // @TODO: make hierarchy of try catches for easier debugging
            {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(statementString);
                
                String columnNames = "ID\t Name\t description\t DateCreated\t DateUpdated\t";
                System.out.println(columnNames);
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String dateCreated = rs.getDate("dateCreated").toString();
                    String dateUpdated = rs.getDate("dateUpdated").toString();
                    String goalRow = String.format("%d\t %s\t %s\t %s\t% s\t",
                            id, name, description, dateCreated, dateUpdated);
                    System.out.println(goalRow);
                }
            }
            catch (SQLException e)
            {
                System.out.println("There was an error in retrieving the goals.");
            }
            
            // 3. Prompt for a goal by its ID
            System.out.println("Enter the ID of the goal you want to edit.");
            int id = userInput.nextInt();
            
            // 4. Even though it's inefficient to query one more time,
            // just get the Goal again by its ID
            String queryString = "SELECT * FROM GOALS WHERE id = " + id;
            try // @TODO: make hierarchy of try catches for easier debugging
            {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(queryString);
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
                
                // print all the attributes, just line by line
                System.out.println("These are the attributes you can edit:");
                System.out.println("1. Title: " + title);
                System.out.println("2. Description: " + description);
                System.out.println("3. Priority: " + priority);
                System.out.println("4. Type: " + type);
                System.out.println("5. Status: " + status);
                System.out.println("6. Date created: " + dateCreated);
                System.out.println("7. Date updated: " + dateUpdated);
                System.out.println("8. Date to end: " + dateToEnd);
                System.out.println("Parent ID: " + projectID); // just print so they know, can't edit
                System.out.println("Parent Goal ID: " + parentGoalID);
            }
            catch (SQLException e)
            {
                System.out.println("There was an error in retrieving the goals.");
            }
            
            
            
        }
        private void contributorsMenu()
        {
            int option;

            System.out.println();
            
            boolean wantToQuit = false;
            while (!wantToQuit)
            {
                System.out.println("This is the contributor menu.");
                System.out.println("1. VIEW all contributors.");
                System.out.println("2. DELETE a contributor.");
                System.out.println("3. VIEW a contributor's contact information.");
                System.out.println("4. BACK to main menu.");
                option = userInput.nextInt();
                
                switch (option)
                {
                    case 1: // add a contributor
                        contributorsMenuView();
                        break;
                    case 2: // edit a contributor
                        contributorsMenuDelete();
                        break;
                    case 3: // view a contributor
                        contributorsCIMenuView();
                        break;
                    case 4:
                        wantToQuit = true;
                        break;
                    default:
                        System.out.println("Invalid menu option.");
                }
            }
                
        }
        private void contributorsMenuDelete{
            //show the user all of the contributors, and ask which one 
            //that they would like to delete
            System.out.println("Enter the email of the contributor that you would like to delete.");
            contributorsMenuView();
            String toDelete = in.nextLine();
            String statementString = "DELETE FROM contributors"+
                                "WHERE contributors.email = " + toDelete;
            try // @TODO: make hierarchy of try catches for easier debugging
            {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(statementString);
                
                //simply print out the result, which should show that the row was deleted
                while (rs.next())
                {
                    String line = rs.getMessage();
                    System.out.println(line);
                }
            }
            catch (SQLException e)
            {
                System.out.println("There was an error in retrieving the contributors.");
            }



        }
        private void contributorsMenuView(){
            String statementString = "SELECT * FROM contributors";
            try // @TODO: make hierarchy of try catches for easier debugging
            {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(statementString);
                
                String columnNames = "ID\t fName\t lName\t email";
                System.out.println(columnNames);
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String fName = rs.getString("fName");
                    String lName = rs.getString("lName");
                    String email = rs.getDate("email");
                    String contributorRow = String.format("%d\t %s\t %s\t %s\t",
                            id, fName, lName, email,);
                    System.out.println(contributorRow);
                }
            }
            catch (SQLException e)
            {
                System.out.println("There was an error in retrieving the contributors.");
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
                System.out.println("1. CommitChanges.");
                System.out.println("2. Abort Changes.");
               ds option = userInput.nextInt();
                
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