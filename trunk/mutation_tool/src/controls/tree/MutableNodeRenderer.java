package controls.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.color.ColorSpace;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * MutableNodeRenderer renders a node of the loaded class tree
 * 
 * @author teh code
 *
 */
public class MutableNodeRenderer extends JLabel implements TreeCellRenderer {
	private static final String sImgClassTree = "../../images/ClassTree.png";
	private static final String sImgMethodTree = "../../images/MethodTree.png";
	private static final Color colorSelected = Color.LIGHT_GRAY;
	private static final Color colorNotSelected = Color.WHITE;
	
	private ImageIcon imgClass, imgMethod;
	
	/**
	 * Constructor for MutableNodeRenderer class
	 */
	public MutableNodeRenderer () {
		imgClass = new ImageIcon(getClass().getResource(sImgClassTree));
		imgMethod = new ImageIcon(getClass().getResource(sImgMethodTree));
	}
	
	
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			MutableNode oNode = (MutableNode)value;
			if (oNode.getMutableMethod() != null) {
				this.setText(oNode.getMutableMethod().getName());
				this.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
				this.setIcon(imgMethod);
			} else if (oNode.getMutableClass() != null) {
				this.setText(oNode.getMutableClass().getClassName());
				this.setIcon(imgClass);
			}
			this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
			if (selected)
				this.setBackground(colorSelected);
			else
				this.setBackground(colorNotSelected);
			this.setOpaque(true);
			this.repaint();
			return this;
			
	}
}
