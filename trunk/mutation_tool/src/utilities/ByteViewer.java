package utilities;

import org.apache.bcel.classfile.*;  // The static component of the BCEL API 
import utilities.ClassLoader;

/* This class is used to get set create on object of a class to store
 * bytecode of its class and methods.
*/ 

public class ByteViewer {
	
	public JavaClass oClass;
	public Method[] oClassMethods;

	// Throws IOException If there is an error reading from the class file.
	public void generateByteCode(String className) {
		try {
			//set object class and methods
			oClass = ClassLoader.loadClassFromPath(className);
			oClassMethods = oClass.getMethods();
			
    } catch (Exception e) { e.printStackTrace(); } 
  }
}
