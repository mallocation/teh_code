package controls;

import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class MutableTree extends JTree implements TreeSelectionListener {
	
	private ArrayList<JavaClass> alMutableClasses;
	private DefaultTreeModel oMutableModel;
	private DefaultTreeCellRenderer oMutableRenderer;
	
	public MutableTree() {
		oMutableModel = new DefaultTreeModel(null);
		oMutableRenderer = new DefaultTreeCellRenderer();
		this.alMutableClasses = new ArrayList<JavaClass>();
	}
	
	public void addMutableClass(JavaClass oJavaClass) {
		JavaClass oClassToAdd = oJavaClass.copy();
		this.alMutableClasses.add(oClassToAdd);
		drawMutableObjects();
	}
	/*
	public void setMutableObjects(ArrayList<JavaClass> alObjects) {
		this.alMutableClasses = alObjects;
		drawMutableObjects();
	}*/
	
	private void clearTree() {
		oMutableModel = new DefaultTreeModel(null);
		oMutableRenderer = new DefaultTreeCellRenderer();
	}
	
	private void drawMutableObjects() {
		MutableNode mutableClassNode;
		Method[] arMethods;
		clearTree();
		
		for (int i=0; i<alMutableClasses.size(); i++) {
			mutableClassNode = new MutableNode(alMutableClasses.get(i));
			arMethods = mutableClassNode.getMutableClass().getMethods();
			for (int j=0; j < arMethods.length; j++) {
				new MutableNode(arMethods[j], mutableClassNode);
			}
			//TODO Remove this, and figure out a way to add multiple classes to a model...or will multiple classes
			//	exist only when a package is opened?
			oMutableModel = new DefaultTreeModel(mutableClassNode);
		}

//		this.oMutableRenderer.setOpenIcon(imgMethod);
//		this.oMutableRenderer.setClosedIcon(imgMethod);
//		this.oMutableRenderer.setLeafIcon(imgMethod);
		this.setCellRenderer(oMutableRenderer);
		this.setModel(oMutableModel);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		this.addTreeSelectionListener(this);
		
	}

	@Override
	/**
	 * This method is called when a new tree node is selected.
	 */
	public void valueChanged(TreeSelectionEvent e) {
		MutableNode oSelectedNode = (MutableNode)this.getLastSelectedPathComponent();		
		if (oSelectedNode.getMutableMethod() == null) {
			System.out.println("Selected: " + oSelectedNode.getMutableClass().getClassName());
		} else {
			System.out.println("Selected: " + oSelectedNode.getMutableClass().getClassName() + "." + oSelectedNode.getMutableMethod().getName());
		}		
	}

}
