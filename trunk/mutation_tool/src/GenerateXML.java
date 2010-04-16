import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class GenerateXML {

	private String xmlOutput;
	private DocumentBuilderFactory docFact;
	private DocumentBuilder docBuild;
	private Document xmlDoc;
	private Element mutation;
	
    public static void main (String args[]) {
        GenerateXML oXML = new GenerateXML();
        oXML.createXMLRoot();
      //could have included here...but what if I want to make many and want to loops!
        oXML.createXMLEntry("hierarchy level", "mutant Name", "old Op", "new Op");
        oXML.createXMLEntry("hierarchy level", "mutant Name", "old Op", "new Op");
        oXML.createXMLEntry("hierarchy level", "mutant Name", "old Op", "new Op");
        oXML.createXMLEntry("hierarchy level", "mutant Name", "old Op", "new Op");
        oXML.createXMLEntry("hierarchy level", "mutant Name", "old Op", "new Op");
        oXML.createXMLEntry("hierarchy level", "mutant Name", "old Op", "new Op");
        oXML.outputToFile("test.xml");
    }

    public void outputToFile(String outputFile){
    	FileOutputStream oOutput;
    	PrintStream oStream;
    	try{
    		
            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            
            //remove the xml blah blah from the top
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            //pretty pretty white spaces
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create String from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(xmlDoc);
            trans.transform(source, result);
            xmlOutput = sw.toString();

            //Output to file
            oOutput = new FileOutputStream(outputFile);
            oStream = new PrintStream(oOutput);
            oStream.println(xmlOutput);
            oStream.close();
    	} catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //create root node of the tree
    public void createXMLRoot(){
    	try{
    		//Create root element and add it to the tree
            mutation = xmlDoc.createElement("mutation");
            xmlDoc.appendChild(mutation);

    	} catch (Exception e) {
            System.out.println(e);
        }   	
    }
    
    //create remaining elements
    public void createXMLEntry(String level, String mutantName, String opOld, String opNew){
    	try{
    		//create child element of mutation, add attributes
            Element mutant = xmlDoc.createElement("mutant");
            mutant.setAttribute("level", level);
            mutant.setAttribute("name", mutantName);
            mutant.setAttribute("old_operator", opOld);
            mutant.setAttribute("new_operator", opNew);
            mutation.appendChild(mutant);
                        

    	} catch (Exception e) {
            System.out.println(e);
        }   
    	
    }
    
    
    //construct it 
    public GenerateXML() {
        try {
        	//DOM to make a blank document
        	docFact = DocumentBuilderFactory.newInstance();
        	docBuild = docFact.newDocumentBuilder();
            xmlDoc = docBuild.newDocument();

            

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}