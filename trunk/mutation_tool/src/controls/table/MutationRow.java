package controls.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class MutationRow extends JPanel implements MouseListener {
	
	JLabel lblMutationName;
	JCheckBox chkCreateMutation;	
	JLabel lblMutationType;
	JLabel lblMutationOps;
	JLabel lblMutationLevel;
	
	Font oMutationNameFont;
	Font oPropertiesFont;
	
	Dimension oMutationNameDim, oPropertiesDim, oRowDimension;
	
	Color rowBgColor;
	
	boolean bRowIsSelected;
	boolean bIsHighlighted;
	
	

	public MutationRow(String mutationName, boolean bCreateMutation, String mutationType, String oldOperator,
					   	String newOperator, String mutationLevel, boolean bAltRow, ActionListener oTableListener) {
		lblMutationName = new JLabel(mutationName);
		chkCreateMutation = new JCheckBox();
		chkCreateMutation.setOpaque(true);
		chkCreateMutation.setSelected(bCreateMutation);
		lblMutationType = new JLabel("Type: " + mutationType);
		lblMutationOps = new JLabel("Mutates: " + oldOperator + " to " + newOperator);
		lblMutationLevel = new JLabel("Level: " + mutationLevel);
		
		oMutationNameFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		oPropertiesFont = new Font(Font.SANS_SERIF, Font.ITALIC, 10);
		
		oMutationNameDim = new Dimension(500, 20);
		oPropertiesDim = new Dimension(150,20);
		oRowDimension = new Dimension(600, 50);
		
		rowBgColor = bAltRow ? new Color(238, 238, 238) : new Color(255, 255, 255);
		
		chkCreateMutation.addActionListener(oTableListener);
		chkCreateMutation.addMouseListener(this);
		this.addMouseListener(this);
		
		this.bRowIsSelected = false;
		
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
		
		this.add(lblMutationName);
		this.add(chkCreateMutation);
		this.add(lblMutationType);
		this.add(lblMutationOps);
		this.add(lblMutationLevel);
		
		this.setBackground(rowBgColor);
		chkCreateMutation.setBackground(rowBgColor);
		

		
		this.setPreferredSize(oRowDimension);
		this.setMinimumSize(oRowDimension);
		this.setMaximumSize(oRowDimension);
		
		bIsHighlighted = false;
	}
	
	public boolean isSelected() {
		return this.chkCreateMutation.isSelected();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.bIsHighlighted) {
			this.setBackground(rowBgColor);
			this.bIsHighlighted = false;
		} else {
			this.setBackground(Color.BLUE);
			this.bIsHighlighted = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	@Override
	public void mouseExited(MouseEvent e) {		
		this.setBorder(BorderFactory.createEmptyBorder());		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//do nothing		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//do nothing		
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
