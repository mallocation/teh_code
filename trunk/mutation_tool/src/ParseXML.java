import interfaces.IMutableObject;
import mutations.Mutant;
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
	private Document xmlDoc;
	private NodeList listOfMutants;
	private NodeList listOfClasses;
	private IMutableObject tempMutant;
	public ArrayList<IMutableObject> mutationsList;
	private Element mutant;

	
	/**
	 * The main method. Used to test functionality
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		ParseXML oXMLParse = new ParseXML();
        //oXMLParse.getXMLFile("mutations.xml");
        oXMLParse.getMutantAttributes("mutations.xml");
        
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
        	this.mutationsList = new ArrayList<IMutableObject>();
        } catch (Exception e) {

            System.out.println(e);
        }
	}
	
	/**
	 * Opens xml file and create a Document from it.
	 *
	 * @param inputFileName the name of the input file
	 * @return the XML Document
	 */
	public void getXMLFile(String inputFileName){
		try{
			File xmlFile = new File(inputFileName);
			xmlDoc = docBuild.parse(xmlFile);
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
	 * Gets the number of mutants in an xml file.
	 *
	 * @return the number of mutants
	 */
	public int getNumberOfMutants(){
		//list of nodes with element name of mutant
		listOfMutants = xmlDoc.getElementsByTagName("mutant");
		return listOfMutants.getLength();
	}
	//I need??
	/**
	 * Gets the number of classes in the xml file.
	 *
	 * @return the number of classes
	 */
	public int getNumberOfClasses(){
		//list of nodes with element name of mutant
		listOfClasses = xmlDoc.getElementsByTagName("class");
		return listOfClasses.getLength();
	}
	
	/**
	 * Gets the mutant attributes.
	 *
	 * @param inputFileName the name of the input file
	 * @return the list ofmutant attributes
	 */
	public ArrayList<IMutableObject> getMutantAttributes(String inputFileName){
		int numberOfAttributes;
		String tempAttribute;
		getXMLFile(inputFileName);
        //Redo with the fancy enum type Mutant
        for(int i = 0; i < getNumberOfMutants(); i++){
			mutant = (Element) listOfMutants.item(i);
			NamedNodeMap mutantAtrributes = mutant.getAttributes();
        	numberOfAttributes = mutantAtrributes.getLength();
			
			for(int j = 0; j < numberOfAttributes; j++){
				Attr mutantAttribute = (Attr)mutantAtrributes.item(j);
				tempAttribute = mutantAttribute.getNodeName();
				
				if(tempAttribute.equals("level")){
					tempMutant.setMutantLevel(Mutant.stringToMutantLevel(mutantAttribute.getNodeValue()));
					
				}
				else if(tempAttribute.equals("name")){
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
					System.out.println("Unkown attribute in file");
					System.exit(0);
				}
				//PUT IT IN THE MUTANT ARRAY LIST!!
				System.out.println(mutantAttribute.getNodeName() + ": " + mutantAttribute.getNodeValue());
				
			}
			
			mutationsList.add(tempMutant);
			System.out.println();
		}
		return mutationsList;
	}
	
}
