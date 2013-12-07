import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
        // @TODO
        // set session transaction isolation level serializable:
        // which locks access to DB whiel other transactions waiting
        // but as soon as you commit or rollback the waiting transactions can start
        
	public static void main(String args[]) throws IOException 
        {
            LOGGER.setLevel(Level.INFO);
            
            Dot dot = new Dot();
            dot.connectToMartelDB();
            dot.initializeMartelDB(); // SUPPOSED to CREATE the database, but we don't have
            // permission to do that on infoserver. waiting on David Gaskins to set up server
            // where we have permission to create db
            
            MainMenu mainMenu = new MainMenu(LOGGER, dot.connection);
            mainMenu.mainMenu();
	}

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

        private void initializeMartelDB() throws IOException
        {
            String[] fileNames = new String[] {"dot-sql-drop-all-tables.sql", "dot-sql-create-and-insert-enum.sql", "dot-sql-create-all-tables.sql", "dot-sql-insert-all-tables.sql"};
            for (String fileName : fileNames)
            {
                String[] statements = FileUtil.readFile(fileName).split(";");
                for (String statementString : statements) 
                {
                    try
                    {
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(statementString);
                    }
                    catch (SQLException sqe)
                    {
                        LOGGER.log(Level.SEVERE, "Unable to init database due to error {0}. In file " + fileName + ", offending statement was " + statementString, sqe.getMessage());
                        System.exit(1);
                    }
                }
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
            } catch (SQLException sqe){ 
                try{
                String url = "jdbc:mysql://localhost:3306/cecs323m16";
                String username = "cecs323m16";
                String password = "aigoiY";

                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
            } catch (SQLException sqe2)
            {
                // this is for LOGGING purposes
                LOGGER.log(Level.SEVERE, "Unable to establish a connection to the database due to error {0}", sqe.getMessage());
                sqe2.printStackTrace();
                connection = null;
                
                // this is for the PROGRAM's purpose. i.e., exit because we can't do anything
                System.out.println("Unable to connect to database. Exiting.");
                System.exit(1);
            }          
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
        

        // http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
}