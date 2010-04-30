import javax.swing.SwingUtilities;
import utilities.ParseXML;


public class InterfaceSelection {
	
    public static void main (String args[])  {
    	
    	if(args.length == 0){
			SwingUtilities.invokeLater(new Runnable() {			
				public void run() {
					new MutationTool();
				}			
			});	
    	}
    	
    	else {
    		ParseXML commandLineInterface = new ParseXML();
    		for(int i=0; i < args.length; i++){
    			commandLineInterface.getMutationsFromCommandLine(args[i]);
    		}
    		
    	}
    	
    	
    }
    	

}
