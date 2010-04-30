package controls.table;

import interfaces.IMutableObject;
import interfaces.IMutationRowActor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import mutations.Mutator;

/**
 * PropertiesPanel displays properties about a row in the mutation table
 * that is "clicked" in a properties panel.
 * 
 * @author teh code
 *
 */
public class PropertiesPanel extends JPanel implements IMutationRowActor{
	
	public Dimension propertiesPanelDimension;
	public JTextArea propertiesTextArea;
	public String mutationCountText;
	
	/**
	 * Constructor for the PropertiesPanel class
	 */
	public PropertiesPanel(){
		propertiesPanelDimension = new Dimension(554,120);
		this.setPreferredSize(propertiesPanelDimension);
		this.setMaximumSize(propertiesPanelDimension);
		this.setMinimumSize(propertiesPanelDimension);
		this.setBorder(BorderFactory.createTitledBorder("Properties Panel"));
		propertiesTextArea = new JTextArea(10,45);
		propertiesTextArea.setText("Mutation Count: \n");
		propertiesTextArea.append("Mutant Level: \n");
		propertiesTextArea.append("Method Name: \n");
		propertiesTextArea.append("Mutant Type: \n");
		propertiesTextArea.append("Operator Change: ");
		propertiesTextArea.setOpaque(false);
		propertiesTextArea.setEditable(false);
		this.add(propertiesTextArea);
	}

	@Override
	public void updatePropertiesPanel(IMutableObject oMutant) {
		// TODO Auto-generated method stub
		mutationCountText = Integer.toString(Mutator.getMutationCount(oMutant));
		propertiesTextArea.setText("Mutation Count: " + mutationCountText + "\n");
		propertiesTextArea.append("Mutant Level: " + oMutant.getMutantLevelAsString() + "\n");
		if(oMutant.getMethodName() == null){
			oMutant.setMethodName("N/A");
		}
		propertiesTextArea.append("Method Name: " + oMutant.getMethodName() + "\n");
		propertiesTextArea.append("Mutant Type: " + oMutant.getMutantTypeAsString() + "\n");
		propertiesTextArea.append("Operator Change: " + oMutant.getOldOperator() + " to " + oMutant.getNewOperator());
		
	}
}
