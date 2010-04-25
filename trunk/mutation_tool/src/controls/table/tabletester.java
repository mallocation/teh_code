package controls.table;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class tabletester extends JFrame {
	private MutationTable oTable;
	private MutationTableFilter oFilter;
	JPanel pnl;
	public tabletester() {
		pnl = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oFilter = new MutationTableFilter();
		//oFilter.setSize(oFilter.getMaximumSize());
		oTable = new MutationTable();
		oFilter.addMutationFilterListener(oTable);
		
		//this.setLayout(new GridLayout(0,1));
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.PAGE_AXIS));
			
		pnl.add(oFilter);
		JScrollPane oPane = new JScrollPane(oTable);
		oPane.setBorder(BorderFactory.createTitledBorder("Available Mutations"));
		oPane.setPreferredSize(new Dimension(565, 500));
		oPane.getVerticalScrollBar().setUnitIncrement(10);
		pnl.add(oPane);
		this.getContentPane().add(pnl);
		this.setVisible(true);
		
		setSize(580, 500);
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		new tabletester();
	}

}
