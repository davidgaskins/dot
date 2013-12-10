import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
    public static String homeDirectory;
    public static String emptyFile;
    //public static File emptyFile;
    /**
     * To be called at the start of this program. Makes sure that globally
     * needed files are present and creates them if not.
     */
    public static boolean init() {
        //get home directory
        try {
        Runtime r = Runtime.getRuntime();
            Process p = r.exec(new String[] {"sh", "-c", "echo ~"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            p.waitFor();
            homeDirectory = reader.readLine();
            System.out.println(homeDirectory);
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InterruptedException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        //check that the .dot folder exists
        File settingsDir = new File(homeDirectory + "/.dot");
        if(!settingsDir.isDirectory()){
            System.out.println("Creating global settings directory");
            if(!settingsDir.mkdir()) {
                System.err.println("Could not create settings directory");
                return false;
            }
        }
        
        //make sure there is an empty file for diffs
        File empty = new File(homeDirectory + "/.dot/empty");
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
        settings = new ConfigFile(homeDirectory + "/.dot/settings.conf");
        try {
            settings.load();
        } catch (FileNotFoundException ex) {
            System.out.println("Settings file not created yet");
        }
        //check for needed settings
        if(settings.get("user") == null) {
            settings.store("user", "Default User");
        }
        if(settings.get("repository count") == null) {
            settings.store("repository count", "0");
        }
        
        repositoryCount = Integer.parseInt(settings.get("repository count"));
        for(int i=0; i<repositoryCount && false; i++) {
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
            oldFile = homeDirectory + "/.dot/empty";
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
    
    public static void build(List<String> patches, String path) {
        try {
            File f = new File(path);
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public static String readFile(String path) 
    throws IOException 
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        Charset encoding = Charset.defaultCharset();
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
