package mutations;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interfaces.IMutableObject;

import org.apache.bcel.classfile.JavaClass;

public class MutationsSelectedRow extends JPanel {
	private JavaClass oClass;
	private boolean bExported;
	private ImageIcon imgExported, imgNotExported;
	private MutantCollection colClassMutants;
	private JLabel lblClass, lblMutationCount;
	
	private final String pathImgExported = "../images/green_dot.png";
	private final String pathImgNotExported = "../images/yellow_dot.png";
	
	public MutationsSelectedRow(JavaClass oClass, boolean isExported){
		this.oClass = oClass;
		this.bExported = isExported;
		
		this.imgExported = new ImageIcon(getClass().getResource(pathImgExported));
		this.imgNotExported = new ImageIcon(getClass().getResource(pathImgNotExported));

		this.colClassMutants = new MutantCollection();
		
		this.lblClass = new JLabel(oClass.getClassName());
		
		this.bExported = isExported;
		if (bExported) {
			lblClass.setIcon(imgExported);
		} else {
			lblClass.setIcon(imgNotExported);
		}
		
		lblMutationCount = new JLabel("Mutation Count: " + this.colClassMutants.getCollectionCount());
		
		this.add(lblClass);
		this.add(lblMutationCount);
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
		this.repaint();
	}
	
	public void setExported(boolean bExported) {
		this.bExported = bExported;
		if (isExported()) {
			lblClass.setIcon(imgExported);
		} else {
			lblClass.setIcon(imgNotExported);
		}
		updateMutationCount();
//		lblClass.repaint();
//		this.repaint();
	}
}
