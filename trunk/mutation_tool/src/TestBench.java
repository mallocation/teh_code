import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import controls.MutableTree;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;


public class TestBench extends JFrame {
	private MutableTree oMutableTree;
	
	public TestBench() {
	    super("Mutable Tree");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    Container content = getContentPane();
	    oMutableTree = new MutableTree();
	    
	    
	    content.add(new JScrollPane(oMutableTree), BorderLayout.CENTER);
	    setSize(275, 300);
	    setVisible(true);
	    showClass();
	}
	
	private void showClass() {
		//String sClass = "C:\\Users\\cjohnson\\Documents\\Class Documents\\Spring 10\\CSE361\\Assignment1\\cInstructionHelper";
		String sClass = "MutationTool";
		try {
			ArrayList<JavaClass> alClasses = new ArrayList<JavaClass>();
			alClasses.add(Repository.lookupClass(sClass));			
			oMutableTree.setMutableObjects(alClasses);			
		} catch (Exception e) {
			//showError("Please specify a valid class file.");
		}
	}
	
	public static void main(String[] args) {
		new TestBench();
	}

}