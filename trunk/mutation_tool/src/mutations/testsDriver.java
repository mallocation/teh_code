package mutations;
import mutations.Mutator;
import interfaces.IMutableObject;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import java.io.*;


public class testsDriver{
	
	private void showError(String sError) {
		System.out.println(sError);
		System.exit(1);
	}
	
	public void main(String args[]){
		IMutableObject oMutant = new Mutant();
		JavaClass oClass = null;
		String oldOp = null;
		String newOp = null;
		try{
			oClass = Repository.lookupClass(args[0]);
		}
		catch(Exception e){
			showError("Specify valid class file.");
		}
		try{
			oldOp = args[1].replace("'", "");
			newOp = args[2].replace("'", "");
		}catch (Exception e){
			showError("Specify operators.");
		}
		
		oMutant.setMutableClass(oClass);
		oMutant.setOldOperator(oldOp);
		oMutant.setNewOperator(newOp);
		Mutator oMutator = new Mutator(oMutant);
		System.out.println("" + oMutator.getMutationCount());
		oMutator.performMutation();
		
	}
}
