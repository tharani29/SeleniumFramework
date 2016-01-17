package selenium.modules;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebElementServer {
	
	public String[] getElement(String elementName) throws IOException{
		 
		  String filePath = "./src/main/java/selenium/externalResources/webElementBank.csv";
		  BufferedReader in = new BufferedReader(new FileReader(filePath));
	        String str;
	        String[][] webElements;
	        List<String> list = new ArrayList<>();
	        int lines = 0;
	        while ((str = in.readLine()) != null){
	        	
	        	list.add(str);
		        lines++;
	        }
	        in.close();
	        String[] stringArr = list.toArray(new String[0]);

	        webElements = new String[lines][3];
	        String[] element = new String[2];
	        for (int i=0; i<stringArr.length; i++){
	        	String[] parts = stringArr[i].split("~");
	        	webElements[i][0] = parts[0]; 
	        	webElements[i][1] = parts[1]; 
	        	webElements[i][2] = parts[2];
                if (webElements[i][0].equals(elementName)){
                    element[0]=  webElements[i][1];
                    element[1]=webElements[i][2];
                    System.out.println("    Interacting with "+elementName+", locator:"+element[0]);
                    break;
                }
            }
	       if (element[0]==null) {
			   System.out.println("Element '" + elementName + "', is not in the webElementBank");
		   }

	       
		return element;

	           
	       
}
	
}
