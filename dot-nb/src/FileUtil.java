/*
This class organizes all the file functions
David Martel
David Gaskins
Tan Tran
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
            //System.out.println(homeDirectory);
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
        
        settings.save();
        
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
        System.out.println("Building file " + path);
        try {
            //create new file
            File f = new File(path);
            f.createNewFile();
            
            
            Runtime r = Runtime.getRuntime();
            //apply patches
            
            for(String patch: patches) {
                System.out.println("About to apply: \n" + patch);
                //create patch file (because piping didn't work)
                File patchy = new File(path + ".patch");
                patchy.createNewFile();
                BufferedWriter patchWriter = new BufferedWriter(new FileWriter(patchy,true));
                patchWriter.write(patch);
                /*
                String lines[] = patch.split("\n");
                for(int i=0; i<lines.length; i++) {
                    patchWriter.write(lines[i] + "\n");
                    //System.out.println(lines[i]);
                }
                        */
                patchWriter.flush();
                patchWriter.close();
                
                Process p = r.exec("patch " + path + " " + path + ".patch");
                p.waitFor();
               // if(!path.contains("build"))
                    //patchy.delete();
            
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
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
