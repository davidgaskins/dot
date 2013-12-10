
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * The Dot user is not presented with a Projects menu from the main menu.
 * However, other menu objects such as GoalsMenu and ContributorsMenu
 * use ProjectsMenu to display a list of projects. For now we do not
 * add functionality to create a project.
 */

/**
 *
 * @author Tan Tran
 */
public class ProjectsMenu
{
    Connection connection;
    private static Logger LOGGER;
    private final Scanner userInput;
        
    public ProjectsMenu(Logger LOGGER, Connection connection)
    {
        this.userInput = new Scanner(System.in);
        this.LOGGER = LOGGER;
        this.connection = connection;
    }

    public void projectsMenuView()
    {
        ResultSet rs = null;
        try
        {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM projects ORDER BY id ASC");
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error retrieving projects. Error: {0}", sqe.getMessage());
            System.out.println("There was an error retreiving projects.");
        }
        
        String columnNames = "ID\t Title";
        System.out.println(columnNames);
        try
        {
            while (rs.next())
            {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String displayRow = String.format("%4d, %20s", id, title);
                System.out.println(displayRow);
            }
        }
        catch (SQLException sqe)
        {
            LOGGER.log(Level.SEVERE, "Error processing result set. Error: {0}", sqe.getMessage());
            System.out.println("There was an error in processing the result set.");
            return;
        }
    }
}
