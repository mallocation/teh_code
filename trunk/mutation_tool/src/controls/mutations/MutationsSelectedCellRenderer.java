package controls.mutations;

import java.awt.*;
import javax.swing.*;

/**
 * MutationsSelectedCellRenderer renders a cell of the table that is selected
 * 
 * @author teh code
 *
 */
public class MutationsSelectedCellRenderer implements ListCellRenderer {
	public MutationsSelectedCellRenderer(){
	}
	
	/**
	 * getListCellRendererComponent gets the cell renderer component
	 * 
	 * @param list
	 * @param value
	 * @param index
	 * @param isSelected
	 * @param cellHasFocus
	 * @return row
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		MutationsSelectedRow row = (MutationsSelectedRow) value;
		if (isSelected) {
			row.setBackground(Color.LIGHT_GRAY);
			row.setForeground(Color.white);
		} else {
			row.setBackground(Color.white);
			row.setForeground(Color.black);
		}
		return row;
	}
}