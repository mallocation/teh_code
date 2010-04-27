package mutations;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import mutations.MutantCollection;


public class MutationsSelected extends JPanel implements ActionListener {
	MutantCollection mc;
	private MutationsSelectedRow lastSelected;
	
	private MutationsSelectedRow rows[] = {
			new MutationsSelectedRow("ClassName1", null),
			new MutationsSelectedRow("ClassName2", null),
			new MutationsSelectedRow("ClassName3", null),
	};
	
	private JList list = new JList(rows);
	
	public MutationsSelected() {
		//setLayout(new BorderLayout());
		setLayout(new GridBagLayout());
		
		list = new JList(rows);
		list.setCellRenderer(new MutationsSelectedCellRenderer());
		//list.setVisibleRowCount(2);
		JScrollPane pane = new JScrollPane(list);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.PAGE_START;
		c.gridx = 0;
		c.gridy = 0;
		
		add(pane, c);
		
		
		//Create "Generate" button
		JButton button = new JButton("Generate");
		button.addActionListener(this);
		
		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.HORIZONTAL;
		d.anchor = GridBagConstraints.PAGE_END;
		d.gridx = 0;
		d.gridy = 1;
		
		add(button, d);
		
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
		        boolean adjust = e.getValueIsAdjusting();
		        if (!adjust) {
		          JList list = (JList) e.getSource();
		          int selections[] = list.getSelectedIndices();
		          Object selectionValues[] = list.getSelectedValues();
		          for (int i = 0, n = selections.length; i < n; i++) {
		            if (i == 0) {
		              System.out.println(" Selections: ");
		            }
		            System.out.println(selections[i] + "/" + selectionValues[i] + " ");
		            lastSelected = (MutationsSelectedRow) selectionValues[i];
		          }
		        }
			}
	});
	}
	
	public void actionPerformed(ActionEvent e){
		System.out.println("Generate: " + lastSelected);
	}
	
	public MutationsSelectedRow findRow(int index){
		MutationsSelectedRow ss = rows[0];
		
		System.out.println(ss.className);
		
		return ss;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Selected Mutations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MutationsSelected());
		frame.setSize(new Dimension(300,300));
		frame.setVisible(true);
	}
}