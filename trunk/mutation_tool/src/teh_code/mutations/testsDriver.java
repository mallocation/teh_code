package teh_code.mutations;

import java.io.IOException;
import java.util.jar.*;
import org.apache.bcel.classfile.JavaClass;

import teh_code.interfaces.IMutableObject;
import teh_code.mutations.MutantCollection;
import teh_code.mutations.MutationFactory;
import teh_code.mutations.Mutator;
import teh_code.utilities.ClassLoader;
import java.util.*;
import org.apache.bcel.classfile.*;

public class testsDriver{
	
	private static void showError(String sError) {
		System.out.println(sError);
		System.exit(1);
	}
	/**
	 * This class is for testing Mutator and MutatorFactory: The path
	 * was created specifically for my computer -> I'm sure there is an
	 * easy way to get the path, but I didn't want to waste time.
	 * If you would like to be able to run this, just create a class
	 * and specify its path in the 'path' variable.
	 * @param args
	 */
	public static void main(String args[]){
		String path = "/Users/Pavel/Documents/workspace/mutation_tool/src/mutations/";

		System.out.println("" + args[0] + "" + args[1] + "" + args[2]);

		IMutableObject oMutant = new Mutant();
		JavaClass oClass = null;
		String oldOp = null;
		String newOp = null;
		try{
			String sClassWithPath = path + args[0] + ".class";
			if(ClassLoader.isClassFile(sClassWithPath)){
				oClass = ClassLoader.loadClassFromPath(sClassWithPath);

			}
			else{
				showError("Not a valid class file.");
			}
		}
		catch(Exception e){
			showError("Specify valid class file.");
		}
		try{
			oldOp = args[1].replace("'", "");
			newOp = args[2].replace("'", "");
		}catch (Exception e){
			showError("Specify operators.");
		}
		
		oMutant.setMutableClass(oClass);
		oMutant.setOldOperator(oldOp);
		oMutant.setNewOperator(newOp);
		oMutant.setMutantLevel(IMutableObject.eMutantLevel.METHOD);
		//To test at method-level, just have a class that has the name specified below
		oMutant.setMethodName("sillyAddition");
		System.out.println("" + Mutator.getMutationCount(oMutant));
		//Mutator.performMutation(oMutant);
		MutantCollection mutableObjectsList = MutationFactory.createIMutableObjects(oClass, null);
		Iterator<IMutableObject> iterator = mutableObjectsList.getMutants().iterator();
		
		while(iterator.hasNext()){
			((IMutableObject) iterator.next()).printMutableObjectProperties();
			//Mutator.performMutation((IMutableObject) iterator.next());
		}
		
	/*	JarFile littleJar = null;
		try {
			 littleJar = new JarFile("/Users/Pavel/Documents/workspace/mutation_tool/bcel-5.2.jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Enumeration e = littleJar.entries() ; e.hasMoreElements();){
			Object z =  e.nextElement();
			JavaClass myClass = (JavaClass) z;
			if( z.toString().contains(".class")){
				System.out.println(z);
			}
		}
		
		
		//Go into MutationFactory to set the correct path for your JAR file.
		//See comments in MutationFactory for further instructions.
		MutationFactory.arIMObjectsFromJar();*/
		
	}
}
