package interfaces;

import controls.tree.MutableNode;

/**
 * This interface defines the template for all actions that should occur upon the selection
 * of a tree node in the class tree.
 * 
 * @author teh_code
 * 
 */
public interface IMutableTreeListener {
	
	/**
	 * This will be fired when a new tree node (JavaClass or Method) is selected.
	 * 
	 * @param oSelectedNode the newly selected <code>MutableNode</code>
	 */
	public void mutableNodeSelectionChanged(MutableNode oSelectedNode);

}
