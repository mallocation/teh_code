import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;



public class ParseXML {

	private DocumentBuilderFactory docFact;
	private DocumentBuilder docBuild;
	private Document xmlDoc;
	private int numberOfMutants;
	private NodeList listOfMutants;
	
	//number of attributes for each mutant
	private static int numberOfAttributes = 5;
	//replace with the actual enum take and in an array list so not replaced
	private Element mutant;
	
	public static void main(String[] args) {
		new ParseXML();

	}

	public ParseXML(){
		
        try {
        	//DOM to make a blank document
        	docFact = DocumentBuilderFactory.newInstance();
        	docBuild = docFact.newDocumentBuilder();
            getXMLFile();
            getNumberOfMutants();
            //Redo with the fancy enum type Mutant
            for(int i = 0; i < numberOfMutants; i++){
            	getMutantAttributes(i);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void getXMLFile(){
		try{
			File xmlFile = new File("test.xml");
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
	
	public void getNumberOfMutants(){
		//list of nodes with element name of mutant
		listOfMutants = xmlDoc.getElementsByTagName("mutant");
		numberOfMutants = listOfMutants.getLength();
	}
	
	public void getMutantAttributes(int mutantNumber){
		
		mutant = (Element) listOfMutants.item(mutantNumber);
		NamedNodeMap mutantAtrributes = mutant.getAttributes();
		//Missing or too many attributes in xml file
		if(numberOfAttributes > mutantAtrributes.getLength()){
			System.out.println("Too many attributes for mutant "+mutantNumber);
			System.exit(0);
		}else if(numberOfAttributes < mutantAtrributes.getLength()){
			System.out.println("Not enough attributes for mutant "+mutantNumber);
			System.exit(0);
		}
		
		for(int i = 0; i < numberOfAttributes; i++){
			Attr mutantAttribute = (Attr)mutantAtrributes.item(i);
			
			//!!Wrong attribute in xml expand so accept only valid operators, names, level, types, etc
			if(mutantAttribute.getNodeValue() == ""){
				System.out.println("Missing attribute for: " +mutantAttribute.getNodeName());
				System.exit(0);
			}
			//PUT IT IN THE MUTANT ARRAY LIST!!
			System.out.println(mutantAttribute.getNodeName() + ": " + mutantAttribute.getNodeValue());
		}
		System.out.println();
	}
	
}
