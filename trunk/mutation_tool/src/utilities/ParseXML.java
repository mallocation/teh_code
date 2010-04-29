package utilities;

import interfaces.IMutableObject;
import mutations.Mutant;
import mutations.MutantCollection;
import mutations.Mutator;

import java.io.*;
import java.util.ArrayList;
import org.w3c.dom.*;
import org.xml.sax.*;

import org.apache.bcel.classfile.JavaClass;

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
	private Element eMutant;
	private Element eClass;


	
	/**
	 * The main method. Used to test functionality
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		ParseXML oXMLParse = new ParseXML();
	//Test parsing of xml document input classPath attribute for classes.xml, returning the Mutant Collection from the corresponding id xml file
       //oXMLParse.getMutantAttributes(oXMLParse.getPersistentMutationsFileName(System.getProperty("user.dir") + "/persistentStorage/generated_XML/classes.xml" ,System.getProperty("user.dir") + "\\bin\\GenerateXML.class"),System.getProperty("user.dir") + "\\bin\\GenerateXML.class");
       oXMLParse.getMutationsFromCommandLine((System.getProperty("user.dir")+"/persistentStorage/generated_XML/mutants/265633432655267.xml"));
       //getPersistentMutationsFileName(System.getProperty("user.dir") + "/persistentStorage/generated_XML/classes.xml" ,System.getProperty("user.dir") + "\\bin\\GenerateXML.class")
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
			System.err.println(e);
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
			System.out.println(inputFileName+" is empty");
			e.printStackTrace();
		}
		System.out.println("NULL");
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
	 * @param locationOfXML location of xml file
	 * @param classPathToSearch class path of file to mutate
	 * @return the list of mutant attributes
	 */
	public MutantCollection getMutantAttributes(String locationOfXML, String classPath){
		int numberOfAttributes;
		String tempAttribute;
		Document xmlDoc;
		MutantCollection mutationsList = new MutantCollection();
		IMutableObject tempMutant = new Mutant(); 
		xmlDoc = getXMLFile(locationOfXML);
		
        for(int i = 0; i < getNumberOfMutants(xmlDoc); i++){
        	Boolean valid = true;
			eMutant = (Element) listOfMutants.item(i);
			NamedNodeMap mutantAttributes = eMutant.getAttributes();
        	numberOfAttributes = mutantAttributes.getLength();
    		if(ClassLoader.isClassFile(classPath))
    			tempMutant.setMutableClass(ClassLoader.loadClassFromPath(classPath));
    		else
    			System.out.println("Invalid Class "+classPath);
			for(int j = 0; j < numberOfAttributes; j++){
				Attr mutantAttribute = (Attr)mutantAttributes.item(j);
				tempAttribute = mutantAttribute.getNodeName();
				
				if(tempAttribute.equals("level")){
					tempMutant.setMutantLevel(Mutant.stringToMutantLevel(mutantAttribute.getNodeValue()));
				}
				else if(tempAttribute.equals("name")){
					if(tempMutant.getMutantLevel().equals(IMutableObject.eMutantLevel.METHOD))
						for(int k = 0; k < ClassLoader.loadClassFromPath(classPath).getMethods().length; k++) {
							if(mutantAttribute.getNodeValue().equalsIgnoreCase(ClassLoader.loadClassFromPath(classPath).getMethods()[k].getName())){
								valid = true;
								break;
							}else
								valid = false;
						}
					
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
				//System.out.println(mutantAttribute.getNodeName() + ": " + mutantAttribute.getNodeValue());
				
			}
			if(valid){
				mutationsList.getMutants().add(tempMutant);
			}
			else{
				System.out.println("Mutation skipped. Method name "+tempMutant.getMethodName()+" not valid for "+tempMutant.getMutableClass().getClassName()+".class.");
			}
			valid = true;

		}
		return mutationsList;
	}
	
	
	/**
	 * Gets the name of the xml file containing mutations.
	 *
	 * @param classPath the class path .class file is located in
	 * @param clathPathToSearch attribute to search for
	 * @return the name of the xml file 
	 */
	public String getPersistentMutationsFileName(String classPath, String classPathToSearch){
		String id = null;
		Document xmlDoc = getXMLFile(classPath);
        for(int i = 0; i < getNumberOfClasses(xmlDoc); i++){
        	eClass = (Element) listOfClasses.item(i);
			NamedNodeMap classAtrributes = eClass.getAttributes();
			if(classAtrributes.getLength() != 2) {
				System.out.println("Class must have exactly two attributes.");
				System.exit(0);
			}
			else{
				Attr pathAttribute = (Attr)classAtrributes.item(1);
				if(pathAttribute.getNodeValue().equals(classPathToSearch)){
					Attr idAttribute = (Attr)classAtrributes.item(0);
					id = System.getProperty("user.dir") + "/persistentStorage/generated_XML/mutants/" + idAttribute.getNodeValue()+".xml";
				}
			}
		}
        
		return id;
	}
	

	public void getMutationsFromCommandLine(String fileName){
		Document commandLineXMLDoc = getXMLFile(fileName);
		Element mutations = commandLineXMLDoc.getDocumentElement();
		String classPath = mutations.getAttribute("classPath");
		if(classPath.equals("")){
			System.out.println("Must include classPath attribute for mutations element.");
			System.exit(0);
		}
		MutantCollection mutationsList = getMutantAttributes(fileName,classPath);
		Mutator.generate(mutationsList);
	
	}
	

	
}
