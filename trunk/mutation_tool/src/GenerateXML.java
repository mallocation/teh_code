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
	
	
    public static void main (String args[]) {
        GenerateXML oXML = new GenerateXML();
        oXML.createXML();
        oXML.createOutput("test.xml");
    }

    public void createOutput(String outputFile){
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
    public void createXML(){
    	try{
    		//Create root element and add it to the tree
            Element root = xmlDoc.createElement("mutation");
            xmlDoc.appendChild(root);
            //could have included here...but what if I want to make many and want to loops!
            createMutantEntry(root, "hierarchy level", "mutant Name", "old Op", "new Op");

    	} catch (Exception e) {
            System.out.println(e);
        }   	
    }
    
    //create remaining elements
    public void createMutantEntry(Element parent, String level, String mutantName, String opOld, String opNew){
    	try{
    		//create child element, add an attribute, and add to root
            Element mutant = xmlDoc.createElement("mutant");
            mutant.setAttribute("level", level);
            parent.appendChild(mutant);
            
            Element name = xmlDoc.createElement("name");
            mutant.appendChild(name);
            Text nameText = xmlDoc.createTextNode(mutantName);
            name.appendChild(nameText);
            
            Element oldOperator = xmlDoc.createElement("old_operator");
            mutant.appendChild(oldOperator);
            Text oldOptext = xmlDoc.createTextNode(opOld);
            oldOperator.appendChild(oldOptext);
            
            Element newOperator = xmlDoc.createElement("new_operator");
            mutant.appendChild(newOperator);
            Text newOpText = xmlDoc.createTextNode(opNew);
            newOperator.appendChild(newOpText);
            

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