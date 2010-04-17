package controls.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class MutationTable extends JPanel implements ActionListener {

	public MutationTable() {
		this.setSize(400, 100);
		
		//panel2.add(chk2);
		//panel2.setMinimumSize(new Dimension(400, 50));
		//panel2.setPreferredSize(new Dimension(400, 50));
		//panel2.setMaximumSize(new Dimension(400, 50));
		for (int i=0; i<5000; i++) {
			if (i%2 == 0) {
				MutationRow oRow = new MutationRow("Name", false, "ARITHMETIC", "*", "/", "CLASS", false, this);
				this.add(oRow);
			} else {
				MutationRow oRow = new MutationRow("Name", false, "Logical", "AND", "OR", "CLASS", true, this);
				this.add(oRow);
			}
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		
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

}
