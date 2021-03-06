package teh_code.utilities;
import java.io.*;
import teh_code.interfaces.IMutableObject;
import teh_code.mutations.Mutant;
import teh_code.mutations.MutantCollection;
import teh_code.utilities.Settings;

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

	private String xmlOutput;
	private DocumentBuilderFactory docFact;
	private DocumentBuilder docBuild;
	private Document mutationXMLDoc;
	private Document classXMLDoc;
	private Document appendedXMLDoc;
	private Element mutation;
	private Element classes;
	private String idFileName;
     
    /**
     * Instantiates the GenerateXML class.
     */
    public GenerateXML() {
        try {
        	//DOM to make a blank document
        	docFact = DocumentBuilderFactory.newInstance();
        	docBuild = docFact.newDocumentBuilder();
        	

        } catch (Exception e) {
            System.out.println(e);
        }
    }    

	/**
     * Used to append elements to an existing xml file
     *
     * @param inputFileName the name of the input file
	 * @return append was successful or not
     */
    public Boolean appendToClassXMLFile(String inputFileName, String classPath){
		try{

			Element Class = classXMLDoc.createElement("class");
			File xmlFile = new File(inputFileName);
			String tempPath = "", tempID = "";
			appendedXMLDoc = docBuild.parse(xmlFile);
			for(int i = 0; i < appendedXMLDoc.getElementsByTagName("class").getLength(); i++){
				Class = (Element) appendedXMLDoc.getElementsByTagName("class").item(i);
				NamedNodeMap classAttributes = Class.getAttributes();

					Attr classAttribute = (Attr)classAttributes.item(1);
					if(classAttribute.getNodeName().equals("path")){
						tempPath = classAttribute.getNodeValue();
						if(classPath.equals(tempPath)){
							idFileName=classAttributes.item(0).getNodeValue();
							System.out.println("FALSE");
							System.out.println(idFileName);
							return false;
						}
					}
					classAttribute = (Attr)classAttributes.item(0);
					if(classAttribute.getNodeName().equals("id")){
						
						tempID = classAttribute.getNodeValue();
					}		

				createClassesXMLEntry(tempPath,tempID);
			}
			Settings oSettings = new Settings();
	    	String persistentPath = oSettings.getPersistentInformationPath();
			outputClassesXML(persistentPath + "/classes.xml");
			return true;

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
		return null;
			
	}
    
    /**
     * Output classes xml file.
     *
     * @param outputFile the name of the output file
     */
    public void outputClassesXML(String outputFile){
    	outputToFile(outputFile, classXMLDoc);
    }
    
    /**
     * Output mutations xml file.
     *
     * @param outputFile the name of the output file
     */
    public void outputMutantsXML(String outputFile){
    	outputToFile(outputFile, mutationXMLDoc);
    }
    
    /**
     * Output appended xml file.
     *
     * @param outputFile the name of the output file
     */
    public void outputAppendedXML(String outputFile){
    	outputToFile(outputFile, appendedXMLDoc);
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
        	mutationXMLDoc = docBuild.newDocument();
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
    		classXMLDoc = docBuild.newDocument();
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
     * @param id the class id
     */
    public void createClassesXMLEntry(String classPath, String id){
    	try{
        	idFileName = Long.toString(System.nanoTime());
            Element Class = classXMLDoc.createElement("class");
            Class.setAttribute("path", classPath);
            classes.appendChild(Class);
            if(id.equals("")){
            	Class.setAttribute("id", idFileName);
            }
            else
            	Class.setAttribute("id", id);
            classes.appendChild(Class);
                        
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
            if (mutantName != null)
            	mutant.setAttribute("name", mutantName);
            mutant.setAttribute("type", mutationType);
            mutant.setAttribute("oldOp", opOld);
            mutant.setAttribute("newOp", opNew);
            mutation.appendChild(mutant);
                        

    	} catch (Exception e) {
            System.out.println(e);
        }   
    	
    }
    
    /**
     * Creates the xml file containing mutations.
     *
     *@param classPath path for the class file.
     * @param listOfMutations the array list of mutations associated with the class file in the classPath.
     */
    public void createMutationsXML(MutantCollection listOfMutations, String classPath, String pathToSave){
    	boolean status;
    	Settings oSettings = new Settings();
    	String persistentPath = oSettings.getPersistentInformationPath();
		status = new File(persistentPath).mkdirs();
		status = new File(persistentPath + "/mutants/").mkdirs();
    	IMutableObject tempMutant = new Mutant();
    	createClassesXMLRoot();
    	
    	File oClassToOpen = new File(persistentPath + "/classes.xml");
    	if(!oClassToOpen.canRead()){	
    		outputClassesXML(persistentPath + "/classes.xml");
    	}
    	createMutationsXMLRoot();
   
    	createClassesXMLEntry(classPath,"");
    	appendToClassXMLFile(persistentPath + "/classes.xml", classPath);
 


    	String tempLevel, tempName, tempType, tempOld, tempNew;
    	
    	for(int i = 0; i < listOfMutations.getMutants().size(); i++){
    		tempMutant = listOfMutations.getMutants().get(i);
    		tempLevel = tempMutant.getMutantLevelAsString();
    		tempType = tempMutant.getMutantTypeAsString();
    		tempOld = tempMutant.getOldOperator();
    		tempNew = tempMutant.getNewOperator();

    		if(tempLevel.equals(Mutant.mutantLevelToString(IMutableObject.eMutantLevel.METHOD)))
    			tempName = tempMutant.getMethodName();
    		else
    			tempName = null;		
    		createMutationsXMLEntry(tempLevel, tempName, tempType, tempOld, tempNew);
    	}
 
    	outputMutantsXML(persistentPath + "/mutants/" + idFileName+ ".xml");
    	
    }
    
}











