package controls;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class MutableTree extends JTree {
	
	private ArrayList<JavaClass> alMutableClasses;
	private DefaultTreeModel oMutableModel;
	
	public MutableTree() {
		oMutableModel = new DefaultTreeModel(null);
	}
	
	public void setMutableObjects(ArrayList<JavaClass> alObjects) {
		this.alMutableClasses = alObjects;
		drawMutableObjects();
	}
	
	private void drawMutableObjects() {
		MutableNode mutableClassNode;
		MutableNode mutableMethodNode;
		Method[] arMethods;
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
		this.setModel(oMutableModel);
	}	
}
