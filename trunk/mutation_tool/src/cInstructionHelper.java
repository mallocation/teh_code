import java.util.*;
import java.io.*;
/*import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.ArithmeticInstruction;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.BranchInstruction;*/
import org.apache.bcel.generic.*;
import org.apache.bcel.Constants;

/**
 * cInstructionHelper is a helper class for the ChangeOp program.
 * This class handles all operator/instruction verification.
 *
 * @author cjohnson
 * @version 1.0
 */
public class cInstructionHelper {
	
	/**
	 * Valid arithmetic instructions of type DOUBLE.
	 */
	private ArithmeticInstruction[] arDoubleInstructions = {InstructionFactory.DADD, InstructionFactory.DSUB, InstructionFactory.DMUL, InstructionFactory.DDIV, InstructionFactory.DREM};
	
	/**
	 * Valid arithmetic instructions of type FLOAT.
	 */
	private ArithmeticInstruction[] arFloatInstructions = {InstructionFactory.FADD, InstructionFactory.FSUB, InstructionFactory.FMUL, InstructionFactory.FDIV, InstructionFactory.FREM};
	
	/**
	 * Valid arithmetic instructions of type INT.
	 */
	private ArithmeticInstruction[] arIntInstructions = {InstructionFactory.IADD, InstructionFactory.ISUB, InstructionFactory.IMUL, InstructionFactory.IDIV, InstructionFactory.IREM};
	
	/**
	 * Valid arithmetic instructions of type LONG.
	 */
 	private ArithmeticInstruction[] arLongInstructions = {InstructionFactory.LADD, InstructionFactory.LSUB, InstructionFactory.LMUL, InstructionFactory.LDIV, InstructionFactory.LREM};
	
	/**
	 * Valid branch instructions of type INT
	 */
	private ArrayList<String> al_IntBranchInstructions;
	
	/**
	 * 'A' type branch instructions compare object references.
	 */
	private ArrayList<String> al_ABranchInstructions;
	
	/**
	 * BranchInstructions instructions that compare to zero/null and branch accordingly.
	 */
	private ArrayList<String> al_ZeroNullBranchInstructions;
	
