package utilities;

import interfaces.IMutableObject;
import mutations.Mutant;
import mutations.MutantCollection;

import java.io.*;
import java.util.ArrayList;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

/**
 * The Class ParseXML used to parse a XML file.
 * @author teh_code
 */
public class ParseXML {

	private DocumentBuilderFactory docFact;
	private DocumentBuilder docBuild;
	private NodeList listOfMutants;
	private NodeList listOfClasses;
	private IMutableObject tempMutant;
	public  MutantCollection mutationsList;
	private Element mutant;
	private Element Class;
	private String pathOfClassFile;

	
	/**
	 * The main method. Used to test functionality
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		ParseXML oXMLParse = new ParseXML();
        //oXMLParse.getXMLFile(System.getProperty("user.dir") + "/persistentStorage/generated_XML/classes.xml");
        oXMLParse.getMutantAttributes(System.getProperty("user.dir") + "/persistentStorage/generated_XML/classes.xml");
        
        
        //oXMLParse.getMutantAttributes(oXMLParse.getMutationsFileName(System.getProperty("user.dir") + "/persistentStorage/generated_XML/classes.xml"));
        //oXMLParse.getMutantAttributes("/blah/blah");
        //GenerateXML oXML = new GenerateXML();
       // oXML.createMutationsXML(oXMLParse.mutationsList, "/blah/blah");
	}

	/**
	 * Instantiates a new ParseXML class.
	 */
	public ParseXML(){
        try {
        	//DOM to make a blank document
        	docFact = DocumentBuilderFactory.newInstance();
        	docBuild = docFact.newDocumentBuilder();
        	tempMutant = new Mutant();
        	this.mutationsList = new MutantCollection();
        	
        } catch (Exception e) {

            System.out.println(e);
        }
	}
	
	/**
	 * Opens xml file and create a Document from it.
	 *
	 * @param inputFileName the name of the input file with absolute path
	 * @return the XML Document
	 */
	public Document getXMLFile(String inputFileName){
		try{
			File xmlFile = new File(inputFileName);
			Document xmlDoc = docBuild.parse(xmlFile);
			return xmlDoc;
		} catch(FileNotFoundException e){
			System.out.println("XML File "+inputFileName+" for "+pathOfClassFile +" not found!");
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
		} catch (NullPointerException e){
			System.out.println("Classes.xml is empty");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the number of mutants in an xml file.
	 *
	 * @return the number of mutants
	 */
	public int getNumberOfMutants(Document xmlDoc){
		//list of nodes with element name of mutant
		listOfMutants = xmlDoc.getElementsByTagName("mutant");
		return listOfMutants.getLength();
	}

	/**
	 * Gets the number of classes in the xml file.
	 *
	 * @return the number of classes
	 */
	public int getNumberOfClasses(Document xmlDoc){
		//list of nodes with element name of mutant
		listOfClasses = xmlDoc.getElementsByTagName("class");
		return listOfClasses.getLength();
	}
	
	/**
	 * Gets the mutant attributes.
	 *
	 * @param classPath the class path of the class file
	 * @return the list of mutant attributes
	 */
	public MutantCollection getMutantAttributes(String classPath){
		int numberOfAttributes;
		String tempAttribute;
		String inputFileName = getMutationsFileName(classPath);
		//DEBUG
		System.out.println(classPath);
		Document xmlDoc = getXMLFile(inputFileName);

        for(int i = 0; i < getNumberOfMutants(xmlDoc); i++){
			mutant = (Element) listOfMutants.item(i);
			NamedNodeMap mutantAttributes = mutant.getAttributes();
        	numberOfAttributes = mutantAttributes.getLength();
			
			for(int j = 0; j < numberOfAttributes; j++){
				Attr mutantAttribute = (Attr)mutantAttributes.item(j);
				tempAttribute = mutantAttribute.getNodeName();
				
				if(tempAttribute.equals("level")){
					tempMutant.setMutantLevel(Mutant.stringToMutantLevel(mutantAttribute.getNodeValue()));
				}
				else if(tempAttribute.equals("name")){
					if(tempMutant.getMutantLevelAsString().equals("METHOD"))
						tempMutant.setMethodName(mutantAttribute.getNodeValue());
				}
				else if(tempAttribute.equals("type")){
					tempMutant.setMutantType(Mutant.stringToMutantType(mutantAttribute.getNodeValue()));
				}
				else if(tempAttribute.equals("oldOp")){
					tempMutant.setOldOperator(mutantAttribute.getNodeValue());
				}
				else if(tempAttribute.equals("newOp")){
					tempMutant.setNewOperator(mutantAttribute.getNodeValue());
				}
				else{
					System.out.println("Unkown attribute in file.");
					//System.exit(0);
				}
				//REMOVE
				System.out.println(mutantAttribute.getNodeName() + ": " + mutantAttribute.getNodeValue());
				
			}
			
			mutationsList.getMutants().add(tempMutant);
			System.out.println();
		}
		return mutationsList;
	}
	
	
	/**
	 * Gets the name of the xml file containing mutations.
	 *
	 * @param classPath the class path .class file is located in
	 * @return the name of the xml file 
	 */
	public String getMutationsFileName(String classPath){
		String id = null;
		setClassPath(classPath);
		Document xmlDoc = getXMLFile(System.getProperty("user.dir") + "/persistentStorage/generated_XML/classes.xml");
		System.out.println(getNumberOfClasses(xmlDoc));
        for(int i = 0; i < getNumberOfClasses(xmlDoc); i++){
        	Class = (Element) listOfClasses.item(i);
			NamedNodeMap classAtrributes = Class.getAttributes();
			//if(classAtrributes.getLength() != 2) {
			//	System.out.println("Class must have exactly two attributes.");
			//	System.exit(0);
			//}
			//else{
				Attr pathAttribute = (Attr)classAtrributes.item(1);
				if(pathAttribute.getNodeValue().equals(classPath)){
					Attr idAttribute = (Attr)classAtrributes.item(0);
					id = idAttribute.getNodeValue()+".xml";
				}
			//}
		}		
        System.out.println(id);
		return id;
	}
	
	/**
	 * Sets the path of the class file
	 *
	 * @param classPath the class path .class file is located in
	 */
	public void setClassPath(String classPath) {
		this.pathOfClassFile = classPath;
	}

	
}
