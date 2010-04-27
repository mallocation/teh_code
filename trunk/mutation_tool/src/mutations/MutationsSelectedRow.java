package mutations;

import interfaces.IMutableObject;

import java.awt.*;
import javax.swing.*;

import org.apache.bcel.classfile.JavaClass;

public class MutationsSelectedRow extends JPanel {
	public JavaClass oClass;
	public String mutationCount;
	public boolean exported;
	public String imagePath;
	public ImageIcon image;
	public MutantCollection mutant;
	
	public MutationsSelectedRow(JavaClass rClass){
		this.oClass = rClass;
		this.exported = false;
		this.imagePath = "../images/ClassTree.png";
		this.mutant = new MutantCollection();
		//this.mutationCount = Integer.toString(mutants.getCollectionCount());
		
		JLabel cn = new JLabel(rClass.toString());
		JLabel mc = new JLabel(" (Mutation Count: " + this.mutant.getCollectionCount() + ")");
		JLabel im = new JLabel(new ImageIcon(this.getClass().getResource(this.imagePath)));
		
		this.add(im);
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
	
	public ImageIcon getImage(){
		if (image == null){
			image = new ImageIcon(imagePath);
		}
		return image;
	}
	
	public void addMutableObj(IMutableObject mObject){
		this.mutant.addMutant(mObject);
	}
	
	public void removeMutableObj(IMutableObject mObject){
		this.mutant.removeMutant(mObject);
	}
}
