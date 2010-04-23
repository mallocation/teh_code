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
