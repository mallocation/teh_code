package mutations;

import java.io.IOException;
import java.util.*;
import java.util.jar.*;
import mutations.MutantCollection;

import org.apache.bcel.classfile.JavaClass;
import interfaces.IMutableObject;
import mutations.Mutant;
import mutations.cInstructionHelper;
import org.apache.bcel.classfile.Method;
import utilities.ClassLoader;

public class MutationFactory{
	//Change Variables to test JAR support.  To test, just set the correct path.  The
	// simple file test.jar has been uploaded to 'mutations' so you just have to change
	// the path to your correct one. Check out the files -> The possible mutations are correct.
	// There are still a few lose ends  such as setting the IMutableObjectLevel....
	public static String jarPath = "/Users/Pavel/Documents/workspace/mutation_tool/src/mutations/";
	public static String jarName = "test.jar";
	public MutationFactory(){}
	
	public static void printNestedArrayListToSeeIfPavelIsDumbOrNot(ArrayList<ArrayList<IMutableObject>> myNestedList){
		
		Iterator<ArrayList<IMutableObject>> iterator = myNestedList.iterator();
		while(iterator.hasNext()){
			ArrayList<IMutableObject> nestedArrayList =  iterator.next();
			Iterator<IMutableObject> itr = nestedArrayList.iterator();
			while(itr.hasNext()){
				itr.next().printMutableObjectProperties();
			}
		}
	}
	
	public static /*ArrayList<ArrayList<IMutableObject>>*/ void  arIMObjectsFromJar (){
		
		ArrayList<ArrayList<IMutableObject>>  arIMutObjects = new ArrayList<ArrayList<IMutableObject>>();
		JarFile littleJar = null;
		try {
			 littleJar = new JarFile(jarPath + jarName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Enumeration e = littleJar.entries() ; e.hasMoreElements();){
			Object z =  e.nextElement();
			if( z.toString().contains(".class")){
				JavaClass myClass = loadJavaClass(z);
				arIMutObjects.add(createIMutableObjects(myClass, null).getMutants());
			}
		}
		printNestedArrayListToSeeIfPavelIsDumbOrNot(arIMutObjects);
		/*return arIMutObjects;*/
	}
	
	public static JavaClass loadJavaClass(Object obj){
		JavaClass oClass = null;
		try{
			String sClassWithPath = jarPath + obj.toString();
			if(ClassLoader.isClassFile(sClassWithPath)){
				oClass = ClassLoader.loadClassFromPath(sClassWithPath);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return oClass;
	}
	/**
	 * Creates permutations.
	 * @return
	 */
	public static /*ArrayList<IMutableObjects>*/MutantCollection createIMutableObjects(JavaClass oClass, String oMethod){
		String arithmeticOps[] = cInstructionHelper.getArithmeticInstructionsAsString();
		String relationalOps[] = cInstructionHelper.getRelationalInstructionsAsString();
		String booleanOps[] = cInstructionHelper.getBooleanInstructionsAsString();
		MutantCollection oMutableObjects = new MutantCollection();
		//ArrayList<IMutableObject> oMutableObjects = new ArrayList<IMutableObject>();
		int length;
		
		length = arithmeticOps.length;
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				if(i != j){
					IMutableObject oMutant = new Mutant();
					oMutant.setMutableClass(oClass);
					oMutant.setOldOperator(arithmeticOps[i]);
					oMutant.setNewOperator(arithmeticOps[j]);
					oMutant.setMutantType(IMutableObject.eMutantType.ARITHMETIC);
					if(oMethod == null){
						oMutant.setMutantLevel(IMutableObject.eMutantLevel.CLASS);
					}else{
						oMutant.setMutantLevel(IMutableObject.eMutantLevel.METHOD);
					//	oMutant.setMutableMethod(oMethod);
						oMutant.setMethodName(oMethod/*.getName()*/);
					}
					if(Mutator.getMutationCount(oMutant) != 0){
						oMutableObjects.getMutants().add(oMutant);
					}
				}
			}
		}
		
		length = relationalOps.length;
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				if(i != j){
					IMutableObject oMutant = new Mutant();
					oMutant.setMutableClass(oClass);
					oMutant.setOldOperator(relationalOps[i]);
					oMutant.setNewOperator(relationalOps[j]);
					oMutant.setMutantType(IMutableObject.eMutantType.RELATIONAL);
					if(oMethod == null){
						oMutant.setMutantLevel(IMutableObject.eMutantLevel.CLASS);
					}else{
						oMutant.setMutantLevel(IMutableObject.eMutantLevel.METHOD);
					//	oMutant.setMutableMethod(oMethod);
						oMutant.setMethodName(oMethod/*.getName()*/);
					}
					if(Mutator.getMutationCount(oMutant) != 0){
						oMutableObjects.getMutants().add(oMutant);
					}
				}
			}
		}
		
		length = booleanOps.length;
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				if(i != j){
					IMutableObject oMutant = new Mutant();
					oMutant.setMutableClass(oClass);
					oMutant.setOldOperator(booleanOps[i]);
					oMutant.setNewOperator(booleanOps[j]);
					oMutant.setMutantType(IMutableObject.eMutantType.LOGICAL);
					oMutant.setMutantLevel(IMutableObject.eMutantLevel.CLASS);
					if(oMethod == null){
						oMutant.setMutantLevel(IMutableObject.eMutantLevel.CLASS);
					}else{
						oMutant.setMutantLevel(IMutableObject.eMutantLevel.METHOD);
					//	oMutant.setMutableMethod(oMethod);
						oMutant.setMethodName(oMethod/*.getName()*/);
					}
					if(Mutator.getMutationCount(oMutant) != 0){
						oMutableObjects.getMutants().add(oMutant);
					}
					
				}
			}
		}
		return oMutableObjects;
	}
}


















