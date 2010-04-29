package interfaces;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

/**
 * IMutableObject defines a template for all mutations within the MutationTool.
 * 
 * @author teh_code
 *
 */
public interface IMutableObject {
	
	/**
	 * Sets the JavaClass of the mutation that will be mutated.
	 * @param oMutableClass <code>JavaClass</code> class to be mutated.
	 */
	public void setMutableClass(JavaClass oMutableClass);
	
	/**
	 * Sets the Method of the JavaClass that will be mutated.
	 * This should be set only if the mutation level is METHOD.
	 * @param oMutableMethod <code>Method</code> method to be mutated.
	 */
	public void setMutableMethod(Method oMutableMethod);
	
	/**
	 * Gets the class that will be mutated.
	 * @return <code>JavaClass</code> that will be mutated.
	 */
	public JavaClass getMutableClass();
	
	/**
	 * Gets the method that will be mutated.
	 * @return <code>Method</code> that will be mutated.
	 */
	public Method getMutableMethod();
	
	/**
	 * Sets the type of mutation, as defined in the <code>eMutantType</code> enumeration.
	 * @param oMutantType <code>eMutantType</code> type to set i.e. ARITHMETIC
	 */
	public void setMutantType(eMutantType oMutantType);
	
	/**
	 * Sets the level of mutation, defining whether the object represents a package-wide mutation, class-wide mutation, or
	 * a mutation specific to a method, as defined in the <code>eMutantLevel</code> enumeration.
	 * @param oMutantLevel <code>eMutantLevel</code> level to set i.e. CLASS
	 */
	public void setMutantLevel(eMutantLevel oMutantLevel);
	
	
	public eMutantType getMutantType();
	
	public String getMutantTypeAsString();
	
	public eMutantLevel getMutantLevel();
	
	public String getMutantLevelAsString();	
	
	public void setOldOperator(String oldOp);
	
	public void setNewOperator(String newOp);
	
	public void setMethodName(String methodName);
	
	public String getOldOperator();
	
	public String getNewOperator();
	
	public String getMethodName();
	
	//public eMutantLevel stringToMutantLevel(String sMutantLevel);
	
	//public eMutantType stringToMutantType(String sMutantType);
	
	public void printMutableObjectProperties();
	
	public enum eMutantType {
		ARITHMETIC,
		LOGICAL,
		RELATIONAL
	}
	
	public enum eMutantLevel {
		PACKAGE,
		CLASS,
		METHOD
	}

}
