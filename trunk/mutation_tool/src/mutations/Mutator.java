package mutations;

import java.util.*;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import interfaces.IMutableObject;;
/**
 * Mutator performs dynamic operator mutations on a specific
 * class package, class file, or method in a class.  Its purpose is to change
 * all instances of some old relational, boolean, or arithmetic operator to a new
 * operator of the proper corresponding kind (i.e., arithmetic-arithmetic). It is used
 * in conjunction with the cInstructionHelper class.
 * 
 * specified at runtime
 *
 * @author teh_code
 * @version 1.0
 *
 */
public class Mutator {
	
	/**
	 * List of usable operators within the program.
	 */
	private static ArrayList<String> _alValidOperators;
	
	/**
	 * List of arithmetic operators.
	 */
	private static ArrayList<String> _alArithmeticOperators;
	
	/**
	 * List of relational operators
	 */
	private static ArrayList<String> _alRelationalOperators;
	
	/**
	 * List of boolean operators
	 */
	private static ArrayList<String> _alBooleanOperators;
	
	/**
	 * Class to be mutated.  Specified at runtime.
	 */
	private static JavaClass oClass;
	
	/**
	 * ClassGen object used to generate the mutated class.
	 */
	private static ClassGen oClassGen;
	
	/**
	 * ConstantPoolGen used in generating the mutated class.
	 */
	private static ConstantPoolGen oConstantPoolGen;
	
	/**
	 * Array of methods within the mutated class.
	 */
	private static Method[] arMethods;
	
	/**
	 * The operator to be mutated.
	 */
	private static String sOldOperator;
	
	/**
	 * The new operator resulting from mutation.
	 */
	private static String sNewOperator;
	
	/**
	 * The instruction helper used for generating mutated instructions.
	 */
	private static cInstructionHelper oInstHelper;
		
	/**
	 * This will create an instance of the Mutator program.
	 *
	 * @param args command line arguments of main method containing class file and operators
	 */
	public Mutator(IMutableObject oMutableObject) {
		//parse the arguments (class file, operators) and create an instruction helper
		parseArguments(oMutableObject);
		oInstHelper = new cInstructionHelper();
	}
	
	/**
	 * This is a readonly property that contains a list of all valid operators that may be modified within a class.
	 * This readonly property provides for an easy way to quickly add/remove operators for use.
	 *
	 * @return <code>ArrayList</code> containing all operators available for modification.
	 */
	private static ArrayList<String> alValidOperators() {
		//if the list is null, initialize it
		if (_alValidOperators == null) {
			_alValidOperators = new ArrayList<String>();
			_alValidOperators.add("+");_alValidOperators.add("-");_alValidOperators.add("*");_alValidOperators.add("/");_alValidOperators.add("%");										//Arithmetic
			_alValidOperators.add("==");_alValidOperators.add("!=");_alValidOperators.add("<");_alValidOperators.add(">");_alValidOperators.add("<=");_alValidOperators.add(">=");		//Relational Operators
			_alValidOperators.add("&&");_alValidOperators.add("||");		//booleans
			
		}
		return _alValidOperators;
	}
	
	/**
	 * This list contains all valid boolean operators that may be mutated in a class.
	 * @return <code>ArrayList</code> containing all boolean operators available for mutations.
	 */
	private static ArrayList<String> alBooleanOperators(){
		if(_alBooleanOperators == null){
			_alBooleanOperators = new ArrayList<String>();
			_alBooleanOperators.add("&&");_alBooleanOperators.add("||");
		}
		return _alBooleanOperators;
	}
	
	/**
	 * This list contains all valid relational operators that may be mutated in a class.
	 * @return <code>ArrayList</code> containing all relational operators available for mutations.
	 */
	private static ArrayList<String> alRelationalOperators(){
		if(_alRelationalOperators == null){
			_alRelationalOperators = new ArrayList<String>();
			_alRelationalOperators.add("==");_alRelationalOperators.add("!=");_alRelationalOperators.add("<=");
			_alRelationalOperators.add(">=");_alRelationalOperators.add("<");_alRelationalOperators.add(">");
		}
		return _alRelationalOperators;
	}
	
