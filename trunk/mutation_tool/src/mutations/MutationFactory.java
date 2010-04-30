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
	
	/**
	 * Creates permutations.
	 * @return collection of mutable objects
	 */
	public static /*ArrayList<IMutableObjects>*/MutantCollection createIMutableObjects(JavaClass oClass, Method oMethod){
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
						oMutant.setMutableMethod(oMethod);
						oMutant.setMethodName(oMethod.getName());
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
						oMutant.setMutableMethod(oMethod);
						oMutant.setMethodName(oMethod.getName());
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
						oMutant.setMutableMethod(oMethod);
						oMutant.setMethodName(oMethod.getName());
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


















