import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;

import teh_code.utilities.ParseXML;


public class MutationTool {
	
    public static void main (String args[])  {
    	
    	if(args.length == 0 && !GraphicsEnvironment.isHeadless()){
			SwingUtilities.invokeLater(new Runnable() {			
				public void run() {
					new teh_code.gui.MutationTool();
				}			
			});	
    	}	else {
    		ParseXML commandLineInterface = new ParseXML();
    		
    		if (args.length != 0) {
    			for(int i=0; i < args.length; i++){
    				commandLineInterface.getMutationsFromCommandLine(args[i]);  			
    			}
    		} else {
    			//print usage
    			System.out.println("\n\nUsage: MutationTool file1 file2 file3");
    			System.out.println("where file1, file2, etc are compatible mutation scripting xml files\nSee manual for details.");    			
    		}
    	}    	
    }
}