	/**
	 * This list contains all valid arithmetic operators that may be mutated in a class.
	 * @return <code>ArrayList</code> containing all arithmetic operators available for mutations.
	 */
	private static ArrayList<String> alArithmeticOperators(){
		if(_alArithmeticOperators == null){
			_alArithmeticOperators = new ArrayList<String>();
			_alArithmeticOperators.add("+");_alArithmeticOperators.add("-");_alArithmeticOperators.add("*");_alArithmeticOperators.add("/");_alArithmeticOperators.add("%");
		}
		return _alArithmeticOperators;
	}
	
	/**
	 * Determines if sOperator is an element in the list alOperatorsList. The list itself
	 * will determine the type of operator you are checking sOperator to be.
	 * @param <code>alOperatorsList</code> The list of valid operators to be checked.
	 * @param <code>sOperator</code> The operator to be tested against the valid operators list.
	 * @return <code> true </code> if the operator is in the valid operators list 'alOperatorsList'
	 * 		   <code> false </code> otherwise
	 */
	private static boolean isValidOperator(ArrayList<String> alOperatorsList, String sOperator){
		if(alOperatorsList.indexOf(sOperator) != -1){
			return true;
		}
		else {
			return false;
		}
	}

	
	/**
	 * Parses the arguments passed from the "main" method.
	 * These arguments should contain the class name, and the operators.
	 *
	 * @param args program execution command line arguments
	 */
	private static void parseArguments(IMutableObject oMutableObject) {
		//Get the class file
	
		oClass = oMutableObject.getMutableClass();
		oClassGen = new ClassGen(oClass);


		//Store the operators, and trim any single quotes (needed to pass *, <, >, etc. as an argument)
		sOldOperator = oMutableObject.getOldOperator();			//.replace("'", "");
		sNewOperator = oMutableObject.getNewOperator();
		//Having sOldOperator equal '*' and sNewOperator equal '>' is an example of type mismatch,
		//or an unacceptable combination of operators.
		boolean operatorTypesMismatch = true;

		if (!isValidOperator(alValidOperators(), sOldOperator) || !isValidOperator(alValidOperators(), sNewOperator)) {
			showError("Invalid operator(s) specified.");
		}
		else if (sOldOperator.equals(sNewOperator)) {
			showError("Old and New operators cannot be the same operator.");
		}
		else if ( isValidOperator(alArithmeticOperators(), sOldOperator) && isValidOperator(alArithmeticOperators(), sNewOperator)) {
			operatorTypesMismatch = false;
		}
		else if ( isValidOperator(alBooleanOperators(), sOldOperator) && isValidOperator(alBooleanOperators(), sNewOperator)){
			operatorTypesMismatch = false;
		}
		else if ( isValidOperator(alRelationalOperators(), sOldOperator) && isValidOperator(alRelationalOperators(), sNewOperator)){
			operatorTypesMismatch = false;
		}
		if (operatorTypesMismatch){
			showError("Operators type mismatch. Refer to manual for possible operator combinations.");
		}

	}
	
	/**
	 * Prints the usage specifications of the program.
	 */
	private static void printUsage() {
		System.out.println("<Usage> java Mutator <class-file> <oldOp> <newOp>");
		System.out.println("<class-file>: without extension");
		System.out.println("<oldOp>: See Manual for Details");
		System.out.println("<newOp>: See Manual for Details");
	}
	
	/**
	 * Shows an error to the user, prints the program usage, and exits.
	 * 
	 * @param sError the error to display to the user
	 */
	private static void showError(String sError) {
		System.out.println(sError);
		printUsage();
		System.exit(1);
	}
	
