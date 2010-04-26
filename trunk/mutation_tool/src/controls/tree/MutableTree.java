package controls.tree;

import interfaces.IMutableTreeListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import com.sun.xml.internal.ws.api.server.Container;


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
	private DefaultTreeCellRenderer oTreeRenderer;
	private DefaultMutableTreeNode oRootNode;
	private ImageIcon imgClass;// = new ImageIcon(getClass().getResource("../images/ClassTree.png"));
	private ImageIcon imgMethod;// = new ImageIcon(getClass().getResource("../images/MethodTree.png"));
	
	public MutableTree() {
		//super("Mutation tree");
		
		// Create an array list for all of the tree selection listeners.
		alTreeSelectionListeners = new ArrayList<IMutableTreeListener>();
		
		// Clear the list of mutable classes that are represented in this tree
		alMutableClasses = new ArrayList<JavaClass>();

		// Create a new JTree
		//oClassTree = new JTree(oRootNode);

		imgClass = new ImageIcon(getClass().getResource("../../images/ClassTree.png"));
		imgMethod = new ImageIcon(getClass().getResource("../../images/MethodTree.png"));
		
	    oTreeRenderer = new MutableNodeRenderer();
	    oTreeRenderer.setOpenIcon(imgClass);
	    oTreeRenderer.setClosedIcon(imgClass);
	    oTreeRenderer.setLeafIcon(imgClass);
	    oTreeRenderer.setIcon(imgClass);



	    this.setRootVisible(false);
	    this.setShowsRootHandles(false);

	    oRootNode = new DefaultMutableTreeNode("No classes Loaded.");
	
		oMutableModel = new DefaultTreeModel(oRootNode);

		this.setCellRenderer(oTreeRenderer);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
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
		//oMutableModel = new DefaultTreeModel(oTreeRootNode);
		//oMutableRenderer = new DefaultTreeCellRenderer();
		oRootNode.removeAllChildren();
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
			oRootNode.add(mutableClassNode);
		}

				
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
			//alTreeSelectionListeners.get(i).mutableNodeSelectionChanged(oSelectedNode);
		}

	}
	
	public static void main(String[] args) {
		new MutableTree();
	}

}
