package mutations;

import java.awt.*;
import javax.swing.*;

public class MutationsSelectedCellRenderer implements ListCellRenderer {
	public MutationsSelectedCellRenderer(){
	}
	
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