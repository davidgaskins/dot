
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 * This class is for organizing all file functions. Silly java for forcing 
 * objects everywhere.
 */
public class FileUtil {
    
    public static ConfigFile settings;
    public static int repositoryCount = 0;
    /**
     * To be called at the start of this program. Makes sure that globally
     * needed files are present and creates them if not.
     */
    public static boolean init() {
        //check that the .dot folder exists
        File settingsDir = new File("~/.dot");
        if(!settingsDir.isDirectory()){
            System.out.println("Creating global settings directory");
            if(!settingsDir.mkdir()) {
                System.err.println("Could not create settings directory");
                return false;
            }
        }
        
        //make sure there is an empty file for diffs
        File empty = new File("~/.dot/empty");
        empty.delete();
        try {
            empty.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!empty.isFile()) {
            System.err.println("Unable to create empty file for diffs");
            return false;
        }
        
        
        //load config file
        settings = new ConfigFile("~/.dot/settings.conf");
        try {
            settings.load();
        } catch (FileNotFoundException ex) {
        }
        //check for needed settings
        if(settings.get("user") == null) {
            settings.store("user", "Default User");
        }
        if(settings.get("repository count") == null) {
            settings.store("repository count", "0");
        }
        
        repositoryCount = Integer.parseInt(settings.get("repository count"));
        for(int i=0; i<repositoryCount; i++) {
            if(checkRepository(settings.get("repostory " + i))){
                System.err.println("Repository " + settings.get("repository " + i) + " is invalid");
            }
        }
        
        
        return true;
    }
    
    public static String diff(String oldFile, String newFile) {
        //check that both files exist
        //if first file is null, assign to empty file
        //if second is null or invalid, return null
        if(oldFile == null) {
            oldFile = "~/.dot/empty";
        }
        if(newFile == null) {
            System.err.println("Comparison file is null");
        }
        
        String result = "";
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec("diff " + oldFile + " " + newFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            p.waitFor();
            String line;
            while((line = reader.readLine()) != null) {
                result += line + "\n";
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    static boolean checkRepository(String directory) {
        File dir = new File(directory);
        return dir.isDirectory();
    }
    
    static void close() {
        if(settings != null) {
            settings.save();
        }
    }
}
