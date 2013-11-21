import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

public class ConfigFile {
	private String fileName;
	private HashMap<String,String> map;

	public ConfigFile(String file) {
		this.fileName = file;
		file = null;
		map = new HashMap();
	}

	public load() throws FileNotFoundException {
		try {
			BufferedReader reader;
			FileInputStream stream;
			stream = new FileInputStream(fileName);
			reader = new BufferedReader(stream);


			String line;
			String[] splited;

			while((line = reader.readLine()) != null) {
				String key, value = "";
				splited = line.split(": ");
				if(splited.length < 2) continue;
				value = splited[1];
				for(int i=2; i<splited.length;i++)
					value += ": " + splited[i];
				map.put(key,value);
			}

		} catch(IOException e) {
			System.err.println(e);
		} finally {
			//close file
			stream.close();
		}
	}

	public void store(String key, String value) {
		map.put(key,value);
	}

	public String get(String key) {
		return map.get(key);
	}

	public void store() {
		//save values in map to file
	}

}