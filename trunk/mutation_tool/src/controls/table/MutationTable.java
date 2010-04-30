package controls.table;

import interfaces.IMutableObject;
import interfaces.IMutableTreeListener;
import interfaces.IMutationFilterListener;
import interfaces.IMutationRowActor;
import interfaces.IMutationRowListener;
import interfaces.IMutationTableListener;
import mutations.MutantCollection;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.apache.bcel.classfile.JavaClass;

import utilities.ParseXML;

import mutations.MutationFactory;
import controls.mutations.MutationsSelected;
import controls.tree.MutableNode;

/**
 * MutationTable is responsible for displaying a table of possible mutations
 * 
 * @author teh code
 *
 */
public class MutationTable extends JPanel implements IMutableTreeListener, IMutationFilterListener {

	private ArrayList<MutationRow> alMutableRows;
	
	private IMutationRowActor oPropertiesPanel;
	
	private IMutationRowListener oRowListener;
	
	private JLabel lblNoMutations, lblLoading;
	
	private ImageIcon imgLoadingTable;
	
	private ArrayList<IMutationTableListener> alTableListeners;
	
	private MutationsSelected oMutationsSelectedPanel;
	
	private boolean bCheckPersistantMutations;
	
	private Hashtable<JavaClass, MutantCollection> htPersistantMutations;
	
	/**
	 * Constructor for the MutationTable class
	 * 
	 * @param oTableFilterPanel
	 * @param oPropertiesPanel
	 * @param oGeneratePanel
	 * @param oSelectedMutations
	 */
	public MutationTable(IMutationTableListener oTableFilterPanel, IMutationRowActor oPropertiesPanel, IMutationRowListener oGeneratePanel, MutationsSelected oSelectedMutations) {
		this.alMutableRows = new ArrayList<MutationRow>();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.oPropertiesPanel = oPropertiesPanel;
		this.oRowListener = oGeneratePanel;
		this.lblNoMutations = new JLabel("No mutations available.");
		this.lblLoading = new JLabel("Loading mutations...");
		this.imgLoadingTable = new ImageIcon(getClass().getResource("../../images/TableLoader2.gif"));
		this.lblLoading.setIcon(imgLoadingTable);
		
		this.alTableListeners = new ArrayList<IMutationTableListener>();
		
		this.addMutationTableListener(oTableFilterPanel);
		
		this.oMutationsSelectedPanel = oSelectedMutations;
		
		this.bCheckPersistantMutations = true;
		
		this.htPersistantMutations = new Hashtable<JavaClass, MutantCollection>();
	}
	
	/**
	 * Get a collection returning all mutations that are selected to be created.
	 * 
	 * @return oSelectedColl a collection of IMutableObject
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
	
	/**
	 * Function to set check for persistent mutations to true or false
	 * @param bCheck true if checking for persistant mutations
	 */
	public void setCheckForPersistentMutations(boolean bCheck) {
		this.bCheckPersistantMutations = bCheck;
	}
	
	/**
	 * Function to show mutable rows
	 */
	private void addMutableRowsToTable() {
		this.setVisible(false);
		this.setVisible(true);
		if (this.alMutableRows.size() != 0) {
			for (int i=0; i<alMutableRows.size(); i++) {
				if (oMutationsSelectedPanel.getMutableObjectsForClass(alMutableRows.get(i).getMutableObject().getMutableClass()).containsMutableObject(alMutableRows.get(i).getMutableObject())) {
					alMutableRows.get(i).setSelected(true, false);
				}
				this.add(alMutableRows.get(i));
			}
		} else {
			this.add(lblNoMutations);
		}			
		this.setVisible(false);
		this.setVisible(true);
	}
	
	/**
	 * This is fired when 
	 */
	@Override
	public void mutableNodeSelectionChanged(MutableNode oSelectedNode) {
		this.removeAll();
		this.add(lblLoading);
		this.setVisible(false);
		this.setVisible(true);
		
		for (int i=0; i<alTableListeners.size(); i++) {
			this.alTableListeners.get(i).mutationTableLoaded();
		}
		
		new TableLoader(oSelectedNode).execute();
	}
	
	/**
	 * TableLoader loads the mutation table
	 * 
	 * @author teh code
	 *
	 */
	private class TableLoader extends SwingWorker<Void, Void> {
		private MutableNode selectedNode;
		
		public TableLoader(MutableNode selectedNode) {
			this.selectedNode = selectedNode;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			MutantCollection alMutableObjects = new MutantCollection();
			ParseXML oXMLParser = new ParseXML();
			
			if (selectedNode.getMutableMethod() == null) {
				//show both class level and method level!
				alMutableObjects.appendToCollection(MutationFactory.createIMutableObjects(selectedNode.getMutableClass(), null));
				for (int i=0; i<selectedNode.getMutableClass().getMethods().length; i++) {
					alMutableObjects.appendToCollection(MutationFactory.createIMutableObjects(selectedNode.getMutableClass(), selectedNode.getMutableClass().getMethods()[i]));
				}			
			} else {
				alMutableObjects = MutationFactory.createIMutableObjects(selectedNode.getMutableClass(), selectedNode.getMutableMethod());
			}
			
			alMutableRows.clear();
			removeAll();

			for (int i=0; i<alMutableObjects.getMutants().size(); i++) {
				IMutableObject oMutableObject = alMutableObjects.getMutants().get(i);
				boolean bAltRow = (i%2 == 0) ? false : true;	
				alMutableRows.add(new MutationRow(oMutableObject, bAltRow, oPropertiesPanel, oRowListener));
			}
			
			if (bCheckPersistantMutations && !htPersistantMutations.containsKey(selectedNode.getMutableClass())) {
				htPersistantMutations.put(selectedNode.getMutableClass(), oXMLParser.getPersistentMutations(selectedNode.getMutableClass()));
				MutantCollection mcPersistant = htPersistantMutations.get(selectedNode.getMutableClass());
				for (int i=0; i<alMutableRows.size(); i++) {
					if (mcPersistant.containsMutableObject(alMutableRows.get(i).getMutableObject())) {
						alMutableRows.get(i).setSelected(true, true);
					}
				}
			}
			
						
			return null;
		}
		
		@Override
		protected void done() {
			if (!isCancelled())				
				addMutableRowsToTable();
			super.done();
		}
	}

	@Override
	public void filterMutations(String mutantSearch, String mutantType, String oldOp, String newOp) {
		int nVisible = 0;
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
				if (nVisible % 2 == 0) {
					oRow.setAltRow(false);
				} else {
					oRow.setAltRow(true);
				}
				nVisible++;
				oRow.setVisible(true);
			} else {
				oRow.setVisible(false);
			}		
		}	
		this.repaint();
	}

	@Override
	public void selectAllVisible(boolean bSelectAll) {
		for (int i=0; i<alMutableRows.size(); i++) {
			if (alMutableRows.get(i).isVisible()) {
				alMutableRows.get(i).setSelected(bSelectAll, true);
			}
		}		
	}
	
	/**
	 * Adds a mutation table listener
	 * @param oTableListener mutation table listener
	 */
	public void addMutationTableListener(IMutationTableListener oTableListener) {
		if (!this.alTableListeners.contains(oTableListener)) {
			this.alTableListeners.add(oTableListener);
		}
	}

}