	/**
	 * Finds the number of mutations in a class (for now, implemented
	 * later for method-level)
	 */
	public static int getMutationCount(IMutableObject oMutableObject) {
		oInstHelper = new cInstructionHelper();
		parseArguments(oMutableObject);
		int nMutations = 0;
		
		oConstantPoolGen = oClassGen.getConstantPool();
		arMethods = oClassGen.getMethods();
		
		for (int i=0; i<arMethods.length; i++) {
			// mutation process
			// 1. Go through all instructions in a method
			// 2. If an instruction is an arithmetic instruction, see if it matches the old operator
			// 3. If it matches the old operator, change the instruction to match the new operator, and set the isntruction
			// 4. otherwise, don't worry about it and move on!
			
			MethodGen oMethodGen = new MethodGen(arMethods[i], oClass.getClassName(), oConstantPoolGen);
			InstructionList il = oMethodGen.getInstructionList();
			InstructionHandle ih;
			for (ih = il.getStart(); ih != null; ih = ih.getNext()) {
				Instruction oInstruction = ih.getInstruction();
				if (oInstHelper.isArithmeticInstruction(oInstruction) || oInstHelper.isBranchInstruction(oInstruction)) {
					if (oInstHelper.InstructionMatchOperator(oInstruction, sOldOperator)) {
						//convert to the new instruction			
						nMutations++;
					}					
				}
			}
			il.dispose();
		}		
		return nMutations;
	}	
	
	/**
	 * Changes all instances of the old operator to the new operator
	 * within a specific class file.
	 */
	public static void performMutation(IMutableObject oMutableObject) {
		int nMutations = 0;
		parseArguments(oMutableObject);
		oInstHelper = new cInstructionHelper();
		System.out.println("Changing all instances of \"" + sOldOperator + "\" to \"" + sNewOperator + "\" in " + oClass.getClassName());
		
		oConstantPoolGen = oClassGen.getConstantPool();
		arMethods = oClassGen.getMethods();
		for (int i=0; i<arMethods.length; i++) {
			// mutation process
			// 1. Go through all instructions in a method
			// 2. If an instruction is an arithmetic instruction, see if it matches the old operator
			// 3. If it matches the old operator, change the instruction to match the new operator, and set the isntruction
			// 4. otherwise, don't worry about it and move on!
			
			MethodGen oMethodGen = new MethodGen(arMethods[i], oClass.getClassName(), oConstantPoolGen);
			InstructionList il = oMethodGen.getInstructionList();
			InstructionHandle ih;

			for (ih = il.getStart(); ih != null; ih = ih.getNext()) {
				Instruction oInstruction = ih.getInstruction();
				
				if (oInstHelper.isArithmeticInstruction(oInstruction)) {
					if (oInstHelper.InstructionMatchOperator(oInstruction, sOldOperator)) {
						//convert to the new instruction
						oInstruction = oInstHelper.ConvertArithmetic(oInstruction, sNewOperator);
						ih.setInstruction(oInstruction);					
						nMutations++;
					}					
				}
				else if(oInstHelper.isBranchInstruction(oInstruction)){
					BranchInstruction oBranchInstruction = (BranchInstruction) oInstruction;
					if (oInstHelper.InstructionMatchOperator(oInstruction, sOldOperator)) {
						oBranchInstruction = oInstHelper.ConvertBranch(oBranchInstruction, sNewOperator);
						ih.setInstruction(oBranchInstruction);
						nMutations++;
					}
				}

			}
			il.setPositions();
			oMethodGen.setInstructionList(il);
			oClassGen.setMethodAt(oMethodGen.getMethod(), i);
			il.dispose();
		}		
		System.out.println("Mutation complete. " + nMutations + " mutation(s) were performed.");	
		dumpClass();
	}	
	
	/**
	 * Dumps the modified class to the program directory.
	 */
	public static void dumpClass() {
		try {
			oClassGen.getJavaClass().dump("/Users/Pavel/Documents/workspace/mutation_tool/src/mutations/" + oClass.getClassName() + ".class");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}