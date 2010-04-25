package controls.table;

import interfaces.IMutableObject;
import interfaces.IMutableTreeListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import utilities.ClassLoader;

import mutations.Mutant;
import mutations.MutationFactory;

import controls.MutableNode;

public class MutationTable extends JPanel implements ActionListener, IMutableTreeListener {

	private ArrayList<MutationRow> alMutableRows;
	
	public MutationTable() {
		this.alMutableRows = new ArrayList<MutationRow>();
		this.setSize(400, 100);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		loadTableWithClass();
		
		
	}
	
	private void showAllMutableRows() {
		this.removeAll();
		for (int i=0; i<alMutableRows.size(); i++) {
			this.add(alMutableRows.get(i));
		}
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
	
	public void loadTableWithClass() {
		ArrayList<IMutableObject> alMutableObjects = MutationFactory.createIMutableObjects(ClassLoader.loadClassFromPath("C:\\Users\\cjohnson\\Documents\\Class Documents\\Spring 07\\CSE155\\HW #6\\CasualEmployee.class"));
		this.alMutableRows.clear();
		this.removeAll();
		for (int i=0; i<alMutableObjects.size(); i++) {
			IMutableObject oMutableObject = alMutableObjects.get(i);
			boolean bAltRow = (i%2 == 0) ? false : true;
			alMutableRows.add(new MutationRow(oMutableObject.getMutableClass().getClassName(), false, oMutableObject.getMutantTypeAsString(), oMutableObject.getOldOperator(),
												oMutableObject.getNewOperator(), oMutableObject.getMutantLevelAsString(), bAltRow , this));
		}
		showAllMutableRows();
	}

	@Override
	public void mutableNodeSelectionChanged(MutableNode oSelectedNode) {
		ArrayList<IMutableObject> alMutableObjects = MutationFactory.createIMutableObjects(oSelectedNode.getMutableClass());
		this.alMutableRows.clear();
		for (int i=0; i<alMutableObjects.size(); i++) {
			IMutableObject oMutableObject = alMutableObjects.get(i);
			boolean bAltRow = (i%2 == 0) ? false : true;
			alMutableRows.add(new MutationRow(oMutableObject.getMutableClass().getClassName(), false, oMutableObject.getMutantTypeAsString(), oMutableObject.getOldOperator(),
												oMutableObject.getNewOperator(), oMutableObject.getMutantTypeAsString(), bAltRow , this));
		}
	}

}
