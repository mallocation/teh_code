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
		
		JMenu menu = new JMenu("File");
		JMenu subMenu = new JMenu("Open");
		subMenu.setMnemonic(KeyEvent.VK_S);

		JMenuItem menuItem = new JMenuItem("SubMenu Item");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		subMenu.add(menuItem);

		JMenuItem menuItem2 = new JMenuItem("SubMenu Item 2");
		subMenu.add(menuItem2);

		menu.add(subMenu);
		menuBar.add(menu);

		this.setJMenuBar(menuBar);
	}
	
	public void createFrames(){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		panel.add(new Label("Welcome!"));
		panel.setPreferredSize(new Dimension(250,800));
		
		JPanel panel2 = new JPanel();
		panel2.add(new Label("Bienvenido!"));
		panel2.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		panel2.setPreferredSize(new Dimension(500,800));
		
		JPanel panel3 = new JPanel();
		panel3.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		panel3.add(new Label("Bonjour!"));
		panel3.setPreferredSize(new Dimension(250,800));
				
		GridLayout gridLayout = new GridLayout(0,3);
		
		this.getContentPane().setLayout(gridLayout);	
		this.getContentPane().add(panel);
		this.getContentPane().add(panel2);
		this.getContentPane().add(panel3);
	}
	
	public void actionPerformed(ActionEvent e){
	
	}
	
	public static void main(String[] args){
		MutationTool mTool = new MutationTool();
	}
	//
	
}