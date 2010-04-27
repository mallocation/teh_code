package mutations;

import java.awt.*;
import javax.swing.*;

public class MutationsSelectedRow extends JPanel {
	public String className;
	public String mutationCount;
	public boolean exported;
	public String imagePath;
	public ImageIcon image;
	public MutantCollection mutant;
	
	public MutationsSelectedRow(String name, MutantCollection mutants){
		this.className = name;
		this.exported = false;
		this.imagePath = "../images/ClassTree.png";
		this.mutant = mutants;
		//this.mutationCount = Integer.toString(mutants.getCollectionCount());
		
		JLabel cn = new JLabel(this.className);
		//JLabel mc = new JLabel(" (Mutation Count: " + this.mutationCount + ")");
		JLabel mc = new JLabel(" (Mutation Count: 1111)");
		JLabel im = new JLabel(new ImageIcon(this.getClass().getResource(this.imagePath)));
		
		this.add(im);
		this.add(cn);
		this.add(mc);
	}
	
	public String getClassName(){
		return this.className;
	}
	
	public String getMutationCount(){
		return this.mutationCount;
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
}
