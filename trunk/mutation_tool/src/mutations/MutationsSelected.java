package mutations;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.*;
import java.util.*;


public class MutationsSelected extends JPanel implements ActionListener {

	private MutationsSelectedRow rows[] = {
			new MutationsSelectedRow("ClassName1", "11", "../images/ClassTree.png"),
			new MutationsSelectedRow("ClassName2", "23", "../images/ClassTree.png"),
			new MutationsSelectedRow("ClassName3", "36", "../images/MethodTree.png")
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
		
	}
	
	public void actionPerformed(ActionEvent e){
		//TODO - implement
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Selected Mutations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MutationsSelected());
		frame.setSize(new Dimension(300,300));
		frame.setVisible(true);
	}
}