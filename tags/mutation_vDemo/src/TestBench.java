import interfaces.IMutableObject;
import interfaces.IMutableObject.eMutantLevel;
import interfaces.IMutableObject.eMutantType;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import controls.MutableTree;
import controls.table.MutationTable;

import mutations.Mutant;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;

import org.apache.bcel.classfile.*;

import utilities.ClassLoader;


public class TestBench extends JFrame {
	//private MutableTree oMutableTree;
	
	//private static final String sClass = "C:\\Users\\cjohnson\\Documents\\Class Documents\\Spring 10\\CSE430\\Project\\cachemoney\\trunk\\assembler\\CacheMoney\\bin\\Utilities\\Assembler.class";
	//private static final String sClass2 = "C:\\Users\\cjohnson\\Documents\\Class Documents\\Spring 10\\CSE430\\Project\\cachemoney\\trunk\\assembler\\CacheMoney\\bin\\Utilities\\AssemblyParser.class";
	
	public TestBench() {
	    super("Mutable Tree");
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    Container content = getContentPane();
	    MutationTable oTable = new MutationTable();
	    //this.add(oTable);
	    JScrollPane oScroll = new JScrollPane(oTable);
	    this.add(oScroll);
	    
//	    DefaultListModel oListModel = new DefaultListModel();
//	    JList oMutationTable = new JList(oListModel);
//	    oMutationTable.setCellRenderer(new MutationRenderer());
//	    
//	    IMutableObject oMutant1 = new Mutant();
//	    oMutant1.setMutantLevel(eMutantLevel.CLASS);
//	    oMutant1.setMutantType(eMutantType.ARITHMETIC);
//	    oListModel.addElement(oMutant1);
//	    
//	    content.add(oMutationTable);
	    
	    
	    
	    
	    
	    
	    //content.add(new JScrollPane(oMutableTree), BorderLayout.CENTER);
//	    setSize(600, 300);
	    setVisible(true);
	}
	

	public static void main(String[] args) {
		new TestBench();
	}

}