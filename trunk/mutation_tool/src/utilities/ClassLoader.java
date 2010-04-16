package utilities;

import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

/**
 * This class performs the tasks necessary for loading class files.
 * 
 * @author teh_code
 *
 */
public class ClassLoader {
	
	/**
	 * Load a class using a specific file name (includes path).
	 * 
	 * @param sFileName file name of the class
	 * @return <code>JavaClass</code> if the class is found and is a class.
	 * 		   <code>null</code> if there is a problem loading the class.
	 */
	public static JavaClass loadClassFromPath(String sFileName) {
		try {
			ClassParser oClassParser = new ClassParser(sFileName);			
			return oClassParser.parse();			
		} catch (IOException e) {
			return null;
		}
	}

}
