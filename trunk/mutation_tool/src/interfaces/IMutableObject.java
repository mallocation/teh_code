package interfaces;

public interface IMutableObject {
	
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