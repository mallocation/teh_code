package controls.table;

import interfaces.IMutableObject;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;


import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class MutationRenderer extends JPanel implements ListCellRenderer {
	
	private JLabel lblMutationLevel;
	private JLabel lblMutationName;
	private JLabel lblMutationType;
	private JCheckBox chkSelectMutant;
	private IMutableObject oMutant;
	
	public MutationRenderer () {
		this.lblMutationLevel = new JLabel();
		this.lblMutationName = new JLabel();
		this.lblMutationType = new JLabel();
		this.chkSelectMutant = new JCheckBox();
	
		lblMutationName.setSize(new Dimension(600, 50));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(lblMutationName);
		this.add(lblMutationType);
		this.add(lblMutationLevel);
		this.add(chkSelectMutant);
		
		
		this.setSize(600, 100);
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,int index, boolean isSelected, boolean cellHasFocus) {
		this.oMutant = (IMutableObject)value;
		
		this.lblMutationLevel.setText("Level : " + oMutant.getMutantLevelAsString());
		this.lblMutationName.setText("Mutation!!!");
		this.lblMutationType.setText("Type : " + oMutant.getMutantTypeAsString());
		this.chkSelectMutant.setSelected(isSelected);

		return this;		
	}

}
