package controls;

import interfaces.IMutableTreeListener;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import utilities.JavaClassSort;

/**
 * MutableTree is used in the Graphical User Interface portion of the mutation tool.
 * This tree will display classes in a tree format, as well as their individual methods.
 * 
 * @author teh_code
 *
 */
// TODO Edit this class, so when adding a new class and such, things are displayed in order in the tree.
public class MutableTree extends JTree implements TreeSelectionListener {
	
	private ArrayList<JavaClass> alMutableClasses;
	private ArrayList<IMutableTreeListener> alTreeSelectionListeners;
	private DefaultTreeModel oMutableModel;
	private DefaultTreeCellRenderer oMutableRenderer;
	private DefaultMutableTreeNode oTreeRootNode;
	
	public MutableTree() {
		oMutableModel = new DefaultTreeModel(null);
		oMutableRenderer = new DefaultTreeCellRenderer();
		alTreeSelectionListeners = new ArrayList<IMutableTreeListener>();
		this.alMutableClasses = new ArrayList<JavaClass>();
		this.oTreeRootNode = new DefaultMutableTreeNode("No classes loaded.");
		this.addTreeSelectionListener(this);
		drawMutableObjects();
	}
	
	public void addMutableClassToTree(JavaClass oJavaClass) {
		JavaClass oClassToAdd = oJavaClass.copy();
		this.alMutableClasses.add(oClassToAdd);
		sortMutableClasses();
		drawMutableObjects();
	}
	
	private void sortMutableClasses() {
		Collections.sort(alMutableClasses, new JavaClassSort());
	}
	
	private void clearTree() {
		oMutableModel = new DefaultTreeModel(oTreeRootNode);
		oMutableRenderer = new DefaultTreeCellRenderer();
		oTreeRootNode.removeAllChildren();
	}
	
	private void drawMutableObjects() {
		MutableNode mutableClassNode;
		Method[] arMethods;
		clearTree();
		
		if (alMutableClasses.size() == 0)
			this.setRootVisible(true);
		else
			this.setRootVisible(false);		
		
		for (int i=0; i<alMutableClasses.size(); i++) {
			mutableClassNode = new MutableNode(alMutableClasses.get(i));
			arMethods = mutableClassNode.getMutableClass().getMethods();
			for (int j=0; j < arMethods.length; j++) {
				new MutableNode(arMethods[j], mutableClassNode);
			}
			oTreeRootNode.add(mutableClassNode);
		}

//		this.oMutableRenderer.setOpenIcon(imgMethod);
//		this.oMutableRenderer.setClosedIcon(imgMethod);
//		this.oMutableRenderer.setLeafIcon(imgMethod);
		this.setCellRenderer(oMutableRenderer);
		this.setModel(oMutableModel);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}
	
	/**
	 * Add a MutableNodeSelectionListener to the tree, which will be called when a new node is selected.
	 * 
	 * @param oNewListener Listener to register
	 */
	public void addMutableNodeSelectionListener(IMutableTreeListener oNewListener) {
		alTreeSelectionListeners.add(oNewListener); //Add to the list
	}

//	@Override
	/**
	 * This method is called when a new tree node is selected.
	 */
	public void valueChanged(TreeSelectionEvent e) {
		MutableNode oSelectedNode = (MutableNode)this.getLastSelectedPathComponent();

		// go through each listener, and fire it off, sending the newly selected node
		for (int i=0; i<alTreeSelectionListeners.size(); i++) {
			alTreeSelectionListeners.get(i).mutableNodeSelectionChanged(oSelectedNode);
		}

	}

}
