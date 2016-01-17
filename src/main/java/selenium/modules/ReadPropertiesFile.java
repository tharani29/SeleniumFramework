package selenium.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ReadPropertiesFile {
	
	public static String main(String property) {
		String value = null;
		try {
			File file = new File("./src/main/java/selenium/externalResources/config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String result = properties.getProperty(key);
				if (key.equals(property)){
					value = result;
				}					
			}
			if (value.equals(null)){
				System.out.println("Property:"+property+"not found.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
