
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
public class SampleQueryMenu
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput;

    public SampleQueryMenu(Logger LOGGER, Connection connection)
    {
        this.userInput = new Scanner(System.in);
        this.LOGGER = LOGGER;
        this.connection = connection;
    }
}
