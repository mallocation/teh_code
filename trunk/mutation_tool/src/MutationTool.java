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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import interfaces.IMutableTreeListener;

import mutations.Mutator;

import utilities.ClassLoader;

import controls.mutations.MutationsSelected;
import controls.tree.MutableTree;
import controls.table.MutationTable;
import controls.table.MutationTableFilter;
import controls.table.PropertiesPanel;


/**
 * MutationTool is the main class that is compiled and ran.  It is
 * responsible for both the GUI and command line versions of our
 * mutation tool.
 * 
 * @author teh code
 *
 */
public class MutationTool extends JFrame implements ActionListener
{
	//------------------------------------------
	// Variables
	//------------------------------------------
	
	
	//------------------------------------------
	// GUI declarations
	//------------------------------------------
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem fileMenuOpen;
	JMenuItem fileMenuExit;
	JMenu optionsMenu;
	JCheckBoxMenuItem optionsMenuPersist;

	MutableTree oMutableTree;
	MutationTable oMutationTable;
	PropertiesPanel oPropertiesPanel;
	
	/**
	 * Constructor for MutationTool
	 */
	public MutationTool(){
		this.setTitle("Mutation Tool");
		createMenu();
		
		createFrames();
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.pack();
		this.setSize(1080,700);
		this.setVisible(true);
	}
	
	/**
	 * createMenu creates menu for the frame
	 */
	public void createMenu(){
		JPopupMenu.setDefaultLightWeightPopupEnabled(false); // Prevents the menu bar from being hidden
	
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		fileMenuOpen = new JMenuItem("Open");
		fileMenuExit = new JMenuItem("Exit");
		
		optionsMenu = new JMenu("Options");
		optionsMenuPersist = new JCheckBoxMenuItem("Check For Persistant Mutations", true);

		//add action listeners
		fileMenuOpen.addActionListener(this);
		fileMenuExit.addActionListener(this);
		optionsMenuPersist.addActionListener(this);
		
		//add items to menu and menu bar
		fileMenu.add(fileMenuOpen);
		fileMenu.add(fileMenuExit);
		
		optionsMenu.add(optionsMenuPersist);
		
		menuBar.add(fileMenu);
		menuBar.add(optionsMenu);

		this.setJMenuBar(menuBar);
	}
	
	/**
	 * createFrames creates the frames necessary for our mutation tool
	 */
	public void createFrames(){
		oMutableTree = new MutableTree();		
		oMutableTree.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		
		JScrollPane oScroll = new JScrollPane(oMutableTree);
		oScroll.setPreferredSize(new Dimension(250,500));
		
		oPropertiesPanel = new PropertiesPanel();		
		
		MutationTableFilter oTableFilter = new MutationTableFilter();
		MutationsSelected oMutationsSelected = new MutationsSelected();

		oMutationTable = new MutationTable(oTableFilter, oPropertiesPanel, oMutationsSelected, oMutationsSelected);
		oTableFilter.addMutationFilterListener(oMutationTable);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		panel2.add(oTableFilter);
		JScrollPane oTablePane = new JScrollPane(oMutationTable);
		oTablePane.setBorder(BorderFactory.createTitledBorder("Available Mutations"));
		oTablePane.getVerticalScrollBar().setUnitIncrement(10);
		panel2.add(oTablePane);
		oMutableTree.addMutableNodeSelectionListener(oMutationTable);
		panel2.add(oPropertiesPanel);
		
		
		JPanel panel3 = new JPanel();

		panel3.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.GRAY));
		panel3.add(oMutationsSelected);
		panel3.setPreferredSize(new Dimension(250,800));		
				
		BorderLayout borderLayout = new BorderLayout();
		
		this.getContentPane().setLayout(borderLayout);	

		this.getContentPane().add(oScroll, BorderLayout.WEST);
		this.getContentPane().add(panel2, BorderLayout.CENTER);
		this.getContentPane().add(panel3, BorderLayout.EAST);
	}
	
	/**
	 * actionPerformed deals with action events
	 */
	public void actionPerformed(ActionEvent e){
		// Make sure the event was done through the menu
		if (e.getSource() instanceof JMenuItem)
		{
			// User Clicked Open
			if (e.getSource().equals(fileMenuOpen)) {
            	// Open the Open Dialog to allow user to select file
            	String current = System.getProperty("user.dir");
            	JFileChooser chooser = new JFileChooser(current);
            	chooser.setMultiSelectionEnabled(true);
            	int option = chooser.showOpenDialog(null); 
            	// If user chose a file, open it
            	if (option == JFileChooser.APPROVE_OPTION) {
            		File[] arFilesToOpen = chooser.getSelectedFiles();
            		boolean bFilesNotOpened = false;
            		
            		for (int i=0; i<arFilesToOpen.length; i++) {
            			if (!ClassLoader.isClassFile(arFilesToOpen[i].getAbsolutePath())) {
            				bFilesNotOpened = true;            				
            			} else if (ClassLoader.classIsAbstractOrInterface(ClassLoader.loadClassFromPath(arFilesToOpen[i].getAbsolutePath()))) {
            				bFilesNotOpened = true;
            			} else {
            				oMutableTree.addMutableClassToTree(ClassLoader.loadClassFromPath(arFilesToOpen[i].getAbsolutePath()));
            			}
            		}            		
            		if (bFilesNotOpened) {
            			JOptionPane.showMessageDialog(null, "One or more files did not load successfully, because they are not valid class files, or are interfaces / abstract classes, which are not supported.");            			
            		}
            	}
            }
			if (e.getSource().equals(optionsMenuPersist)) {
				oMutationTable.setCheckForPersistentMutations(optionsMenuPersist.isSelected());
			}
			if (e.getSource().equals(fileMenuExit)) {
				// The user wants to exit (quit)
				System.exit(0);
			}
		} 
	}
	
	/**
	 * main function
	 * @param args an xml file if the command line version is desired; null if GUI is desired
	 */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {			
			public void run() {
				new MutationTool();
			}			
		});		
	}
}