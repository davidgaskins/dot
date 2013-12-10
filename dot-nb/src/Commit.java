
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class Commit {
    
    private String author, email, message;
    private List<Change> fileList;
    private String directory;
    
    public Commit(String directory) {
        this.directory = directory;
        fileList = new LinkedList();
    }
    
    public void generateChanges() {
        File repository = new File(directory);
        if(!repository.isDirectory()) {
            System.err.println("Repository does not exist");
            return;
        }
        
        Map<String,File> childList = new TreeMap<String,File>();
        listFiles(directory, repository, childList);
        Iterator it = childList.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String,File> pairs = (Map.Entry<String,File>) it.next();
            System.out.println(pairs.getValue());
        }
        
        System.out.println("Printing previous commit files");
        
        Map<String,File> parentList = new TreeMap<String,File>();
        String parentDir = directory + "/.dot/previous_commit";
        File parentRepo = new File(parentDir);
        listFiles(parentDir, parentRepo, parentList);
        it = parentList.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String,File> pairs = (Map.Entry<String,File>) it.next();
            System.out.println(pairs.getValue());
        }
        
        fileList = new LinkedList<Change>();
        
        //match each file with parent
        it = childList.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String,File> pairs = (Map.Entry<String,File>) it.next();
            
            Change cur = new Change();
            cur.path = pairs.getKey();
            cur.child = pairs.getValue();
            cur.parent = parentList.get(cur.path);
            if(cur.parent == null) {
                cur.diff = FileUtil.diff(null, cur.child.getPath());
            } else {
                cur.diff = FileUtil.diff(cur.parent.getPath(), cur.child.getPath());
            }
            fileList.add(cur);
            System.out.println(cur);
            
        }
        
    }
    
    private void listFiles(String repoDir, File dir, Map<String,File> returnFileList) {
        File[] fileList = dir.listFiles();
        for(int i=0; i<fileList.length; i++) {
            if(fileList[i].isFile()) {
                //add all files to list
                String path = fileList[i].getPath();
                System.out.println("Absolute: " + path);
                path = path.substring(repoDir.length());
                System.out.println("Relative: " + path);
                
                returnFileList.put(path,fileList[i]);
            } else if(fileList[i].isDirectory() && !fileList[i].isHidden()){
                //recurse on all directories
                //will not recurse on hidden directory
                listFiles(repoDir, fileList[i], returnFileList);
            }
        }
    }
    
    //java needs structs
    public class Change {
        //path is relative to repository
        public String path;
        
        public File parent;
        public File child;
        
        
        public String diff;
        
        public String toString() {
            return path + "\n" + diff + "\n";
        }
    }
    
}
