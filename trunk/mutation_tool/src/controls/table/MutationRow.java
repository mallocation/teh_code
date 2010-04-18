package controls.table;

import interfaces.IMutableObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class MutationRow extends JPanel implements ActionListener, FocusListener {
	
	JLabel lblMutationName;
	JCheckBox chkCreateMutation;	
	JLabel lblMutationType;
	JLabel lblMutationOps;
	JLabel lblMutationLevel;
	
	Font oMutationNameFont;
	Font oPropertiesFont;
	
	Dimension oMutationNameDim, oPropertiesDim, oRowDimension;
	
	Color rowBgColor;
	
	

	public MutationRow(String mutationName, boolean bCreateMutation, String mutationType, String oldOperator,
					   	String newOperator, String mutationLevel, boolean bAltRow, ActionListener oTableListener) {
		lblMutationName = new JLabel(mutationName);
		chkCreateMutation = new JCheckBox();
		chkCreateMutation.setSelected(bCreateMutation);
		lblMutationType = new JLabel("Type: " + mutationType);
		lblMutationOps = new JLabel("Mutates: " + oldOperator + " to " + newOperator);
		lblMutationLevel = new JLabel("Level: " + mutationLevel);
		
		oMutationNameFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		oPropertiesFont = new Font(Font.SANS_SERIF, Font.ITALIC, 10);
		
		oMutationNameDim = new Dimension(360, 20);
		oPropertiesDim = new Dimension(150,20);
		oRowDimension = new Dimension(400, 50);
		
		rowBgColor = bAltRow ? new Color(238, 238, 238) : new Color(255, 255, 255);
		
		chkCreateMutation.addActionListener(oTableListener);
		
		drawMutantRow();
	}
	
	public void drawMutantRow() {
		lblMutationName.setFont(oMutationNameFont);
		lblMutationName.setPreferredSize(oMutationNameDim);
		
		lblMutationType.setFont(oPropertiesFont);
		lblMutationType.setPreferredSize(oPropertiesDim);
		
		lblMutationOps.setFont(oPropertiesFont);
		lblMutationOps.setPreferredSize(oPropertiesDim);
		
		lblMutationLevel.setFont(oPropertiesFont);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(rowBgColor);
		
		this.add(lblMutationName);
		this.add(chkCreateMutation);
		this.add(lblMutationType);
		this.add(lblMutationOps);
		this.add(lblMutationLevel);
		
		this.setPreferredSize(oRowDimension);
	}
	
	public boolean isSelected() {
		return this.chkCreateMutation.isSelected();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		this.setBorder(null);		
	}
	
}





//public class MutationRenderer extends JPanel implements ListCellRenderer {
//	
//	private JLabel lblMutationLevel;
//	private JLabel lblMutationName;
//	private JLabel lblMutationType;
//	private JCheckBox chkSelectMutant;
//	private IMutableObject oMutant;
//	
//	public MutationRenderer () {
//		this.lblMutationLevel = new JLabel();
//		this.lblMutationName = new JLabel();
//		this.lblMutationType = new JLabel();
//		this.chkSelectMutant = new JCheckBox();
//	
//		lblMutationName.setSize(new Dimension(600, 50));
//		this.setLayout(new FlowLayout(FlowLayout.LEFT));
//		this.add(lblMutationName);
//		this.add(lblMutationType);
//		this.add(lblMutationLevel);
//		this.add(chkSelectMutant);
//		
//		
//		this.setSize(600, 100);
//		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//	}
//	
//	@Override
//	public Component getListCellRendererComponent(JList list, Object value,int index, boolean isSelected, boolean cellHasFocus) {
//		this.oMutant = (IMutableObject)value;
//		
//		this.lblMutationLevel.setText("Level : " + oMutant.getMutantLevelAsString());
//		this.lblMutationName.setText("Mutation!!!");
//		this.lblMutationType.setText("Type : " + oMutant.getMutantTypeAsString());
//		this.chkSelectMutant.setSelected(isSelected);
//
//		return this;		
//	}
//
//}
