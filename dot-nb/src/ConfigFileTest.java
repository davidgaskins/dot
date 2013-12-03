
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class ConfigFileTest {
    public static void main(String args[]) throws FileNotFoundException, IOException {
        if(args.length != 1) {
            System.out.println("Invalid argument");
            return;
        }
        String file = args[0];
        ConfigFile test = new ConfigFile(file);
        test.load();
        test.print();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) != null) {
            String[] splited = line.split(" ");
            switch(splited[0]) {
                case "store":
                case "s":
                    if(splited.length == 3)
                    test.store(splited[1], splited[2]);
                    break;
                case "get":
                case "g":
                    if(splited.length == 2)
                        System.out.println(test.get(splited[1]));
                    break;
                case "print":
                case "p":
                    test.print();
                    break;
                case "save":
                    if(splited.length == 1)
                        test.save();
                    break;
                case "quit":
                case "q":
                    return;
            }
        }
    }
}
