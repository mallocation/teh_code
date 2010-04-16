package mutations;

import interfaces.IMutableObject;

public class Mutant implements IMutableObject {
	
	private eMutantLevel mutantLevel;
	private eMutantType mutantType;	

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
	
	private String mutantLevelToString(eMutantLevel oMutantLevel) {
		if (oMutantLevel == eMutantLevel.PACKAGE)
			return "PACKAGE";
		else if (oMutantLevel == eMutantLevel.CLASS)
			return "CLASS";
		else if (oMutantLevel == eMutantLevel.METHOD)
			return "METHOD";
		else
			return null;		
	}
	
	private String mutantTypeToString(eMutantType oMutantType) {
		if (oMutantType == eMutantType.ARITHMETIC)
			return "ARITHMETIC";
		else if (oMutantType == eMutantType.LOGICAL)
			return "LOGICAL";
		else if (oMutantType == eMutantType.RELATIONAL)
			return "RELATIONAL";
		else
			return null;		
	}
	
//	private eMutantLevel stringToMutantLevel(String sMutantLevel) {
//		sMutantLevel = sMutantLevel.trim().toUpperCase();
//		
//		if (sMutantLevel.equals("PACKAGE"))
//			return eMutantLevel.PACKAGE;
//		else if (sMutantLevel.equals("CLASS"))
//			return eMutantLevel.CLASS;
//		else if (sMutantLevel.equals("METHOD"))
//			return eMutantLevel.METHOD;
//		else
//			return null;
//	}
}