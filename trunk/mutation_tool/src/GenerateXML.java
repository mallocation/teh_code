import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class GenerateXML {

	private String xmlOutput;
	private DocumentBuilderFactory docFact;
	private DocumentBuilder docBuild;
	private Document mutationXMLDoc;
	private Document classXMLDoc;
	private Document appendedXMLDoc;
	private Element mutation;
	private Element classes;
	
    public static void main (String args[]) {
        GenerateXML oXML = new GenerateXML();
 
        oXML.createXMLRoot("", "classes");
     	oXML.createClassesXMLEntry("/a/b", "1233");
     	oXML.createClassesXMLEntry("classPath", "classID");
     	//oXML.outputToFile("test.xml", oXML.classXMLDoc);
     	oXML.appendToXMLFile("test.xml");

     	oXML.outputToFile("test.xml", oXML.appendedXMLDoc);
     	
     	
    }

    
    //construct it 
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
    
    //add contents of XML file to current XML file
	public void appendToXMLFile(String inputFileName){
		try{
			File xmlFile = new File(inputFileName);
			appendedXMLDoc = docBuild.parse(xmlFile);
			NodeList listOfClasses = appendedXMLDoc.getElementsByTagName("class");
			Element newElement;
			Node elementCopy; 
			int numberOfClasses = listOfClasses.getLength();
			for(int i = 0; i < numberOfClasses; i++){
				newElement = (Element)listOfClasses.item(i);
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
    
    
    
    public void outputToFile(String outputFile, Document outputDoc){
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
    
    //create root node of the tree
    public void createXMLRoot(String filePath, String outputType){
    	try{        	 	
     
    		if(outputType.equals("mutations")){
    			//Create root element and add it to the tree
    			mutation = mutationXMLDoc.createElement(outputType);
    			mutationXMLDoc.appendChild(mutation);
    			if(!filePath.equals("")){
    				mutation.setAttribute("path", filePath);
    			}
    		}else if (outputType.equals("classes")){
    			classes = classXMLDoc.createElement(outputType);
    			classXMLDoc.appendChild(classes);
    		}else
    			System.out.println(outputType);

    	} catch (Exception e) {
            System.out.println(e);
        }   	
    }
    
    public void createClassesXMLEntry(String classPath, String classID){
    	try{
    		//create child element of mutation, add attributes
  
            Element Class = classXMLDoc.createElement("class");
            classes.appendChild(Class);
            Element path = classXMLDoc.createElement("path");
            path.setTextContent(classPath);
            Class.appendChild(path);
            Element id = classXMLDoc.createElement("id");
            id.setTextContent(classID);
            Class.appendChild(id);
                        
    	} catch (Exception e) {
            System.out.println(e);
        }   
    	
    }
    
    //create remaining elements
    public void createXMLEntry(String level, String mutantName, String mutationType, String opOld, String opNew){
    	try{
    		//create child element of mutation, add attributes
            Element mutant = mutationXMLDoc.createElement("mutant");
            mutant.setAttribute("level", level);
            mutant.setAttribute("name", mutantName);
            mutant.setAttribute("type", mutationType);
            mutant.setAttribute("old_operator", opOld);
            mutant.setAttribute("new_operator", opNew);
            mutation.appendChild(mutant);
                        

    	} catch (Exception e) {
            System.out.println(e);
        }   
    	
    }
    
   
}