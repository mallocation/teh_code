package controls.mutations;

import interfaces.IMutableObject;
import interfaces.IMutationRowListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.bcel.classfile.JavaClass;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import mutations.MutantCollection;
import mutations.Mutator;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * MutationsSelected.java creates the mutations selected panel.
 * This panel is used in the final step of generating mutant classes.
 *
 * @author teh_code
 * @version 1.0
 *
 */

public class MutationsSelected extends JPanel implements ActionListener, IMutationRowListener, PropertyChangeListener {
	private DefaultListModel listModel;
	private JList list;
	private JButton button;
	private JProgressBar progClassBar;
	
	/**
	 * This is the constructor for the MutationsSelected class
	 * 
	 */
	public MutationsSelected() {
		listModel = new DefaultListModel();
		
		//listModel.addElement(new MutationsSelectedRow(null, true));
		//listModel.addElement(new MutationsSelectedRow(null, false));
		
		//setLayout(new GridBagLayout());
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		list = new JList(listModel);
		list.setCellRenderer(new MutationsSelectedCellRenderer());
		//list.setVisibleRowCount(2);
		JScrollPane pane = new JScrollPane(list);
		pane.setPreferredSize(new Dimension(240,500));
		add(pane);
		
		
		//Create "Generate" button
		button = new JButton("Generate");
		button.addActionListener(this);
		button.setAlignmentX(CENTER_ALIGNMENT);
		add(button);
		
		progClassBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		progClassBar.setStringPainted(true);
		add(progClassBar);
		
		this.setVisible(true);
	}
	
	/**
	 * addMutation adds a possible mutation to the mutations to be generated
	 * 
	 * @param mObject mutation object to be added
	 */
	public void addMutation(IMutableObject mObject){
		for(int i=0;i<listModel.getSize();i++){
			MutationsSelectedRow mRow = (MutationsSelectedRow)listModel.getElementAt(i);
			if(mObject.getMutableClass().equals(mRow.getJavaClass())){
				if (!mRow.getMutationCollection().containsMutableObject(mObject)) {
					mRow.addMutableObj(mObject);
				}
				return;
			}
		}
		MutationsSelectedRow tempRow = new MutationsSelectedRow(mObject.getMutableClass(), false);
		listModel.addElement(tempRow);
		tempRow.addMutableObj(mObject);
	}
	
	/**
	 * removeMutation removes a mutation from the list of mutations to be generated
	 * 
	 * @param mObject
	 */
	public void removeMutation(IMutableObject mObject){
		for(int i=0;i<listModel.getSize();i++){
			MutationsSelectedRow mRow = (MutationsSelectedRow)listModel.getElementAt(i);
			if(mObject.getMutableClass().equals(mRow.getJavaClass())){
				mRow.removeMutableObj(mObject);
				if (mRow.getMutationCollection().getCollectionCount() == 0){
					listModel.removeElement(mRow);
				}
				return;
			}
		}
	}
	
	/**
	 * action performed function to take deal with action events
	 */
	public void actionPerformed(ActionEvent e){
		if (e.getSource().equals(button)) {
			GenerateMutationsWorker oWorker = new GenerateMutationsWorker(this.listModel);
			oWorker.addPropertyChangeListener(this);
			oWorker.execute();
//			for (int i=0; i<this.listModel.size(); i++) {
//				new GenerateMutationsWorker((MutationsSelectedRow)listModel.getElementAt(i), i).execute();
//			}
		}
	}
	
	/**
	 * GenerateMutationsWorker 
	 *
	 */
	private class GenerateMutationsWorker extends SwingWorker<Void, Void> {
		private ListModel oListModel;
		private MutationsSelectedRow rowToGenerate;
		private int rowIndex;
		
		public GenerateMutationsWorker(ListModel oListModel) {
			this.oListModel = oListModel;
		}

		@Override
		protected Void doInBackground() throws Exception {
			float indiv = (float)(1/(float)oListModel.getSize()) * 100;
			float total = 0;
			for (int i=0; i<oListModel.getSize(); i++) {
				rowToGenerate = (MutationsSelectedRow)oListModel.getElementAt(i);
				if (!rowToGenerate.isExported()) {
					list.setSelectedIndex(i);
					Mutator.generate(rowToGenerate.getMutationCollection());
					rowToGenerate.setExported(true);
					repaint();
				}
				total += indiv;
				setProgress((int)total);
				System.out.println((int)total + "% complete.");
			}
			return null;
		}
		
		@Override
		protected void done() {
			list.clearSelection();
			super.done();
		}
		
	}
	
//	private class GenerateMutationsWorker extends SwingWorker<Void, Void> {
//		
//		private MutationsSelectedRow rowToGenerate;
//		private int rowIndex;
//		
//		public GenerateMutationsWorker(MutationsSelectedRow oListRow, int rowIndex) {
//			this.rowToGenerate = oListRow;
//			this.rowIndex = rowIndex;
//		}
//
//		@Override
//		protected Void doInBackground() throws Exception {
//			//this.rowToGenerate.setWorking();
//			//repaint();
//			list.setSelectedIndex(rowIndex);
//			Mutator.generate(rowToGenerate.getMutationCollection());
//			return null;
//		}
//		
//		@Override
//		protected void done() {
//			this.rowToGenerate.setExported(true);
//			repaint();
//			super.done();
//		}
//		
//	}
	
	/**
	 * getMutableObjectsForClass returns all mutable objects for a class
	 * 
	 * @param oClass a java class
	 * @return MutantCollection a collection of mutants
	 */
	public MutantCollection getMutableObjectsForClass(JavaClass oClass) {
		for(int i=0;i<listModel.getSize();i++){
			MutationsSelectedRow mRow = (MutationsSelectedRow)listModel.getElementAt(i);
			if(oClass.equals(mRow.getJavaClass())){
				return mRow.getMutationCollection();
			}
		}		
		return new MutantCollection();
	}

	@Override
	public void mutableObjectSelected(IMutableObject oMutant) {
		this.addMutation(oMutant);
		this.repaint();
	}

	@Override
	public void mutableObjectUnSelected(IMutableObject oMutant) {
		this.removeMutation(oMutant);
		this.repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == "progress") {
			int progress = (Integer) evt.getNewValue();
			progClassBar.setValue(progress);	
		}
	}
}