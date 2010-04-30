package controls.tree;

import interfaces.IMutableTreeListener;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import utilities.ClassLoader;

/**
 * MutableTree is used in the Graphical User Interface portion of the mutation tool.
 * This tree will display classes in a tree format, as well as their individual methods.
 * 
 * @author teh_code
 *
 */
// TODO Edit this class, so when adding a new class and such, things are displayed in order in the tree.
public class MutableTree extends JTree implements TreeSelectionListener {
	
	/**
	 * List of mutable classes that are shown in the tree
	 */
	private ArrayList<JavaClass> alMutableClasses;
	
	/**
	 * List of IMutableTreeListeners that are fired when a new node is selected.
	 */
	private ArrayList<IMutableTreeListener> alTreeSelectionListeners;
	
	/**
	 * Tree model used in the tree.
	 */
	private DefaultTreeModel oTreeModel;
	
	/**
	 * Tree cell renderer.
	 */
	private MutableNodeRenderer oTreeRenderer;
	
	/**
	 * Root node of the tree.  This should show 'No classes found.'
	 */
	private MutableNode oRootNode;
	
	/**
	 * Constructs a new mutable tree with no classes loaded.
	 */
	public MutableTree() {
		this.setModel(null);

		// Create the root node, stating 'No classes loaded.'
		this.oRootNode = new MutableNode("No classes loaded.");
	    // Create the tree model with the root node
		this.oTreeModel = new DefaultTreeModel(oRootNode);
		
		
		// Clear the list of mutable classes that are represented in this tree
		alMutableClasses = new ArrayList<JavaClass>();
		
		// Create an array list for all of the tree selection listeners.
		alTreeSelectionListeners = new ArrayList<IMutableTreeListener>();

		// Create the tree cell renderer.
	    oTreeRenderer = new MutableNodeRenderer();
	    
		// Set the cell renderer
		this.setCellRenderer(oTreeRenderer);
		
		// Set the tree model with the model containing the root node
		this.setModel(oTreeModel);

	    // No need to show the handle for roots
	    this.setShowsRootHandles(true);
		
		// Only one selection at a time!
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		// This class will be a selection listener for itself, firing off all other IMutableTreeListeners
		this.addTreeSelectionListener(this);
	}
	
	/**
	 * Add a class to the tree
	 * 
	 * @param oJavaClass class to add to tree
	 */
	public void addMutableClassToTree(JavaClass oJavaClass) {
		JavaClass oClassToAdd = oJavaClass.copy();
		this.alMutableClasses.add(oClassToAdd);
		MutableNode oNewClassNode = new MutableNode(oClassToAdd);
		for (int i=0; i<oClassToAdd.getMethods().length; i++) {
			new MutableNode(oClassToAdd.getMethods()[i], oNewClassNode);			
		}
		oTreeModel.insertNodeInto(oNewClassNode, this.oRootNode, this.oRootNode.getChildCount());
		oTreeModel.reload();
		this.setRootVisible(false);
	}
	
	/**
	 * Clear all nodes in the tree, leaving the root node intact.
	 */
	private void clearTree() {
		//oRootNode.removeAllChildren();
	}
	
	/**
	 * Draw all of the registered classes into the tree.
	 */
	private void drawMutableObjects() {
		MutableNode mutableClassNode;
		Method[] arMethods;
		
		// Clear the tree
		clearTree();

		// Traverse the list of mutable classes to show, and add them to the tree.
		for (int i=0; i<alMutableClasses.size(); i++) {
			mutableClassNode = new MutableNode(alMutableClasses.get(i));
			arMethods = mutableClassNode.getMutableClass().getMethods();
			for (int j=0; j < arMethods.length; j++) {
				new MutableNode(arMethods[j], mutableClassNode);
			}
		}
		this.repaint();
	}
	
	/**
	 * Add a MutableNodeSelectionListener to the tree, which will be called when a new node is selected.
	 * 
	 * @param oNewListener Listener to register
	 */
	public void addMutableNodeSelectionListener(IMutableTreeListener oNewListener) {
		alTreeSelectionListeners.add(oNewListener); //Add to the list
	}

	/**
	 * This method is called when a new tree node is selected.
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		MutableNode oSelectedNode;		
		if (this.getLastSelectedPathComponent() instanceof MutableNode) {
			oSelectedNode = (MutableNode)this.getLastSelectedPathComponent();
			// go through each listener, and fire it off, sending the newly selected node
			for (int i=0; i<alTreeSelectionListeners.size(); i++) {
				alTreeSelectionListeners.get(i).mutableNodeSelectionChanged(oSelectedNode);
			}
		}
	}
}
