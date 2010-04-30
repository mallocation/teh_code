package utilities;

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
			JavaClass oClassToReturn = oClassParser.parse();
			return oClassToReturn;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Call this method to determine if a file at a specific path is a
	 * valid class file.
	 * 
	 * @param sFileName path of file to check
	 * @return <code>true</code> if the file is a valid class file
	 * 		   <code>false</code> if the file is not a valid class file
	 */
	public static boolean isClassFile(String sFileName) {
		if (loadClassFromPath(sFileName) != null)
			return true;
		else
			return false;
	}
	
	public static boolean classIsAbstractOrInterface(JavaClass oClass) {
		return oClass.isAbstract() || oClass.isInterface();	
	}

}