	public cInstructionHelper(){
		al_IntBranchInstructions = new ArrayList<String>();
		al_ABranchInstructions = new ArrayList<String>();
		al_ZeroNullBranchInstructions = new ArrayList<String>();
		al_IntBranchInstructions.add("if_icmpeq");al_IntBranchInstructions.add("if_icmpne");al_IntBranchInstructions.add("if_icmplt");al_IntBranchInstructions.add("if_icmple");
		al_IntBranchInstructions.add("if_icmpgt");al_IntBranchInstructions.add("if_icmpge");
		
		al_ABranchInstructions.add("if_acmpeq");al_ABranchInstructions.add("if_acmpne");
		
		al_ZeroNullBranchInstructions.add("ifeq");al_ZeroNullBranchInstructions.add("ifne");al_ZeroNullBranchInstructions.add("iflt");al_ZeroNullBranchInstructions.add("ifle");
		al_ZeroNullBranchInstructions.add("ifgt");al_ZeroNullBranchInstructions.add("ifge");al_ZeroNullBranchInstructions.add("ifnull");al_ZeroNullBranchInstructions.add("ifnonull");
	}
	
	
	/**
	 * Checks if a specific Instruction in the Java virtual machine is an Arithmetic Instruction.
	 *
	 * @param oInstruction the instruction to check
	 * 
	 * @return <code>true</code> if the instruction is an arithmetic instruction,
	 *         <code>false</code> otherwise
	 */
	public boolean isArithmeticInstruction(Instruction oInstruction) {
		if (oInstruction instanceof ArithmeticInstruction) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isBranchInstruction(Instruction oInstruction){
		String instructionName = oInstruction.getName();
	//	System.out.println("" + instructionName);
		if(al_IntBranchInstructions.indexOf(instructionName) != -1){
			return true;
		}
		else if(al_ABranchInstructions.indexOf(instructionName) != -1){
			return true;
		}
		else if(al_ZeroNullBranchInstructions.indexOf(instructionName) != -1){
			return true;
		}else{
			return false;
		}
	}
			
	
	/**
	 * Checks if a specific instruction is that of the operator type.
	 * i.e. IADD corresponds to the "+" operator, and would therefore return true
	 *
	 * @param oInstruction the instruction to examine
	 * @param sOperator the desired operator to match
	 *
	 * @return <code>true</code> if the instructions is representative of the given operator,
	 *         <code>false</code> otherwise
	 */
	public boolean InstructionMatchOperator(Instruction oInstruction, String sOperator) {
	//	System.out.println("" + oInstruction.getName() );
		return oInstruction.getName().contains(OperatorToInstName(sOperator));
	}
	
	/**
	 * Converts an Arithmetic Instruction to that of the new operator.
	 *
	 * @param oInstruction the instruction to convert
	 * @param sNewOperator operator representative of the new operation
	 *
	 * @return the new arithmetic instruction corresponding to the new operation type
	 */
	public Instruction ConvertArithmetic(Instruction oInstruction, String sNewOperator) {
		//First get the arithmetic type to know what instruction type to convert to
		eArithmeticType oArithType = InstructionToArithmeticType(oInstruction);
		Instruction oNewInstruction = null; //The converted instruction
		
		//Now, we have the arithmetic type, get the corresponding opcode.
		switch (oArithType) {
			case DOUBLE:	oNewInstruction = findInstructionByName(arDoubleInstructions, OperatorToInstName(sNewOperator));break;
			case FLOAT:		oNewInstruction = findInstructionByName(arFloatInstructions, OperatorToInstName(sNewOperator));break;
			case INT:		oNewInstruction = findInstructionByName(arIntInstructions, OperatorToInstName(sNewOperator));break;
			case LONG:		oNewInstruction = findInstructionByName(arLongInstructions, OperatorToInstName(sNewOperator));break;
		}		
		return oNewInstruction;
	}
	
	/**
	 * convers a branch instruction to that of the new operator
	 */
	public BranchInstruction ConvertBranch(BranchInstruction oInstruction, String sNewOperator){
		eBranchType oBranchType = InstructionToBranchType(oInstruction);
		BranchInstruction oNewInstruction = null;
		
		switch(oBranchType) {
			case INT: oNewInstruction = findBranchInstruction(al_IntBranchInstructions, OperatorToInstName(sNewOperator), oInstruction);break;
			case A: oNewInstruction = findBranchInstruction(al_ABranchInstructions, OperatorToInstName(sNewOperator), oInstruction);break;
			case ZERO: oNewInstruction = findBranchInstruction(al_ZeroNullBranchInstructions, OperatorToInstName(sNewOperator), oInstruction);break;
		}
		return oNewInstruction;
	}
	
	/**
	 * Determines an arithmetic operator's corresponding instruction name.
	 *
	 * @param sOperator the arithmetic operator
	 *
	 * @return the arithmetic operation name of the operator
	 */
	private String OperatorToInstName(String sOperator) {
		if (sOperator.equals("+")) { return "add"; }
		else if (sOperator.equals("-")) { return "sub"; }
		else if (sOperator.equals("*")) { return "mul"; }
		else if (sOperator.equals("/")) { return "div"; }
		else if (sOperator.equals("%")) { return "rem"; }
		else if (sOperator.equals("==")) { return "ne"; }
		else if (sOperator.equals("!=")) { return "eq"; }
		else if (sOperator.equals(">") ) { return "le"; }
		else if (sOperator.equals(">=")) { return "lt"; }
		else if (sOperator.equals("<") ) { return "ge"; }
		else if (sOperator.equals("<=")) { return "gt"; }
		else { return ""; }
	}
	
	/**
	 * Searches for an instruction in the specified array by a subset of the
	 * instruction's name.
	 *
	 * @param arInstructions array of instructions
	 * @param sSubName subset of the instruction name
	 *
	 * @return <code>Instruction</code> if an instruction containing sSubName in the name is found,
	 *         <code>null</code> otherwise
	 */
	private Instruction findInstructionByName(Instruction arInstructions[], String sSubName) {
		for (int i=0; i<arInstructions.length; i++) {
			if (arInstructions[i].getName().toLowerCase().contains(sSubName.toLowerCase())) {
				//If the instruction name contains sSubName, return it
				return arInstructions[i];
			}
		}
		return null;
	}
	
	private BranchInstruction findBranchInstruction(ArrayList<String> al_Instructions, String sSubName, BranchInstruction oInstruction){
		String opCode = "";
		BranchInstruction oBranchInstruction = null;
	  //	PrintStream path = org.apache;
		
		for(int i = 0; i < al_Instructions.size(); i++){
			if(al_Instructions.get(i).toLowerCase().contains(sSubName.toLowerCase())){
				opCode = al_Instructions.get(i);
				oBranchInstruction = InstructionFactory.createBranchInstruction(getOpcodeNumber(opCode), oInstruction.getTarget());
				return oBranchInstruction;
			}
		}
		return null;
	}
	
	private short getOpcodeNumber(String sOpcode){
		short opCode = 0;
		for(int i = 0; i < Constants.OPCODE_NAMES.length; i++){
			if(Constants.OPCODE_NAMES[i].toLowerCase().equals(sOpcode.toLowerCase()) ){
				opCode = (short)i;
			}
		}
		return opCode;
	}
			
	public Instruction replaceInstruction(InstructionHandle ih, String sNewOperator){
		Instruction oInstruction = ih.getInstruction();
		short opCode = oInstruction.getOpcode();

		BranchInstruction oBranchInstruction = InstructionFactory.createBranchInstruction(opCode, ih);
		oInstruction = ConvertBranch(oBranchInstruction, sNewOperator);
		
		return oInstruction;
	}	
	
	/**
	 * Determines the arithmetic type of a specific instruction. (Double, Int, etc.)
	 * isArithmeticInstruction should be called prior to this
	 *
	 * @param oInstruction the instruction to check
	 * @return <code>eArithmeticType</code> if a valid arithmetic type is found,
	 *         <code>null</code> otherwise
	 */
	private eArithmeticType InstructionToArithmeticType(Instruction oInstruction) {
		if (Arrays.asList(arDoubleInstructions).indexOf(oInstruction) != -1) {
			return eArithmeticType.DOUBLE;
		} else if (Arrays.asList(arFloatInstructions).indexOf(oInstruction) != -1) {
			return eArithmeticType.FLOAT;
		} else if (Arrays.asList(arIntInstructions).indexOf(oInstruction) != -1) {
			return eArithmeticType.INT;
		} else if (Arrays.asList(arLongInstructions).indexOf(oInstruction) != -1) {
			return eArithmeticType.LONG;
		} else {
			return null; //should never get here
		}
	}		
	
	private eBranchType InstructionToBranchType(Instruction oInstruction){
		String sInstructionName = oInstruction.getName();
		if(al_IntBranchInstructions.indexOf(sInstructionName) != -1){
			return eBranchType.INT;
		}
		else if (al_ABranchInstructions.indexOf(sInstructionName) != -1){
			return eBranchType.A;
		}
		else if (al_ZeroNullBranchInstructions.indexOf(sInstructionName) != -1){
			return eBranchType.ZERO;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Enumeration for valid arithmetic operation types.
	 */
	private enum eArithmeticType {
		DOUBLE,
		FLOAT,
		INT,
		LONG
	}
	
	/**
	 * Enumeration for valid branch operation types.
	 */
	private enum eBranchType {
		INT,
		A,
		ZERO
	}
}
