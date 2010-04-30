package controls.table;

import interfaces.IMutableObject;
import interfaces.IMutationRowActor;
import interfaces.IMutationRowListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import mutations.Mutator;

import utilities.ByteCodeViewer;

/**
 * MutationRow creates a row of the mutation table
 * 
 * @author teh code
 *
 */
public class MutationRow extends JPanel implements ActionListener, MouseListener {
	// Right-click options
	JPopupMenu popupOptions;
	JMenuItem menuViewDiff;
	ByteCodeViewer oDiffViewer;
	
	JLabel lblMutationName;
	JCheckBox chkCreateMutation;	
	JLabel lblMutationType;
	JLabel lblMutationOps;
	JLabel lblMutationLevel;
	
	// Fonts for row displaying
	Font oMutationNameFont;
	Font oPropertiesFont;
	
	// Dimensions for components
	Dimension oMutationNameDim, oPropertiesDim, oRowDimension;
	
	// Background color of row
	Color rowBgColor;
	
	boolean bRowIsSelected;
	boolean bIsHighlighted;
	
	/**
	 * This is the mutable object that the row represents.
	 */
	private IMutableObject oMutableObject;
	
	
	private IMutationRowActor oPropertiesPanel;
	
	private ArrayList<IMutationRowListener> alRowListeners;
	
	//private IMutationRowListener oRowListener;
	
	/**
	 * Constructor for the class MutationRow
	 * 
	 * @param oObject is the mutation object
	 * @param bAltRow color of row
	 * @param oPropertiesPanel used for interfacing with the properties panel
	 * @param oGeneratePanel used for interfacing with the generate panel
	 */
	public MutationRow(IMutableObject oObject, boolean bAltRow, IMutationRowActor oPropertiesPanel, IMutationRowListener oGeneratePanel) {
		// Create arraylist of row listeners
		alRowListeners = new ArrayList<IMutationRowListener>();
		alRowListeners.add(oGeneratePanel);
		
		// Create right-click properties
		popupOptions = new JPopupMenu();
		menuViewDiff = new JMenuItem("View Byte Code...");
		menuViewDiff.addActionListener(this);
		popupOptions.add(menuViewDiff);
		oDiffViewer = null;
		
		this.oMutableObject = oObject;
		this.oPropertiesPanel = oPropertiesPanel;

		lblMutationName = new JLabel(oMutableObject.getMutableMethod() == null ? oMutableObject.getMutableClass().getClassName() : oMutableObject.getMethodName());
		chkCreateMutation = new JCheckBox();
		chkCreateMutation.setOpaque(true);

		lblMutationType = new JLabel("Type: " + oMutableObject.getMutantTypeAsString());
		lblMutationOps = new JLabel("Mutates: " + oMutableObject.getOldOperator() + " to " + oMutableObject.getNewOperator());
		lblMutationLevel = new JLabel("Level: " + oMutableObject.getMutantLevelAsString());
		
		oMutationNameFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		oPropertiesFont = new Font(Font.SANS_SERIF, Font.ITALIC, 12);
		
		oMutationNameDim = new Dimension(495, 20);
		oPropertiesDim = new Dimension(178,20);
		oRowDimension = new Dimension(535, 50);
		
		rowBgColor = bAltRow ? new Color(235,245,255) : new Color(255, 255, 255);
		
//		chkCreateMutation.addActionListener(oTableListener);
		chkCreateMutation.addMouseListener(this);
		this.addMouseListener(this);
		
		this.bRowIsSelected = false;
		
		drawMutantRow();
	}
	
	/**
	 * drawMutantRow draws a row of the mutation table
	 */
	public void drawMutantRow() {
		lblMutationName.setFont(oMutationNameFont);
		lblMutationName.setPreferredSize(oMutationNameDim);
		
		lblMutationType.setFont(oPropertiesFont);
		lblMutationType.setPreferredSize(oPropertiesDim);

		
		lblMutationOps.setFont(oPropertiesFont);
		lblMutationOps.setPreferredSize(oPropertiesDim);

		
		lblMutationLevel.setFont(oPropertiesFont);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.add(lblMutationName);
		this.add(chkCreateMutation);
		this.add(lblMutationType);
		this.add(lblMutationOps);
		this.add(lblMutationLevel);

		
		this.setBackground(rowBgColor);
		chkCreateMutation.setBackground(rowBgColor);
		chkCreateMutation.addActionListener(this);

		
		this.setPreferredSize(oRowDimension);
		this.setMinimumSize(oRowDimension);
		this.setMaximumSize(oRowDimension);
		
		bIsHighlighted = false;
		
	}
	
	/**
	 * Sets color of row
	 * @param bAltRow
	 */
	public void setAltRow(boolean bAltRow) {
		rowBgColor = bAltRow ? new Color(235,245,255) : new Color(255, 255, 255);
		this.setBackground(rowBgColor);
		this.chkCreateMutation.setBackground(rowBgColor);
		this.repaint();
	}
	
	/**
	 * Returns true if a row is selected
	 * @return
	 */
	public boolean isSelected() {
		return this.chkCreateMutation.isSelected();
	}
	
	/**
	 * Performs tasks for setting a row as selected
	 * 
	 * @param bSelected
	 * @param updateRowListeners
	 */
	public void setSelected(boolean bSelected, boolean updateRowListeners) {
		this.chkCreateMutation.setSelected(bSelected);
		if (updateRowListeners)
			fireMutationRowListeners();
	}
	
	/**
	 * Function to fire mutation row listeners
	 */
	private void fireMutationRowListeners() {
		if (this.isSelected()) {
			for(int i=0; i<alRowListeners.size(); i++) {
				alRowListeners.get(i).mutableObjectSelected(this.getMutableObject());
			}
		} else {
			for (int i=0; i<alRowListeners.size(); i++) {
				alRowListeners.get(i).mutableObjectUnSelected(this.getMutableObject());
			}
		}
	}
	
	/**
	 * Function to get the mutable object
	 * @return
	 */
	public IMutableObject getMutableObject() {
		return this.oMutableObject;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		oPropertiesPanel.updatePropertiesPanel(this.oMutableObject);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	@Override
	public void mouseExited(MouseEvent e) {		
		this.setBorder(BorderFactory.createEmptyBorder());		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popupOptions.show(e.getComponent(), e.getX(), e.getY());
		}		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popupOptions.show(e.getComponent(), e.getX(), e.getY());
		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object oActionObject = e.getSource();
		if (oActionObject.equals(menuViewDiff)) {
			// TODO only let them open the bytecodeviewer once
			if (oDiffViewer == null) {
				oDiffViewer = new ByteCodeViewer(this.getMutableObject().getMutableClass(), Mutator.performMutation(this.getMutableObject(), false).getMutableClass());
			}
		} else if (oActionObject.equals(chkCreateMutation)) {
			fireMutationRowListeners();
		}
		
	}
	
}