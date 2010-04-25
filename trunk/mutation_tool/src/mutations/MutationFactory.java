package mutations;

import java.util.*;
import org.apache.bcel.classfile.JavaClass;
import interfaces.IMutableObject;
import mutations.Mutant;

public class MutationFactory{
	
	public MutationFactory(){}
	
	/**
	 * Creates permutations.
	 * @return
	 */
	public static ArrayList<IMutableObject> createIMutableObjects(JavaClass oClass){
		String arithmeticOps[] = {"+", "-", "*", "/", "%"};
		String relationalOps[] = {"==", "!=", "<", "<=", ">", ">="};
		String booleanOps[] = {"&&", "||"};
		int length = arithmeticOps.length;
		
		ArrayList<IMutableObject> oMutableObjects = new ArrayList<IMutableObject>();
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				if(i != j){
					IMutableObject oMutant = new Mutant();
					oMutant.setOldOperator(arithmeticOps[i]);
					oMutant.setNewOperator(arithmeticOps[j]);
					oMutant.setMutantType(Mutant.eMutantType.ARITHMETIC);
					oMutant.setMutantLevel(Mutant.eMutantLevel.CLASS);
					oMutant.setMutableClass(oClass);
					Mutator oMutator = new Mutator(oMutant);
					if(oMutator.getMutationCount() != 0){
						oMutableObjects.add(oMutant);
					}
					
				}
			}
		}
		
		length = relationalOps.length;
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				if(i != j){
					IMutableObject oMutant = new Mutant();
					oMutant.setOldOperator(relationalOps[i]);
					oMutant.setNewOperator(relationalOps[j]);
					oMutant.setMutantType(Mutant.eMutantType.RELATIONAL);
					oMutant.setMutantLevel(Mutant.eMutantLevel.CLASS);
					oMutant.setMutableClass(oClass);
					Mutator oMutator = new Mutator(oMutant);
					if(oMutator.getMutationCount() != 0){
						oMutableObjects.add(oMutant);
					}
				}
			}
		}
		
		length = booleanOps.length;
		for(int i = 0; i < length; i++){
			for(int j = 0; j < length; j++){
				if(i != j){
					IMutableObject oMutant = new Mutant();
					oMutant.setOldOperator(relationalOps[i]);
					oMutant.setNewOperator(relationalOps[j]);
					oMutant.setMutantType(Mutant.eMutantType.LOGICAL);
					oMutant.setMutantLevel(Mutant.eMutantLevel.CLASS);
					oMutant.setMutableClass(oClass);
					Mutator oMutator = new Mutator(oMutant);
					if(oMutator.getMutationCount() != 0){
						oMutableObjects.add(oMutant);
					}
					
				}
			}
		}
		return oMutableObjects;
	}
	
	
}