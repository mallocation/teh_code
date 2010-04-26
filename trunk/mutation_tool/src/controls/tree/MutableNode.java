package controls.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

public class MutableNode extends DefaultMutableTreeNode {
	
	private JavaClass oMutableClass;
	private Method oMutableMethod;

	/**
	 * Construct a tree node for a class.
	 * @param oClass The JavaClass to construct a node for.
	 */
	public MutableNode(JavaClass oClass) {
		super(oClass.getClassName());
		this.oMutableClass = oClass;
		this.oMutableMethod = null;
	}
	
	/**
	 * Construct a tree node for a method.
	 * @param oMethod The Method to construct a node for.
	 * @param oClassNode The MutableNode that represents the class in which the method exists.
	 */
	public MutableNode(Method oMethod, MutableNode oClassNode) {
		super(oMethod.getName());
		this.oMutableMethod = oMethod;
		this.oMutableClass = oClassNode.getMutableClass();
		oClassNode.add(this);
	}
	
	public JavaClass getMutableClass() {
		return this.oMutableClass;
	}
	
	public Method getMutableMethod() {
		return this.oMutableMethod;
	}
	
	

}
