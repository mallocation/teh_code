package mutations;

import interfaces.IMutableObject;
import interfaces.IMutationRowListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import mutations.MutantCollection;
import java.util.ArrayList;
import java.util.Iterator;


public class MutationsSelected extends JPanel implements ActionListener, IMutationRowListener {
	MutantCollection mc;
	private DefaultListModel listModel;
	private JList list;
	private JButton button; 
	
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
		
		this.setVisible(true);
	}
	
	public void addMutation(IMutableObject mObject){
		for(int i=0;i<listModel.getSize();i++){
			MutationsSelectedRow mRow = (MutationsSelectedRow)listModel.getElementAt(i);
			if(mObject.getMutableClass().equals(mRow.getJavaClass())){
				mRow.addMutableObj(mObject);
				return;
			}
		}
		MutationsSelectedRow tempRow = new MutationsSelectedRow(mObject.getMutableClass(), false);
		listModel.addElement(tempRow);
		tempRow.addMutableObj(mObject);
	}
	
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
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource().equals(button)) {
			for (int i=0; i<this.listModel.getSize(); i++) {
				MutationsSelectedRow oRow = (MutationsSelectedRow)this.listModel.getElementAt(i);
				Mutator.generate(oRow.getMutationCollection());
			}
		}
	}
/*
	public static void main(String[] args) {
		JFrame frame = new JFrame("Selected Mutations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MutationsSelected());
		frame.pack();
		frame.setVisible(true);
	}*/

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
}