package controls;

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
public class MutableTree extends JPanel implements TreeSelectionListener {
	private JTree oClassTree;
	private ArrayList<JavaClass> alMutableClasses;
	private ArrayList<IMutableTreeListener> alTreeSelectionListeners;
	private DefaultTreeModel oMutableModel;
	private DefaultTreeCellRenderer oMutableRenderer;
	private DefaultMutableTreeNode oRootNode;
	
	public MutableTree() {
		//super("Mutation tree");
		
		//oRootNode = new DefaultMutableTreeNode("Root Node");
		//oRootNode.add(new DefaultMutableTreeNode("Child 1"));
		//oRootNode.add(new DefaultMutableTreeNode("Child 2"));
		//oClassTree = new JTree(oRootNode);
		//this.getContentPane().add(oClassTree);
		setSize(275,300);
		setVisible(true);
		
		Object[] hierarchy =
	      { "javax.swing",
	        "javax.swing.border",
	        "javax.swing.colorchooser",
	        "javax.swing.event",
	        "javax.swing.filechooser",
	        new Object[] { "javax.swing.plaf",
	                       "javax.swing.plaf.basic",
	                       "javax.swing.plaf.metal",
	                       "javax.swing.plaf.multi" },
	        "javax.swing.table",
	        new Object[] { "javax.swing.text",
	                       new Object[] { "javax.swing.text.html",
	                                      "javax.swing.text.html.parser" },
	                       "javax.swing.text.rtf" },
	        "javax.swing.tree",
	        "javax.swing.undo" };
	    DefaultMutableTreeNode root = processHierarchy(hierarchy);
	    JTree tree = new JTree(root);
	    DefaultTreeCellRenderer test = new DefaultTreeCellRenderer();
		ImageIcon imgClass = new ImageIcon(getClass().getResource("../images/ClassTree.png"));
		ImageIcon imgMethod = new ImageIcon(getClass().getResource("../images/MethodTree.png"));	
	    test.setOpenIcon(imgMethod);
	    test.setClosedIcon(imgClass);
	    tree.setCellRenderer(test);
	    this.add(new JScrollPane(tree), BorderLayout.CENTER);
	    setSize(275, 300);
	    setVisible(true);
	    //this.oClassTree.setRootVisible(false);
	    //this.oClassTree.setShowsRootHandles(true);
	}

	  /** Small routine that will make node out of the first entry
	   *  in the array, then make nodes out of subsequent entries
	   *  and make them child nodes of the first one. The process is
	   *  repeated recursively for entries that are arrays.
	   */
	    
	private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
		DefaultMutableTreeNode child;
		for(int i=1; i<hierarchy.length; i++) {
			Object nodeSpecifier = hierarchy[i];
			if (nodeSpecifier instanceof Object[])  // Ie node with children
				child = processHierarchy((Object[])nodeSpecifier);
			else
				child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
			node.add(child);
		}
		return(node);
	}
	
		//oMutableModel = new DefaultTreeModel(null);
		//oMutableRenderer = new DefaultTreeCellRenderer();
		//alTreeSelectionListeners = new ArrayList<IMutableTreeListener>();
		//this.alMutableClasses = new ArrayList<JavaClass>();
		//this.oTreeRootNode = new DefaultMutableTreeNode("No classes loaded.");
		//this.addTreeSelectionListener(this);
		//drawMutableObjects();
	//}
	
//	public void addMutableClassToTree(JavaClass oJavaClass) {
//		JavaClass oClassToAdd = oJavaClass.copy();
//		this.alMutableClasses.add(oClassToAdd);
//		sortMutableClasses();
//		drawMutableObjects();
//	}
	public void addMutableClassToTree(JavaClass oJavaClass) {
//		JavaClass oClassToAdd = oJavaClass.copy();
//		this.alMutableClasses.add(oClassToAdd);
//		sortMutableClasses();
//		drawMutableObjects();
	}

	private void sortMutableClasses() {
		Collections.sort(alMutableClasses, new JavaClassSort());
	}
	
	private void clearTree() {
		//oMutableModel = new DefaultTreeModel(oTreeRootNode);
		oMutableRenderer = new DefaultTreeCellRenderer();
		//oTreeRootNode.removeAllChildren();
	}
	
	private void drawMutableObjects() {
		MutableNode mutableClassNode;
		Method[] arMethods;
		clearTree();
		
//		if (alMutableClasses.size() == 0)
//			this.setRootVisible(true);
//		else
//			this.setRootVisible(false);		
		
		for (int i=0; i<alMutableClasses.size(); i++) {
			mutableClassNode = new MutableNode(alMutableClasses.get(i));
			arMethods = mutableClassNode.getMutableClass().getMethods();
			for (int j=0; j < arMethods.length; j++) {
				new MutableNode(arMethods[j], mutableClassNode);
			}
//			oTreeRootNode.add(mutableClassNode);
		}

//		this.oMutableRenderer.setOpenIcon(imgMethod);
//		this.oMutableRenderer.setClosedIcon(imgMethod);
//		this.oMutableRenderer.setLeafIcon(imgMethod);
//		this.setCellRenderer(oMutableRenderer);
//		this.setModel(oMutableModel);
//		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
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
		//MutableNode oSelectedNode = (MutableNode)this.getLastSelectedPathComponent();

		// go through each listener, and fire it off, sending the newly selected node
		for (int i=0; i<alTreeSelectionListeners.size(); i++) {
			//alTreeSelectionListeners.get(i).mutableNodeSelectionChanged(oSelectedNode);
		}

	}
	
	public static void main(String[] args) {
		new MutableTree();
	}

}
