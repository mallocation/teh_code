package utilities;

import static org.junit.Assert.*;

import mutations.MutantCollection;

import org.junit.Before;
import org.junit.Test;

public class TestGenerateXML {
	
	private ParseXML parse;
	private GenerateXML testGenerate;
	private String fileName;
	private String classPath;
	private MutantCollection parsedList;
	private MutantCollection generatedList;
	
	@Before
	public void setUp() throws Exception {
		parse = new ParseXML();
		testGenerate = new GenerateXML();
		parsedList = new  MutantCollection();
		generatedList = new MutantCollection();
		fileName = "test.xml";
		classPath = "Average.class";
		parsedList = parse.getMutantAttributes(fileName, classPath);
	}

	@Test
	public void testOutputClassesXML() {
		fail("Not yet implemented");
	}

	@Test
	public void testOutputMutantsXML() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateMutationsXMLRoot() {
		fail("Not yet implemented");
	}
	

	@Test
	public void testCreateClassesXMLRoot() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateClassesXMLEntry() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateMutationsXMLEntry() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateMutationsXML() {
		fail("Not yet implemented");
	}

}
