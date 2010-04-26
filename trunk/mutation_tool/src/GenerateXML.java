import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


/**
 * The Class GenerateXML. Used to generate a XML Document
 * of a list of classes, or a list of mutants in a class,
 * or append classes/mutants to a file. 
 * @author teh_code
 */
public class GenerateXML {

	/** String to be output to xml file */
	private String xmlOutput;
	
	/**Factory API to create DOM object trees from XML documents.  */
	private DocumentBuilderFactory docFact;
	
	/** The doc build. */
	private DocumentBuilder docBuild;
	
	/** The Document containing list of mutations. */
	private Document mutationXMLDoc;
	
	/** The Document containing list of classes. */
	private Document classXMLDoc;
	
	/** The Document to append to. */
	private Document appendedXMLDoc;
	
	/** The mutation element. */
	private Element mutation;
	
	/** The classes element. */
	private Element classes;
	
    /**
     * Main method to test functionality.
     *
     * @param args the arguments
     */
    public static void main (String args[]) {
        GenerateXML oXML = new GenerateXML();
 
        //oXML.createClassesXMLRoot();
        oXML.createMutationsXMLRoot();
        //oXML.createClassesXMLEntry("/blah/blah", "1234");
        //oXML.createClassesXMLEntry("/fwe/easf", "86786");
        //oXML.createClassesXMLEntry("/asd/asdf", "54");
        //oXML.createClassesXMLEntry("/blah/fda", "615");
        //oXML.createClassesXMLEntry("/faw/afw", "6678");
        oXML.createMutationsXMLEntry("class", "asdaf", "ARITHMETIC", "asddff", "asdfsf");
        oXML.createMutationsXMLEntry("class", "asdaf", "ARITHMETIC", "asddff", "asdfsf");
        oXML.createMutationsXMLEntry("class", "asdaf", "ARITHMETIC", "asddff", "asdfsf");
        oXML.createMutationsXMLEntry("method", "asdaf", "ARITHMETIC", "asddff", "asdfsf");

     	//oXML.outputToFile("classes.xml", oXML.classXMLDoc);
     	//oXML.outputToFile("mutations.xml", oXML.mutationXMLDoc);
     	oXML.appendToXMLFile("mutations.xml", "mutation");

     	oXML.outputToFile("mutations.xml", oXML.appendedXMLDoc);
     	
     	
    }

    
    //construct it 
    /**
     * Instantiates the GenerateXML class.
     */
    public GenerateXML() {
        try {
        	//DOM to make a blank document
        	docFact = DocumentBuilderFactory.newInstance();
        	docBuild = docFact.newDocumentBuilder();
        	classXMLDoc = docBuild.newDocument();
        	mutationXMLDoc = docBuild.newDocument();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

	/**
     * Used to append elements to an existing xml file
     *
     * @param inputFileName the name of the input file
     * @param inputXMLType mutant or class to append to
     */
    public void appendToXMLFile(String inputFileName, String inputXMLType){
		try{
			File xmlFile = new File(inputFileName);
			appendedXMLDoc = docBuild.parse(xmlFile);
			NodeList listOfNodes = appendedXMLDoc.getElementsByTagName("inputXMLType");
			Element newElement;
			Node elementCopy; 
			int numberOfClasses = listOfNodes.getLength();
			for(int i = 0; i < numberOfClasses; i++){
				newElement = (Element)listOfNodes.item(i);
				elementCopy = appendedXMLDoc.importNode(newElement,true);
				appendedXMLDoc.getDocumentElement().appendChild(elementCopy);
			}
		} catch(FileNotFoundException e){
			System.out.println("File not found!");
			System.exit(0);
		}catch(SAXParseException e) {
			System.out.println("Error: "+e.getMessage()+" at line " + e.getLineNumber() + ", column " + e.getColumnNumber());
			System.exit(0);
		}catch (SAXException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
			
	}
    
    
    
    /**
     * Output to file.
     *
     * @param outputFile the name of the output file
     * @param outputDoc the name of the document to output (classes or mutations)
     */
    public void outputToFile(String outputFile, Document outputDoc){
    	FileOutputStream oOutput;
    	PrintStream oStream;
    	try{
    		            
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            
            //remove the xml specifications from the top of the document
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            //pretty pretty white spaces
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create String from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(outputDoc);
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
    

    /**
     * Creates the root of the mutations xml file.
     */
    public void createMutationsXMLRoot(){
    	try{        	 	
    		//Create root element and add it to the tree
    		mutation = mutationXMLDoc.createElement("mutations");
    		mutationXMLDoc.appendChild(mutation);
    	} catch (Exception e) {
            System.out.println(e);
        }   	
    }
    
    /**
     * Creates the root of the classes xml file.
     */
    public void createClassesXMLRoot(){
    	try{        	 	
    		classes = classXMLDoc.createElement("classes");
    		classXMLDoc.appendChild(classes);

    	} catch (Exception e) {
            System.out.println(e);
        }   	
    }
    /**
     * Creates the classes xml entry.
     *
     * @param classPath the class path
     * @param classID the class id
     */
    public void createClassesXMLEntry(String classPath, String classID){
    	try{
    		//create child element of mutation, add attributes
  
            Element Class = classXMLDoc.createElement("class");
            Class.setAttribute("path", classPath);
            classes.appendChild(Class);
            Element id = classXMLDoc.createElement("id");
            id.setTextContent(classID);
            Class.appendChild(id);
                        
    	} catch (Exception e) {
            System.out.println(e);
        }   
    	
    }
    

    /**
     * Creates the xml entry.
     *
     * @param level the level
     * @param mutantName the mutant name
     * @param mutationType the mutation type
     * @param opOld the op old
     * @param opNew the op new
     */
    public void createMutationsXMLEntry(String level, String mutantName, String mutationType, String opOld, String opNew){
    	try{
    		//create child element of mutation, add attributes
            Element mutant = mutationXMLDoc.createElement("mutant");
            mutant.setAttribute("level", level);
            mutant.setAttribute("name", mutantName);
            mutant.setAttribute("type", mutationType);
            mutant.setAttribute("oldOp", opOld);
            mutant.setAttribute("newOp", opNew);
            mutation.appendChild(mutant);
                        

    	} catch (Exception e) {
            System.out.println(e);
        }   
    	
    }
    
   
}