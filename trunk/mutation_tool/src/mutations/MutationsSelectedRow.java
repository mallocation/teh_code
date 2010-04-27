package mutations;

import interfaces.IMutableObject;

import java.awt.*;
import javax.swing.*;

import org.apache.bcel.classfile.JavaClass;

public class MutationsSelectedRow extends JPanel {
	private JavaClass oClass;
	private String mutationCount;
	private boolean exported;
	private ImageIcon imgExported, imgNotExported;// String imagePath;
	private ImageIcon image;
	private MutantCollection mutant;
	private JLabel cn, mc, im;
	
	private static final String pathImgExported = "../images/green_dot.png";
	private static final String pathImgNotExported = "../images/yellow_dot.png";
	
	public MutationsSelectedRow(JavaClass rClass, boolean isExported){
		this.oClass = rClass;
		this.exported = isExported;
		imgExported = new ImageIcon(getClass().getResource(pathImgExported));
		imgNotExported = new ImageIcon(getClass().getResource(pathImgNotExported));

		this.mutant = new MutantCollection();
		
		cn = new JLabel(rClass.getClassName());
		if (isExported) {
			cn.setIcon(imgExported);
		} else {
			cn.setIcon(imgNotExported);
		}
		//cn.setIcon(image);
		
		mc = new JLabel(" (Mutation Count: " + this.mutant.getCollectionCount() + ")");
		
		//im = new JLabel(new ImageIcon(this.getClass().getResource(this.imagePath)));
		
		//this.add(im);
		this.add(cn);
		this.add(mc);
	}
	
	public JavaClass getJavaClass(){
		return this.oClass;
	}
	
	public String getMutationCount(){
		return this.mutationCount;
	}
	
	public MutantCollection getMutationCollection(){
		return this.mutant;
	}
	
	public boolean isExported(){
		return this.exported;
	}
	
	public void addMutableObj(IMutableObject mObject){
		this.mutant.addMutant(mObject);
		this.updateMutationCount();		
	}
	
	public void removeMutableObj(IMutableObject mObject){
		this.mutant.removeMutant(mObject);
		this.updateMutationCount();
	}
	
	private void updateMutationCount() {
		this.mc.setText(" (Mutation Count: " + this.mutant.getCollectionCount() + ")");
		this.setExported(false);
		this.repaint();
	}
	
	public void setExported(boolean bExported) {
		this.exported = bExported;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if (exported) {
					cn.setIcon(imgExported);
				} else {
					cn.setIcon(imgNotExported);
				}
			}
		});
		
//		if (bExported) {
//			cn.setIcon(imgExported);
//		} else {
//			cn.setIcon(imgNotExported);
//		}
//		this.paintImmediately(cn.getVisibleRect());
	}
}
