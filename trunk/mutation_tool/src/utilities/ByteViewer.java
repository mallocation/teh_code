package utilities;

import java.io.*;

import org.apache.bcel.classfile.*;  // The static component of the BCEL API 
import org.apache.bcel.Repository;

/* The ByteViewer is used to display a human-readable listing of the
   compiled Java bytecode for a class.
*/ 

public class ByteViewer {
	
	public JavaClass classClass;
	public Method[] classMethods;
	public Code classCode;

  /* Throws IOException If there is an error reading from the class file.
  */
  public void generateByteCode(String className) {

    try {
      /* Load the class from CLASSPATH. 
      */
      JavaClass       clazz   = Repository.lookupClass(className);

      /* Get data in a method. 
      */ 
      Method[]        methods = clazz.getMethods();

      System.out.println(clazz);
      for(int i=0; i < methods.length; i++) {
	  System.out.println(methods[i]);
	  Code code = methods[i].getCode();
	  if(code != null)
	      System.out.println(code);
      }
    } catch (Exception e) { e.printStackTrace(); } 
  }
}
