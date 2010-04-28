package interfaces;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public interface IMutableObject {
	
	public void setMutableClass(JavaClass oMutableClass);
	
	public void setMutableMethod(Method oMutableMethod);
	
	public JavaClass getMutableClass();
	
	public Method getMutableMethod();
	
	public void setMutantType(eMutantType oMutantType);
	
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
