package teh_code.interfaces;

/**
 * This interface is the template for all operations that should perform when a mutant from 
 * the mutation table is either selected or unselected.
 * 
 * @author teh_code
 *
 */
public interface IMutationRowListener {
	
	/**
	 * This will be called if a mutant (IMutableObject) is selected in the table.
	 * 
	 * @param oMutant selected mutation 
	 */
	public void mutableObjectSelected(IMutableObject oMutant);
	
	/**
	 * This will be called if a mutant (IMutableObject) is unselected in the table.
	 * 
	 * @param oMutant unselected mutation
	 */
	public void mutableObjectUnSelected(IMutableObject oMutant);

}
