
import java.sql.Connection;
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
    
    public CommitsMenu(Logger LOGGER, Connection connection)
    {
        this.userInput = new Scanner(System.in);
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    public void commitsMenu()
    {
        //this is test code
        Commit commity = new Commit(FileUtil.settings.get("repository"));
        commity.generateChanges();
    }
}
