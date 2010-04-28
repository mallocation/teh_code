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


public class MutableNodeRenderer extends JLabel implements TreeCellRenderer {
	private static final String sImgClassTree = "../../images/ClassTree.png";
	private static final String sImgMethodTree = "../../images/MethodTree.png";
	private ImageIcon imgClass, imgMethod;
	private DefaultTreeCellRenderer oDefaultRenderer;
	
	public MutableNodeRenderer () {
		imgClass = new ImageIcon(getClass().getResource(sImgClassTree));
		imgMethod = new ImageIcon(getClass().getResource(sImgMethodTree));
		oDefaultRenderer = new DefaultTreeCellRenderer();
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
			return this;
//		} else {
//			return oDefaultRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
//		}
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
