package controls.table;

import interfaces.IMutableObject;
import interfaces.IMutationRowActor;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PropertiesPanel extends JPanel implements IMutationRowActor{
	
	public Dimension propertiesPanelDimension;
	
	public PropertiesPanel(){
		propertiesPanelDimension = new Dimension(554,200);
		this.setPreferredSize(propertiesPanelDimension);
		this.setMaximumSize(propertiesPanelDimension);
		this.setMinimumSize(propertiesPanelDimension);
		this.setBorder(BorderFactory.createTitledBorder("Properties Panel"));
	}

	@Override
	public void updatePropertiesPanel(IMutableObject oMutant) {
		// TODO Auto-generated method stub
		System.out.println("aldkfjlksdf");
		
	}
}
