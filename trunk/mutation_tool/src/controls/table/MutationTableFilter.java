package controls.table;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mutations.Mutant;
import mutations.cInstructionHelper;
import interfaces.IMutationFilterListener;

public class MutationTableFilter extends JPanel implements ActionListener {
	private JLabel lblMutationType, lblMutatedOp, lblNewOp;
	private JComboBox ddlMutationType, ddlMutatedOp, ddlNewOp;
	private JCheckBox chkSelectAll;
	private Dimension oTableDimension;
	private ArrayList<IMutationFilterListener> alFilterListeners;
	
	public MutationTableFilter() {
		this.setBorder(BorderFactory.createTitledBorder("Mutation Filter"));
		lblMutationType = new JLabel("Mutation Type");
		lblMutatedOp = new JLabel("Mutated Operation");
		lblNewOp = new JLabel("New Operation");
		
		ddlMutationType = new JComboBox(Mutant.getAllMutantTypesAsString());
		ddlMutationType.insertItemAt("", 0);
		ddlMutationType.setSelectedIndex(0);
		ddlMutatedOp = new JComboBox();
		ddlNewOp = new JComboBox();
	
		chkSelectAll = new JCheckBox("Select All");
		
		oTableDimension = new Dimension(565, 90);
		
		
		this.add(lblMutationType);
		this.add(ddlMutationType);
		this.add(lblMutatedOp);
		this.add(ddlMutatedOp);
		this.add(lblNewOp);
		this.add(ddlNewOp);
		this.add(chkSelectAll);
		this.setPreferredSize(oTableDimension);
		this.setMinimumSize(oTableDimension);
		this.setMaximumSize(oTableDimension);
		
		ddlMutationType.addActionListener(this);
		ddlMutatedOp.addActionListener(this);
		ddlNewOp.addActionListener(this);
		
		chkSelectAll.addActionListener(this);
		
		this.alFilterListeners = new ArrayList<IMutationFilterListener>();		
	}
	
	public void addMutationFilterListener(IMutationFilterListener oListener) {
		this.alFilterListeners.add(oListener);
	}
	
	private void fireSelectAllListeners() {
		for (int i=0; i<alFilterListeners.size(); i++) {
			this.alFilterListeners.get(i).selectAllVisible(chkSelectAll.isSelected());
		}
	}
	
	private void fireFilterListeners() {
		String mutationType, oldOp, newOp;
		mutationType = ddlMutationType.getSelectedItem() == null ? "" : ddlMutationType.getSelectedItem().toString();
		oldOp = ddlMutatedOp.getSelectedItem() == null ? "" : ddlMutatedOp.getSelectedItem().toString();
		newOp = ddlNewOp.getSelectedItem() == null ? "" : ddlNewOp.getSelectedItem().toString();
		
		for (int i=0; i<alFilterListeners.size(); i++) {
			this.alFilterListeners.get(i).filterMutations(mutationType, oldOp, newOp);
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
	
	
	
}
