package utilities;

import java.io.FileNotFoundException;
import java.io.IOException;

import mutations.MutantCollection;

import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import junit.framework.TestCase;


	public class TestParseXML extends TestCase {
		
		private ParseXML testParse;
		private String fileName;
		private String classPath;
		private MutantCollection mutationsList;
		public TestParseXML(String testName){
			super(testName);
		}
		
		public void setUp(){
			testParse = new ParseXML();
			mutationsList = new  MutantCollection();
			fileName = "test.xml";
			classPath = "Average.class";
			mutationsList = testParse.getMutantAttributes(fileName, classPath);
		}
		

		@Test
		public void testGetLevel() {	
			String level = "METHOD";
			String testLevel = mutationsList.getMutant(0).getMutantLevelAsString();
			assertEquals(level, testLevel);
		}
		@Test
		public void testGetMethodName() {	
			String name = "subtract";
			String testName = mutationsList.getMutant(0).getMethodName();
			assertEquals(name, testName);
		}
		@Test
		public void testGetNewOp() {	
			String newOp = "+";
			String testNewOp = mutationsList.getMutant(0).getNewOperator();
			assertEquals(newOp, testNewOp);
		}
		@Test
		public void testGetOldOp() {	
			String oldOP = "-";
			String testOldOp = mutationsList.getMutant(0).getOldOperator();
			assertEquals(oldOP, testOldOp);
		}
		@Test
		public void testGetType() {	
			String type = "ARITHMETIC";
			String testType = mutationsList.getMutant(0).getMutantTypeAsString();
			assertEquals(type, testType);
		}
		@Test
		public void testGetPersistentMutationsFileName(){
			String id = System.getProperty("user.dir") + "/persistentStorage/generated_XML/mutants/23424074861190.xml";
			String testid = testParse.getPersistentMutationsFileName(System.getProperty("user.dir") + "/persistentStorage/generated_XML/classes.xml" ,System.getProperty("user.dir") + "\\bin\\Average.class");
			assertEquals(id,testid);
		}
		
		@Test
		public void testGetXMLFile() {
			testParse.getXMLFile("test.xml");
		}
}


