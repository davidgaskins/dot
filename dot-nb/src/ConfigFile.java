/*
ConfigFile.java
Reads the configuration file
David Gaskins
David Martel
Tan Tran
*/
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigFile {

    private String fileName;
    private HashMap<String, String> map;

    public ConfigFile(String file) {
        this.fileName = file;
        file = null;
        map = new HashMap();
    }

    public void load() throws FileNotFoundException{

        BufferedReader reader = null;
        FileReader stream = null;
        try {
            File f = new File(fileName);
            if(!f.exists())
                f.createNewFile();
            stream = new FileReader(fileName);
            reader = new BufferedReader(stream);

            String line;
            String[] splited;

            while ((line = reader.readLine()) != null) {
                String key = null, value = "";
                splited = line.split(": ");
                if (splited.length < 2) {
                    continue;
                }
                value = splited[1];
                for (int i = 2; i < splited.length; i++) {
                    value += ": " + splited[i];
                }
                key = splited[0];
                map.put(key, value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close file
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void store(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public void save() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
            for (Map.Entry<String,String> pairs : map.entrySet()) {
                writer.write(pairs.getKey() + ": " + pairs.getValue() + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void print() {
        for (Map.Entry<String,String> pairs : map.entrySet()) {
            System.out.println(pairs.getKey() + " : " + pairs.getValue());
        }
    }

}
