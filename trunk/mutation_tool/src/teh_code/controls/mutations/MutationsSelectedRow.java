package teh_code.controls.mutations;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;



import org.apache.bcel.classfile.JavaClass;

import teh_code.interfaces.IMutableObject;
import teh_code.mutations.MutantCollection;

/**
 * MutationsSelectedRow performs tasks necessary in the selected mutations panel
 * 
 * @author Teh Code
 *
 */
public class MutationsSelectedRow extends JPanel {
	private JavaClass oClass;
	private boolean bExported;
	private ImageIcon imgExported, imgNotExported;
	private MutantCollection colClassMutants;
	private JLabel lblClass, lblMutationCount;
	private JProgressBar progGenerateBar;
	
	private final String pathImgExported = "../../images/green_dot.png";
	private final String pathImgNotExported = "../../images/yellow_dot.png";
	
	/**
	 * The constructor for the class MutationsSelectedRow
	 * 
	 * @param oClass
	 * @param isExported
	 */
	public MutationsSelectedRow(JavaClass oClass, boolean isExported){
		this.oClass = oClass;
		this.bExported = isExported;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.imgExported = new ImageIcon(getClass().getResource(pathImgExported));
		this.imgNotExported = new ImageIcon(getClass().getResource(pathImgNotExported));

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
	
	/**
	 * Returns JavaClass
	 */
	public JavaClass getJavaClass(){
		return this.oClass;
	}
	
	/**
	 * Returns mutation count
	 */
	public int getMutationCount(){
		return this.colClassMutants.getCollectionCount();
	}
	
	/**
	 * Returns mutation collection
	 */
	public MutantCollection getMutationCollection(){
		return this.colClassMutants;
	}
	
	/**
	 * Returns true if exported
	 */
	public boolean isExported(){
		return this.bExported;
	}
	
	/**
	 * adds a mutable object and updates the mutation count
	 * @param mObject
	 */
	public void addMutableObj(IMutableObject mObject){
		this.colClassMutants.addMutant(mObject);
		this.updateMutationCount();		
	}
	
	/**
	 * removes a mutable object and updates the mutation count
	 * @param mObject
	 */
	public void removeMutableObj(IMutableObject mObject){
		this.colClassMutants.removeMutant(mObject);
		this.updateMutationCount();
	}
	
	/**
	 * function to update the mutation count
	 */
	private void updateMutationCount() {
		this.lblMutationCount.setText("Mutation Count: " + this.colClassMutants.getCollectionCount());
		this.setExported(false);
	}
	
	/**
	 * Sets exported
	 * @param bExported
	 */
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
