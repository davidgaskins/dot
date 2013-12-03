
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
    private final Scanner userInput = new Scanner(System.in);
    private String queryOrStatement;
    
    public CommitsMenu(Logger LOGGER, Connection connection)
    {
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
    
    private void commitMenu()
    {
        System.out.println("The transaction has been commited.");
        //need to add the commit code       
    }
}
