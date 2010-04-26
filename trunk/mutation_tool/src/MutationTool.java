/**
 * MutationTool is the main class and GUI for the mutation tool
 * 	designed for csce 361
 * 
 * CSCE 361
 * 
 * @author teh_code
 * @version 1.0
 */

//------------------------------------------
// Import Statements
//------------------------------------------

import interfaces.IMutableTreeListener;

import java.io.*;
import java.util.*;
import javax.swing.*;

import utilities.ClassLoader;

import controls.tree.MutableTree;
import controls.table.MutationTable;
import controls.table.MutationTableFilter;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class MutationTool extends JFrame implements ActionListener
{
	//------------------------------------------
	// Variables
	//------------------------------------------
	
	
	
	//------------------------------------------
	// GUI declarations
	//------------------------------------------
	MutableTree oMutableTree;
	MutationTable oMutationTable;
	IMutableTreeListener oTreeListener = new MutableTreeListenerExample();
	
	public MutationTool(){
		createMenu();
		
		createFrames();
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.pack();
		this.setSize(1062,800);
		this.setVisible(true);
	}
	
	
	public void createMenu(){
		JPopupMenu.setDefaultLightWeightPopupEnabled(false); // Prevents the menu bar from being hidden
	
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem fileMenuOpen = new JMenuItem("Open");
		JMenuItem fileMenuExit = new JMenuItem("Exit");
		//subMenu.setMnemonic(KeyEvent.VK_S);

		//JMenuItem menuItem = new JMenuItem("SubMenu Item");
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		//subMenu.add(menuItem);

		//JMenuItem menuItem2 = new JMenuItem("SubMenu Item 2");
		//subMenu.add(menuItem2);

		//add action listeners
		fileMenuOpen.addActionListener(this);
		fileMenuExit.addActionListener(this);
		
		//add items to menu and menu bar
		fileMenu.add(fileMenuOpen);
		fileMenu.add(fileMenuExit);
		menuBar.add(fileMenu);

		this.setJMenuBar(menuBar);
	}
	
	public void createFrames(){
		oMutableTree = new MutableTree();
		oMutableTree.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		oMutableTree.setPreferredSize(new Dimension(250,800));
		oMutableTree.addMutableNodeSelectionListener(oTreeListener);
//		JPanel panel = new JPanel();
//		panel.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
//		panel.add(new Label("Jar, Class, Method Select Panel"));
//		panel.setPreferredSize(new Dimension(250,800));
		
		MutationTableFilter oTableFilter = new MutationTableFilter();
		oMutationTable = new MutationTable();
		//oMutationTable.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		//oMutationTable.setPreferredSize(new Dimension(400, 800));
		oTableFilter.addMutationFilterListener(oMutationTable);
		JPanel panel2 = new JPanel();
		//panel2.add(new Label("Mutation Table Panel"));
		//panel2.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		//panel2.setPreferredSize(new Dimension(500,800));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel2.add(oTableFilter);
		JScrollPane oTablePane = new JScrollPane(oMutationTable);
		oTablePane.setBorder(BorderFactory.createTitledBorder("Available Mutations"));
		oTablePane.getVerticalScrollBar().setUnitIncrement(10);
		panel2.add(oTablePane);
		oMutableTree.addMutableNodeSelectionListener(oMutationTable);
		
		JPanel panel3 = new JPanel();
		panel3.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		panel3.add(new Label("Selected Mutations Panel"));
		panel3.setPreferredSize(new Dimension(250,800));
				
		BorderLayout borderLayout = new BorderLayout();
		
		this.getContentPane().setLayout(borderLayout);	

		this.getContentPane().add(oMutableTree, BorderLayout.WEST);
		this.getContentPane().add(panel2, BorderLayout.CENTER);
//		JScrollPane oTableScrollPane = new JScrollPane(oMutationTable);
//		oTableScrollPane.getVerticalScrollBar().setUnitIncrement(100);
		//this.getContentPane().add(oTableScrollPane, BorderLayout.CENTER);
		
		this.getContentPane().add(panel3, BorderLayout.EAST);
	}
	
	public void actionPerformed(ActionEvent e){
		// Make sure the event was done through the menu
		if (e.getSource() instanceof JMenuItem)
		{
			// Set the string the event is handling
			String eventName = ((JMenuItem) e.getSource()).getText();
			// User Clicked Open
            if (eventName.equals("Open")) {            
            	// Open the Open Dialog to allow user to select file
            	String current = System.getProperty("user.dir");
            	JFileChooser chooser = new JFileChooser(current);
            	int option = chooser.showOpenDialog(null);
            	File oClassToOpen; 
            	// If user chose a file, open it
            	if (option == JFileChooser.APPROVE_OPTION) {
            		oClassToOpen = chooser.getSelectedFile();
            		// If there is a problem with the input file, tell the user
            		if (!ClassLoader.isClassFile(oClassToOpen.getAbsolutePath())) {//if (oClassToOpen.canRead() == false || oClassToOpen.exists() == false || !ClassLoader.isClassFile(oClassToOpen.getAbsolutePath())) {
            			JOptionPane.showMessageDialog(null, "File did not load successfully. Either the file\ndoes not exist, or it is not a valid class file.");
            			oClassToOpen = null;
            		} else {            			
            			oMutableTree.addMutableClassToTree(ClassLoader.loadClassFromPath(oClassToOpen.getAbsolutePath()));
            		}
            	}
            }
			
			if (eventName.equals("Exit"))
			{
				// The user wants to exit (quit)
				System.exit(0);
			}
		}
	}
	
	public static void main(String[] args){
		new MutationTool();
	}
}