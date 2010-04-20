import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import utilities.ByteViewer;
import org.apache.bcel.classfile.*;

/**
 * This class displays the bytecode differences between two classes.  The two arguments for the
 * constructor are the file paths of the two .class files.
 * @author teh_code
 *
 */

public class ByteCodeViewer extends JFrame{
	
	public String originalClassFile;
	public String mutatedClasssFile;
	
	public ByteCodeViewer(String file1, String file2)
	{
		
		this.originalClassFile = file1;
		this.mutatedClasssFile = file2;
		
		//-----------------------------------------------
		//create GUI
		//-----------------------------------------------
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		this.setSize(800, 600);
		this.setTitle     ("Byte Code Differences Viewer");
        this.setLocation  (100, 100);
        this.setResizable(false);
	    
	    
	    JPanel originalClassPanel = new JPanel();
	    originalClassPanel.add(new Label("Original Class File"));
	    originalClassPanel.setPreferredSize(new Dimension(400,600));
	    originalClassPanel.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
	    
	    JPanel mutatedClassPanel = new JPanel();
	    mutatedClassPanel.add(new Label("Mutated Class File"));
	    mutatedClassPanel.setPreferredSize(new Dimension(400,600));
	    mutatedClassPanel.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
	    
	    this.getContentPane().setLayout(new BorderLayout());
	    this.getContentPane().add(originalClassPanel, BorderLayout.WEST);
	    this.getContentPane().add(mutatedClassPanel, BorderLayout.EAST);
	    
	    
	    //-----------------------------------------------
	    // generate and display bytecode
	    //-----------------------------------------------
	    
	    // original class file bytecode
	    ByteViewer oOriginalByteCode = new ByteViewer();
	    oOriginalByteCode.generateByteCode(originalClassFile);	    
	    
	    //place bytecode in appropriate text area
	    JTextArea originalClassTextArea = new JTextArea(32,34);
	    originalClassTextArea.setText(oOriginalByteCode.oClass.toString());
	    for(int i=0; i < oOriginalByteCode.oClassMethods.length; i++) {
	    	Code code = oOriginalByteCode.oClassMethods[i].getCode();
  	  		if(code != null)
  	  			originalClassTextArea.append(code.toString());
  	  	}
	    originalClassTextArea.setEditable(false);
	    JScrollPane originalClassScrollPane = new JScrollPane(originalClassTextArea);
	    originalClassPanel.add(originalClassScrollPane);
	    
	    
	    
	    // modified class file bytecode
	    ByteViewer oModifiedByteCode = new ByteViewer();
	    oModifiedByteCode.generateByteCode(mutatedClasssFile);
	    
	    JTextArea modifiedClassTextArea = new JTextArea(32,33);
	    modifiedClassTextArea.setText(oModifiedByteCode.oClass.toString());
	    for(int i=0; i < oModifiedByteCode.oClassMethods.length; i++) {
	    	Code code = oModifiedByteCode.oClassMethods[i].getCode();
  	  		if(code != null)
  	  			modifiedClassTextArea.append(code.toString());
  	  	}
	    modifiedClassTextArea.setEditable(false);
	    JScrollPane modifiedClassScrollPane = new JScrollPane(modifiedClassTextArea);
	    mutatedClassPanel.add(modifiedClassScrollPane);
	    
	    
	    this.setVisible(true);
	}
}
