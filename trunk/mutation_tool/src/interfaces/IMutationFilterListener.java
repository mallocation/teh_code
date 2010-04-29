package interfaces;

/**
 * This interface defines the template for actions that will be performed from the 
 * MutationFilter panel.  This should be implemented by MutationTable.
 * 
 * @author teh_code
 */
public interface IMutationFilterListener {
	
	/**
	 * Fired when an action occurs with the 'Select All' checkbox.
	 * 
	 * @param bSelectAll <code>true</code> if the checkbox is checked
	 * 					 <code>false</code> if the checkbox is unchecked
	 */
	public void selectAllVisible(boolean bSelectAll);
	
	/**
	 * This is fired when a change occurs in the filter specifications for a mutation.
	 * 
	 * @param mutantSearch mutant to search for (class name, method name)
	 * @param mutantType type of mutation i.e. Arithmetic
	 * @param oldOp	old operator
	 * @param newOp new operator
	 */
	public void filterMutations(String mutantSearch, String mutantType, String oldOp, String newOp);

}
