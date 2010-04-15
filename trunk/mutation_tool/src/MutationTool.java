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

import java.io.*;
import java.util.*;
import javax.swing.*;

import controls.MutableTree;

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
	
	public MutationTool(){
		createMenu();
		
		createFrames();
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.pack();
		this.setSize(1000,800);
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
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		panel.add(new Label("Jar, Class, Method Select Panel"));
		panel.setPreferredSize(new Dimension(250,800));
		
		JPanel panel2 = new JPanel();
		panel2.add(new Label("Mutation Table Panel"));
		panel2.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		panel2.setPreferredSize(new Dimension(500,800));
		
		JPanel panel3 = new JPanel();
		panel3.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		panel3.add(new Label("Selected Mutations Panel"));
		panel3.setPreferredSize(new Dimension(250,800));
				
		BorderLayout borderLayout = new BorderLayout();
		
		this.getContentPane().setLayout(borderLayout);	
		this.getContentPane().add(panel, BorderLayout.WEST);
		this.getContentPane().add(panel2, BorderLayout.CENTER);
		this.getContentPane().add(panel3, BorderLayout.EAST);
	}
	
	public void actionPerformed(ActionEvent e){
		// Make sure the event was done through the menu
		if (e.getSource() instanceof JMenuItem)
		{
			// Set the string the event is handling
			String eventName = ((JMenuItem) e.getSource()).getText();
			if (eventName.equals("Open"))
			{
				//TODO - implement
			}
			
			
			if (eventName.equals("Exit"))
			{
				// The user wants to exit (quit)
				System.exit(0);
			}
		}
	}
	
	public static void main(String[] args){
		MutationTool mTool = new MutationTool();
	}
	//
	
}