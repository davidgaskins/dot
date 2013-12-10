
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author David Martel
 * @author Tan Tran
 */
public class CommitsMenu 
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput;
    private String repositoryDir;
    
    public CommitsMenu(Logger LOGGER, Connection connection)
    {
        this.userInput = new Scanner(System.in);
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    public void commitsMenu()
    {
        String input;
        boolean wantToQuit = false;
        while (!wantToQuit)
        {
            System.out.println("This is the commits menu. Obviously, "
                    + "you cannot commit changes on behalf of others, "
                    + "but you can 1. delete the latest commit, and "
                    + "2. checkout a project as it was at a specific commit. ");
            System.out.println("1. DELETE latest commit");
            System.out.println("2. CHECKOUT the repo.");
            System.out.println("3. BACK to menu menu");
            input = userInput.nextLine();
            input = input.trim();
            switch(input) {
                case "1":
                    // @TODO deleteLatestCommit();
                    break;
                case "2":
                    commitsMenuCheckout();
                    break;
                case "3":
                    wantToQuit = true;
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }
    
    public void commitsMenuView()
    {
        ResultSet rs;
        // 1. get the goals
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM commits ORDER BY commitDate DESC");
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error processing result set. Error: {0}", sqe.getMessage());
            return;
        }            
        
        // 2. print out the COMMITS
        String columnNames = "ID\t ContributorID\t GoalID\t CommitDate\t Description\t";
        System.out.println(columnNames);
        try
        {
            while (rs.next())
            {
                int id = rs.getInt("id");
                String contributorID = rs.getString("contributorID");
                String goalID = rs.getString("goalID");
                String commitDate = rs.getDate("commitDate").toString();
                String description = rs.getDate("description").toString();
                String goalRow = String.format("%4d\t"
                        + "%4d\t"
                        + "%4d\t"
                        + "%s\t"
                        + "%s\t",
                        id, contributorID, goalID, commitDate, description);
                System.out.println(goalRow);
            }
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error processing result set. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in processing the result set.");
            return;
        }
    }
    
    private void commitsMenuCheckout() 
    {
        ResultSet rs;
        //find commit to checkout
        commitsMenuView();
        
        System.out.println("Which commit would you like to checkout?");
        String commitIDToStopAt = userInput.nextLine();
               
        //find repository
        System.out.println("What directory would you like to initialize");
        repositoryDir = userInput.nextLine();
        if(repositoryDir.trim().equals("")) {
            //set working directory
            repositoryDir = System.getProperty("user.dir");
        }

        // 4. get diffs up until that point
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT bodyOfDiff"
                    + "FROM commits INNER JOIN changes ON commits.ID = changes.commitID"
                    + "ORDER BY commits.commitDate DESC"
                    + "WHERE commits.commitID <=" + commitIDToStopAt);
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error getting diffs. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in retrieving the Goal.");
            return;
        }            

        //apply diffs to make files
        List<String> patches = new ArrayList<>();
        try {
            while (rs.next())
            {
                String p = rs.getString("bodyOfDiff");
                patches.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommitsMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileUtil.build(patches, repositoryDir);
    }
}
