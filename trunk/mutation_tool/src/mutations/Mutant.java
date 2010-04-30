package mutations;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import interfaces.IMutableObject;

/**
 * Mutant is used to create an individual mutant
 * 
 * @author teh code
 *
 */
public class Mutant implements IMutableObject {
	
	private eMutantLevel mutantLevel;
	private eMutantType mutantType;	
	private JavaClass oMutableClass;
	private Method oMutableMethod;
	private String oldOp;
	private String newOp;
	private String methodName;
	
	@Override
	public JavaClass getMutableClass() {
		return this.oMutableClass;
	}

	@Override
	public void setMutableClass(JavaClass oMutableClass) {
		this.oMutableClass = oMutableClass;		
	}

	@Override
	public void setMutantLevel(eMutantLevel oMutantLevel) {
		this.mutantLevel = oMutantLevel;	
	}

	@Override
	public void setMutantType(eMutantType oMutantType) {
		this.mutantType = oMutantType;
	}

	@Override
	public eMutantLevel getMutantLevel() {
		return this.mutantLevel;
	}

	@Override
	public eMutantType getMutantType() {
		return this.mutantType;
	}
	
	@Override
	public String getMutantLevelAsString() {
		return mutantLevelToString(this.mutantLevel);
	}

	@Override
	public String getMutantTypeAsString() {
		return mutantTypeToString(this.mutantType);
	}	
	
	@Override
	public Method getMutableMethod() {
		return this.oMutableMethod;
	}

	@Override
	public void setMutableMethod(Method oMutableMethod) {
		this.oMutableMethod = oMutableMethod;
	}

	@Override
	public void setOldOperator(String oldOp){
		this.oldOp = oldOp;
	}
	
	@Override
	public void setNewOperator(String newOp){
		this.newOp = newOp;
	}
	
	@Override
	public void setMethodName(String methodName){
		this.methodName = methodName;
	}
	
	@Override
	public String getOldOperator(){
		return this.oldOp;
	}
	
	@Override
	public String getNewOperator(){
		return this.newOp;
	}

	@Override
	public String getMethodName(){
		return this.methodName;
	}
	
	@Override
	public void printMutableObjectProperties(){
		System.out.println("Class Name: " +getMutableClass().getClassName());
		System.out.println("Old Operator: " + getOldOperator());
		System.out.println("New Operator: " + getNewOperator());
		System.out.println("Mutant Type: " + getMutantTypeAsString());
		System.out.println("Mutant Level: " + getMutantLevelAsString());
		if(getMutantLevelAsString().equals("METHOD")){
			System.out.println("Method Name: " + getMethodName());
		}
		
	}
	
	/**
	 * mutantLevelToString converts the mutant level to a string value
	 * 
	 * @param oMutantLevel mutant level
	 * @return
	 */
	public static String mutantLevelToString(eMutantLevel oMutantLevel) {
		if (oMutantLevel == eMutantLevel.PACKAGE)
			return "PACKAGE";
		else if (oMutantLevel == eMutantLevel.CLASS)
			return "CLASS";
		else if (oMutantLevel == eMutantLevel.METHOD)
			return "METHOD";
		else
			return null;		
	}
	
	/**
	 * mutantTypeToString converts the mutant type to a string value
	 * 
	 * @param oMutantType mutant type
	 * @return
	 */
	public static String mutantTypeToString(eMutantType oMutantType) {
		if (oMutantType == eMutantType.ARITHMETIC)
			return "ARITHMETIC";
		else if (oMutantType == eMutantType.LOGICAL)
			return "LOGICAL";
		else if (oMutantType == eMutantType.RELATIONAL)
			return "RELATIONAL";
		else
			return null;		
	}
	
	/**
	 * stringToMutantLevel converts a string to a mutant level
	 * 
	 * @param sMutantLevel mutant level
	 * @return
	 */
	public static eMutantLevel stringToMutantLevel(String sMutantLevel) {
		sMutantLevel = sMutantLevel.trim().toUpperCase();
		
		if (sMutantLevel.equals("PACKAGE"))
			return eMutantLevel.PACKAGE;
		else if (sMutantLevel.equals("CLASS"))
			return eMutantLevel.CLASS;
		else if (sMutantLevel.equals("METHOD"))
			return eMutantLevel.METHOD;
		else
			return null;
	}
	
	/**
	 * stringToMutantType converts a string to a mutant type
	 * 
	 * @param sMutantType mutant type
	 * @return
	 */
	public static eMutantType stringToMutantType(String sMutantType) {
		sMutantType = sMutantType.trim().toUpperCase();
		
		if (sMutantType.equals("ARITHMETIC"))
			return eMutantType.ARITHMETIC;
		else if (sMutantType.equals("LOGICAL"))
			return eMutantType.LOGICAL;
		else if (sMutantType.equals("RELATIONAL"))
			return eMutantType.RELATIONAL;
		else
			return null;
	}
	
	/**
	 * getAllMutantTypesAsString returns all mutant types in an array of strings
	 * 
	 * @return an array of strings containing all mutant types
	 */
	public static String[] getAllMutantTypesAsString() {
		return new String[] {mutantTypeToString(eMutantType.ARITHMETIC), mutantTypeToString(eMutantType.LOGICAL), mutantTypeToString(eMutantType.RELATIONAL)};
	}	
}