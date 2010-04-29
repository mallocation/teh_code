package controls.table;

import interfaces.IMutableObject;
import interfaces.IMutableTreeListener;
import interfaces.IMutationFilterListener;
import interfaces.IMutationRowActor;
import interfaces.IMutationRowListener;
import mutations.MutantCollection;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import mutations.MutationFactory;

import controls.tree.MutableNode;

public class MutationTable extends JPanel implements ActionListener, IMutableTreeListener, IMutationFilterListener {

	private ArrayList<MutationRow> alMutableRows;
	
	private IMutationRowActor oPropertiesPanel;
	
	private IMutationRowListener oRowListener;
	
	private JLabel lblNoMutations;
	
	private ImageIcon imgLoadingTable;
	
	public MutationTable(IMutationRowActor oPropertiesPanel, IMutationRowListener oGeneratePanel) {
		this.alMutableRows = new ArrayList<MutationRow>();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.oPropertiesPanel = oPropertiesPanel;
		this.oRowListener = oGeneratePanel;
		this.lblNoMutations = new JLabel("Loading Mutations...");
		this.imgLoadingTable = new ImageIcon(getClass().getResource("../../images/TableLoader.gif"));
		this.lblNoMutations.setIcon(imgLoadingTable);
		this.add(lblNoMutations);
	}
	
	/**
	 * Get a collection returning all mutations that are selected to be created.
	 * 
	 * @return collection of IMutableObject
	 */
	public MutantCollection getSelectedMutants() {
		MutantCollection oSelectedColl = new MutantCollection();
		for (int i=0; i<alMutableRows.size(); i++) {
			if (alMutableRows.get(i).isSelected()) {
				oSelectedColl.addMutant(alMutableRows.get(i).getMutableObject());
			}
		}
		return oSelectedColl;
	}
	
	private void showAllMutableRows() {
		this.removeAll();
		this.add(lblNoMutations);
		this.setVisible(false);
		this.setVisible(true);
		for (int i=0; i<alMutableRows.size(); i++) {
			this.add(alMutableRows.get(i));
		}
		if (this.getComponentCount() == 0) {
			this.add(lblNoMutations);
		}
		this.remove(lblNoMutations);
		this.setVisible(false);
		this.setVisible(true);
	}
	
	public void getSelectedMutationCount() {
		Component[] arComponents = this.getComponents();
		int nSelected = 0;
		for (int i=0; i<arComponents.length; i++) {
			MutationRow oRow = (MutationRow)arComponents[i];
			if (oRow.isSelected()) {
				nSelected++;
			}			
		}
		System.out.println(nSelected + " selected mutations.");		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getSelectedMutationCount();		
	}

	@Override
	public void mutableNodeSelectionChanged(MutableNode oSelectedNode) {
		MutantCollection alMutableObjects = new MutantCollection();
		if (oSelectedNode.getMutableMethod() == null) {
			//show both class level and method level!
			alMutableObjects.appendToCollection(MutationFactory.createIMutableObjects(oSelectedNode.getMutableClass(), null));
			for (int i=0; i<oSelectedNode.getMutableClass().getMethods().length; i++) {
				alMutableObjects.appendToCollection(MutationFactory.createIMutableObjects(oSelectedNode.getMutableClass(), oSelectedNode.getMutableClass().getMethods()[i]));
			}			
		} else {
			alMutableObjects = MutationFactory.createIMutableObjects(oSelectedNode.getMutableClass(), oSelectedNode.getMutableMethod());
		}
		//MutantCollection alMutableObjects = MutationFactory.createIMutableObjects(oSelectedNode.getMutableClass(), null);
		
		this.alMutableRows.clear();
		this.removeAll();
		for (int i=0; i<alMutableObjects.getMutants().size(); i++) {
			IMutableObject oMutableObject = alMutableObjects.getMutants().get(i);
			boolean bAltRow = (i%2 == 0) ? false : true;
			alMutableRows.add(new MutationRow(oMutableObject, bAltRow, oPropertiesPanel, oRowListener));
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				showAllMutableRows();
				
			}
		});
		//showAllMutableRows();
	}

	@Override
	public void filterMutations(String mutantSearch, String mutantType, String oldOp, String newOp) {
		for (int i=0; i<alMutableRows.size(); i++) {
			MutationRow oRow = alMutableRows.get(i);

			String matchSearchTerm = oRow.getMutableObject().getMutableMethod() == null ? oRow.getMutableObject().getMutableClass().getClassName() : oRow.getMutableObject().getMutableMethod().getName();
			String matchMutant = mutantType.equals("") ? oRow.getMutableObject().getMutantTypeAsString() : mutantType;
			String matchOldOp = oldOp.equals("") ? oRow.getMutableObject().getOldOperator() : oldOp;
			String matchNewOp = newOp.equals("") ? oRow.getMutableObject().getNewOperator() : newOp;

			
			if (oRow.getMutableObject().getMutantTypeAsString().equalsIgnoreCase(matchMutant) &&
					oRow.getMutableObject().getOldOperator().equals(matchOldOp) &&
					oRow.getMutableObject().getNewOperator().equals(matchNewOp) &&
					matchSearchTerm.toLowerCase().contains(mutantSearch.toLowerCase())) {
				oRow.setVisible(true);
			} else {
				oRow.setVisible(false);
			}		
		}
	}

	@Override
	public void selectAllVisible(boolean bSelectAll) {
		for (int i=0; i<alMutableRows.size(); i++) {
			if (alMutableRows.get(i).isVisible()) {
				alMutableRows.get(i).setSelected(bSelectAll);
			}
		}		
	}

}
