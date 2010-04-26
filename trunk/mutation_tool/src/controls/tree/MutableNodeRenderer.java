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


public class MutableNodeRenderer extends DefaultTreeCellRenderer {
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
			return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);			
	}

//	private JLabel oNodeLabel;
//	private Color bgNotSelectedColor;
//	private Color bgSelectedColor;
//	private DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();
//	private Icon imgClassIcon, imgMethodIcon;
//	public MutableNodeRenderer() {
//		oNodeLabel = new JLabel();
//		oNodeLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 5));
//		oNodeLabel.setOpaque(true);
//		bgNotSelectedColor = Color.WHITE;
//		bgSelectedColor = new Color(184, 207, 229);
//		URL oClassIconURL = getClass().getResource("../images/ClassTree.png");
//		URL oMethodIconURL = getClass().getResource("../images/MethodTree.png");
//		imgClassIcon = new ImageIcon(oClassIconURL);
//		imgMethodIcon = new ImageIcon(oMethodIconURL);
//	}
//	
//	@Override
//	public Component getTreeCellRendererComponent(JTree tree, Object value,
//			boolean selected, boolean expanded, boolean leaf, int row,
//			boolean hasFocus) {
//		Component renderedComponent = null;
//		if (value instanceof MutableNode) {
//			MutableNode oNode = (MutableNode)value;
//			if (oNode.getMutableMethod() != null) {
//				oNodeLabel.setText(oNode.getMutableMethod().getName());
//				oNodeLabel.setIcon(imgMethodIcon);
//			} else {
//				oNodeLabel.setText(oNode.getMutableClass().getClassName());
//				oNodeLabel.setIcon(imgClassIcon);
//			}
//			if (selected) {
//				oNodeLabel.setBackground(bgSelectedColor);
//			} else {
//				oNodeLabel.setBackground(bgNotSelectedColor);
//			}
//			renderedComponent = oNodeLabel;
//		}
//		if (renderedComponent == null) {
//			renderedComponent = defaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
//		}
//		return renderedComponent;
//	}
	
}
