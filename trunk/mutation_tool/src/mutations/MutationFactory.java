package mutations;

import java.util.*;
import org.apache.bcel.classfile.JavaClass;
import interfaces.IMutableObject;

public class MutationFactory{
	
	public MutationFactory(){}
	
	/**
	 * Creates permutations.
	 * @return
	 */
	public ArrayList<IMutableObject> createIMutableObjects(JavaClass oClass){
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
					oMutant.setMutantType(oMutant.stringToMutantType("ARITHMETIC"));
					oMutant.setMutantLevel(oMutant.stringToMutantLevel("CLASS"));
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
					oMutant.setMutantType(oMutant.stringToMutantType("RELATIONAL"));
					oMutant.setMutantLevel(oMutant.stringToMutantLevel("CLASS"));
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
					oMutant.setMutantType(oMutant.stringToMutantType("BOOLEAN"));
					oMutant.setMutantLevel(oMutant.stringToMutantLevel("CLASS"));
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