/*
Dot.java manages the jdbc connection, calls the other menus
Tan Tran
David Gaskins
David Martel
*/

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dot {
        private final static String databaseURL = "jdbc:mysql://infoserver.cecs.csulb.edu:3306/cecs323m16"; // @TODO: where do we finally connect to when submitting?
        private final static Logger LOGGER = Logger.getLogger(Dot.class.getName());
        private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
        
        private final Scanner userInput = new Scanner(System.in);
        private Connection connection = null;

	public static void main(String args[]) throws IOException 
    	{
            LOGGER.setLevel(Level.INFO);
            Dot dot = new Dot();
            dot.connectToMartelDB();
            MainMenu mainMenu = new MainMenu(LOGGER, dot.connection);
            
            boolean wantToQuit = false;
            while (!wantToQuit)
            {
                System.out.println("This is the administration tool for the DOT issue tracker");
                System.out.println("and source control program. Before beginning, choose:");
                System.out.println("1. Reinitialize the database with sample data");
                System.out.println("2. Continue with the old database");
                String input =dot.userInput.nextLine();
                    //check input
                InputChecker in = new InputChecker(input);
                if(in.hasAlpha())
                {
                    System.out.println("That was not an int, returning to goals menu.");
                    return;
                }
                input = input.trim();

                switch (input)
                {
                    case "1": // COMMITS
                        dot.initializeMartelDB();
                        mainMenu.commitToDatabaseMenu();
                        //dont need a break; here
                    case "2":
                        wantToQuit = true;
                        break;
                    default:
                            System.out.println("Invalid menu option");
                        break;
                }
            }
            
            FileUtil.init();
            
            mainMenu.mainMenu();
            
            FileUtil.close();
	}

        public Dot()
        {
            try {
                Class.forName(DB_DRIVER);
            } catch (ClassNotFoundException cnfe) {
                LOGGER.log(Level.SEVERE, "Unable to load JDBC driver, due to error: {0}", cnfe);
                System.exit(1);
            }
            
            System.out.println("DB_DRIVER init successful.");
        }

        private void initializeMartelDB() throws IOException
        {
            String[] fileNames = new String[] {"dot-sql-drop-all-tables.sql", "dot-sql-create-and-insert-enum.sql", 
		"dot-sql-create-all-tables.sql", "dot-sql-insert-all-tables.sql"};
            for (String fileName : fileNames)
            {
                String[] statements = FileUtil.readFile(fileName).split("; ");
                for (String statementString : statements) 
                {
                    try
                    {
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(statementString);
                    }
                    catch (SQLException sqe)
                    {
                        LOGGER.log(Level.SEVERE, "Unable to init database due to error {0}. In file " 
				+ fileName + ", offending statement was " + statementString, sqe.getMessage());
                        System.exit(1);
                    }
                }
            }
        }
        
	private void connectToDB()
	{
		try 
		{
			System.out.println("Enter the username.");
			String username = userInput.nextLine();
			System.out.println("Enter the password.");
			String password = userInput.nextLine();
			connection = DriverManager.getConnection(databaseURL, username, password);
			connection.setAutoCommit(false);
		} 
		catch (SQLException sqe) 
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
                System.out.println("Connecting to local server (or ssh forwarded server)");
                String url = "jdbc:mysql://localhost:3306/cecs323m16";
                String username = "cecs323m16";
                String password = "aigoiY";
                
                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
                } catch (SQLException sqe2)
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
        }
}
