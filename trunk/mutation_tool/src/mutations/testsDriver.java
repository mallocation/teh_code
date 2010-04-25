package mutations;

import mutations.Mutator;
import interfaces.IMutableObject;
import org.apache.bcel.classfile.JavaClass;
import utilities.ClassLoader;
import mutations.MutationFactory;
import java.util.*;

public class testsDriver{
	
	private static void showError(String sError) {
		System.out.println(sError);
		System.exit(1);
	}
	public static void main(String args[]){
		String path = "/Users/Pavel/Documents/workspace/mutation_tool/src/mutations/";

		System.out.println(args.length);
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
		Mutator oMutator = new Mutator(oMutant);
		System.out.println("" + oMutator.getMutationCount());
		oMutator.performMutation();
		MutationFactory oFactory = new MutationFactory();
		ArrayList<IMutableObject> mutableObjectsList = oFactory.createIMutableObjects(oClass);
		Iterator iterator = mutableObjectsList.iterator();
		
		while(iterator.hasNext()){
			((IMutableObject) iterator.next()).printMutableObjectProperties();
		}
		
		
	}
}
