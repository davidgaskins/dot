
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    
    public static String diff(String oldFile, String newFile) 
    {
        return null;
    }
    
    public static String readFile(String path) 
    throws IOException 
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        Charset encoding = Charset.defaultCharset();
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    } 
}
