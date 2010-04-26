package mutations;

import java.awt.*;
import javax.swing.*;

public class MutationsSelectedRow extends JPanel {
	private String className;
	private String mutationCount;
	private boolean exported;
	private String imagePath;
	private ImageIcon image;
	
	public MutationsSelectedRow(String name, String count, String imagePath1){
		this.className = name;
		this.mutationCount = count;
		this.exported = false;
		this.imagePath = imagePath1;
		
		JLabel cn = new JLabel(this.className);
		JLabel mc = new JLabel(" (Mutation Count: " + this.mutationCount + ")");
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
