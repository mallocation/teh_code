import interfaces.IMutableObject;

import java.io.*;
import java.util.ArrayList;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;



public class ParseXML {

	private DocumentBuilderFactory docFact;
	private DocumentBuilder docBuild;
	private Document xmlDoc;
	private NodeList listOfMutants;
	private NodeList listOfClasses;
	public IMutableObject tempMutant;
	public ArrayList<IMutableObject> mutationsList;

	

	//replace with the actual enum take and in an array list so not replaced
	private Element mutant;

	
	public static void main(String[] args) {
		ParseXML oXMLParse = new ParseXML();
        oXMLParse.getXMLFile("test.xml");
        oXMLParse.getMutantAttributes();
        
	}

	public ParseXML(){
        try {
        	//DOM to make a blank document
        	docFact = DocumentBuilderFactory.newInstance();
        	docBuild = docFact.newDocumentBuilder();
    
        } catch (Exception e) {

            System.out.println(e);
        }
	}
	
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
	
	public int getNumberOfMutants(){
		//list of nodes with element name of mutant
		listOfMutants = xmlDoc.getElementsByTagName("mutant");
		System.out.println(listOfMutants.getLength());
		return listOfMutants.getLength();
	}
	//I need??
	public int getNumberOfClasses(){
		//list of nodes with element name of mutant
		listOfClasses = xmlDoc.getElementsByTagName("class");
		return listOfClasses.getLength();
	}
	
	public void getMutantAttributes(){
		int numberOfAttributes;
		String tempAttribute;

        //Redo with the fancy enum type Mutant
        for(int i = 0; i < getNumberOfMutants(); i++){
        	System.out.println(i);
			mutant = (Element) listOfMutants.item(i);
			NamedNodeMap mutantAtrributes = mutant.getAttributes();
        	numberOfAttributes = mutantAtrributes.getLength();
			
			for(int j = 0; j < numberOfAttributes; j++){
				Attr mutantAttribute = (Attr)mutantAtrributes.item(j);
				tempAttribute = mutantAttribute.getNodeName();
				
				if(tempAttribute.equals("level")){
					tempMutant.setMutantLevel(tempMutant.stringToMutantLevel(mutantAttribute.getNodeValue()));
					
				}
				else if(tempAttribute.equals("name")){
					tempMutant.setMethodName(mutantAttribute.getNodeValue());
				}
				else if(tempAttribute.equals("type")){
					tempMutant.setMutantType(tempMutant.stringToMutantType(mutantAttribute.getNodeValue()));
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
	}
	
}
