package controls.mutations;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import interfaces.IMutableObject;

import mutations.MutantCollection;

import org.apache.bcel.classfile.JavaClass;

public class MutationsSelectedRow extends JPanel {
	private JavaClass oClass;
	private boolean bExported;
	private ImageIcon imgExported, imgNotExported, imgWorking;
	private MutantCollection colClassMutants;
	private JLabel lblClass, lblMutationCount;
	private JProgressBar progGenerateBar;
	
	private final String pathImgExported = "../images/green_dot.png";
	private final String pathImgNotExported = "../images/yellow_dot.png";
	private final String pathImgWorking = "../images/TableLoader2.gif";
	
	public MutationsSelectedRow(JavaClass oClass, boolean isExported){
		this.oClass = oClass;
		this.bExported = isExported;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.imgExported = new ImageIcon(getClass().getResource(pathImgExported));
		this.imgNotExported = new ImageIcon(getClass().getResource(pathImgNotExported));
		this.imgWorking = new ImageIcon(getClass().getResource(pathImgWorking));

		this.colClassMutants = new MutantCollection();
		
		this.lblClass = new JLabel(oClass.getClassName());
		
		setExported(isExported);
		
		lblMutationCount = new JLabel("Mutation Count: " + this.colClassMutants.getCollectionCount());
		lblMutationCount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		
		progGenerateBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		progGenerateBar.setStringPainted(true);
		progGenerateBar.setPreferredSize(new Dimension(125, 15));
		progGenerateBar.setMaximumSize(new Dimension(125, 15));
				
		this.add(lblClass);
		this.add(lblMutationCount);
		//this.add(progGenerateBar);
	}
	
	public JavaClass getJavaClass(){
		return this.oClass;
	}
	
	public int getMutationCount(){
		return this.colClassMutants.getCollectionCount();
	}
	
	public MutantCollection getMutationCollection(){
		return this.colClassMutants;
	}
	
	public boolean isExported(){
		return this.bExported;
	}
	
	public void addMutableObj(IMutableObject mObject){
		this.colClassMutants.addMutant(mObject);
		this.updateMutationCount();		
	}
	
	public void removeMutableObj(IMutableObject mObject){
		this.colClassMutants.removeMutant(mObject);
		this.updateMutationCount();
	}
	
	private void updateMutationCount() {
		this.lblMutationCount.setText("Mutation Count: " + this.colClassMutants.getCollectionCount());
		this.setExported(false);
	}
	
	public void setWorking() {
		lblClass.setIcon(imgWorking);
		lblClass.repaint();
		this.repaint();
	}
	
	public void setExported(boolean bExported) {
		this.bExported = bExported;
		if (isExported()) {
			lblClass.setIcon(imgExported);
		} else {
			lblClass.setIcon(imgNotExported);
		}
		lblClass.repaint();
	}
}
