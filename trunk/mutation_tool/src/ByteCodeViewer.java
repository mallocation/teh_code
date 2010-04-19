import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utilities.ByteViewer;


public class ByteCodeViewer extends JFrame{
	
	File inputFile;
	
	public ByteCodeViewer()
	{
		
		
		//-----------------------------------------------
		//create GUI
		//-----------------------------------------------
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Container content = getContentPane();
		
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
	    JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose a .asm file");
		
		int status = fileChooser.showOpenDialog(null);
	    
	    if(status == JFileChooser.APPROVE_OPTION)
	    {
	    	inputFile = fileChooser.getSelectedFile();
	    }
	    
	    ByteViewer oByteCode = new ByteViewer();
	    oByteCode.generateByteCode(inputFile.getAbsolutePath());
	    
	    
	    
	    this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ByteCodeViewer();
	}
}
