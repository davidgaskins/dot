
import java.sql.Connection;
import java.util.List;
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
public class CommitsMenu 
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput;
    private String queryOrStatement;
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
        do{
            System.out.println("Commits Menu");
            System.out.println("1. Initialize Repository");
            System.out.println("2. Make Commit");
            System.out.println("3. Checkout commit");
            System.out.println("4. Exit");
            input = userInput.nextLine();
            input = input.trim();
            switch(input) {
                case "1":
                    initRepository();
                    break;
                case "2":
                    makeCommit();
                    break;
                case "3":
                    checkout();
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Invalid input");
            
            }
        
        } while(!"4".equals(input));
    }
    
    private void makeCommit() {
        //potentially ask user for directory
        
        //use directory from config file to make life easier
        repositoryDir = FileUtil.settings.get("repository");
        
        //generate list of patche files
        Commit commity = new Commit(repositoryDir);
        List<Commit.Change> diffList = commity.generateChanges();
        
        //start of sql
    }
    
    private void checkout() {
        String commitID;
        //find commit to checkout
        System.out.println("Which commit would you like to checkout (Default=LastCommit): ");
        commitID = userInput.nextLine();
        
        //find repository
        System.out.println("What directory would you like to initialize (Default=WorkingDirectory): ");
        repositoryDir = userInput.nextLine();
        if(repositoryDir.trim().equals("")) {
            //set working directory
            repositoryDir = System.getProperty("user.dir");
        }
        
        //get list of diffs from database
        
        //apply diffs to make files
        
    }
    
    private void initRepository() {
        
    }
}
