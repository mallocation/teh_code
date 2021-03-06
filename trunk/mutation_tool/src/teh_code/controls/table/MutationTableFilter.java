package teh_code.controls.table;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import teh_code.interfaces.IMutationFilterActor;
import teh_code.interfaces.IMutationFilterListener;
import teh_code.interfaces.IMutationTableListener;
import teh_code.mutations.Mutant;
import teh_code.mutations.cInstructionHelper;


/**
 * MutationTableFilter is used to filter mutations listed in
 * the mutation table.
 * 
 * @author teh code
 *
 */
public class MutationTableFilter extends JPanel implements ActionListener, KeyListener, IMutationTableListener {
	private JLabel lblMutationType, lblMutatedOp, lblNewOp, lblSearch;
	private JComboBox ddlMutationType, ddlMutatedOp, ddlNewOp;
	private JCheckBox chkSelectAll;
	private JTextField txtSearch;
	private Dimension oTableDimension;
	private ArrayList<IMutationFilterListener> alFilterListeners;
	
	/**
	 * Constructor for the MutationFilter class
	 */
	public MutationTableFilter() {
		this.setBorder(BorderFactory.createTitledBorder("Mutation Filter"));
		lblMutationType = new JLabel("Mutation Type");
		lblMutatedOp = new JLabel("Mutated Operation");
		lblNewOp = new JLabel("New Operation");
		lblSearch = new JLabel("Search");
		
		ddlMutationType = new JComboBox(Mutant.getAllMutantTypesAsString());
		ddlMutationType.insertItemAt("", 0);
		ddlMutationType.setSelectedIndex(0);
		ddlMutatedOp = new JComboBox();
		ddlNewOp = new JComboBox();
		
		txtSearch = new JTextField();
	
		chkSelectAll = new JCheckBox("Select All");
		
		oTableDimension = new Dimension(565, 90);
		this.setLayout(null);
		
		this.add(lblMutationType);
		lblMutationType.setBounds(10, 20, 90, 30);
		
		this.add(ddlMutationType);
		ddlMutationType.setBounds(95, 25, 100, 22);
		
		this.add(lblMutatedOp);
		//lblMutatedOp.setBounds(395, 10, 107, 30);
		lblMutatedOp.setBounds(225, 20, 107, 30);
		
		this.add(ddlMutatedOp);
		//ddlMutatedOp.setBounds(507, 15, 50, 22);
		ddlMutatedOp.setBounds(337, 25, 50, 22);
		
		this.add(lblNewOp);
		lblNewOp.setBounds(416, 20, 107, 30);
		
		this.add(ddlNewOp);
		ddlNewOp.setBounds(507, 25, 50, 22);
		
		this.add(chkSelectAll);
		chkSelectAll.setBounds(410, 55, 80, 30);
		
		this.add(lblSearch);
		lblSearch.setBounds(45, 55, 70, 30);
		
		this.add(txtSearch);
		txtSearch.setBounds(95, 60, 240, 22);
		
		this.setPreferredSize(oTableDimension);
		this.setMinimumSize(oTableDimension);
		this.setMaximumSize(oTableDimension);
		
		ddlMutationType.addActionListener(this);
		ddlMutatedOp.addActionListener(this);
		ddlNewOp.addActionListener(this);
		txtSearch.addKeyListener(this);		
		chkSelectAll.addActionListener(this);
		
		this.alFilterListeners = new ArrayList<IMutationFilterListener>();		
	}
	
	/**
	 * Adds mutation filter listener
	 * @param oListener mutation table listener
	 */
	public void addMutationFilterListener(IMutationFilterListener oListener) {
		this.alFilterListeners.add(oListener);
	}
	
	/**
	 * Clears the select all check box
	 */
	private void clearSelectAll() {
		this.chkSelectAll.setSelected(false);
	}
	
	/**
	 * Fires the select all listeners
	 */
	private void fireSelectAllListeners() {
		for (int i=0; i<alFilterListeners.size(); i++) {
			this.alFilterListeners.get(i).selectAllVisible(chkSelectAll.isSelected());
		}
	}
	
	/**
	 * Fires the filter listeners
	 */
	private void fireFilterListeners() {
		String mutantSearch, mutationType, oldOp, newOp;
		mutantSearch = txtSearch.getText();
		mutationType = ddlMutationType.getSelectedItem() == null ? "" : ddlMutationType.getSelectedItem().toString();
		oldOp = ddlMutatedOp.getSelectedItem() == null ? "" : ddlMutatedOp.getSelectedItem().toString();
		newOp = ddlNewOp.getSelectedItem() == null ? "" : ddlNewOp.getSelectedItem().toString();
		
		for (int i=0; i<alFilterListeners.size(); i++) {
			this.alFilterListeners.get(i).filterMutations(mutantSearch, mutationType, oldOp, newOp);
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JCheckBox) {
			fireSelectAllListeners();
		} else {
			if (e.getSource().equals(ddlMutationType)) {
				String mutationType = ddlMutationType.getSelectedItem().toString();
				if (mutationType != "") {
					String[] arOps = cInstructionHelper.getInstructionsAsString(Mutant.stringToMutantType(mutationType));
					ddlMutatedOp.removeAllItems();
					ddlNewOp.removeAllItems();
					ddlMutatedOp.addItem("");
					ddlNewOp.addItem("");
					for (int i=0; i<arOps.length; i++) {
						ddlMutatedOp.addItem(arOps[i]);
						ddlNewOp.addItem(arOps[i]);
					}
				} else {
					ddlMutatedOp.removeAllItems();
					ddlNewOp.removeAllItems();
				}
			}
			fireFilterListeners();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyReleased(KeyEvent e) {
		fireFilterListeners();
		clearSelectAll();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//fireFilterListeners();		
	}
	
	/**
	 * resets filters
	 */
	private void resetFilters() {
		ddlMutationType.setSelectedIndex(0);
		ddlMutatedOp.removeAllItems();
		ddlNewOp.removeAllItems();
		txtSearch.setText("");
		chkSelectAll.setSelected(false);	
	}

	@Override
	public void mutationTableLoaded() {
		this.resetFilters();		
	}
	
	
	
}
