package net.juancarlosfernandez.android.pomotodo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXParserError extends DefaultHandler{

	List<ToodledoError> myError;
	
	private String tempVal;
	
	//to maintain context
	private ToodledoError tempError;
	
	
	public SAXParserError(){
		myError = new ArrayList<ToodledoError>();
	}
	
	public void runExample() {
		parseDocument();
		printData();
	}

	private void parseDocument() {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			
			//parse the file and also register this class for call backs
			sp.parse("data/errorInvalidMailPassword.xml", this);
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	private void printData(){
		
		System.out.println("No of Errors '" + myError.size() + "'.");
		
		Iterator<ToodledoError> it = myError.iterator();
		while(it.hasNext()) {
			ToodledoError tmp = it.next();
			System.out.println("Id -> " + tmp.getId());
			System.out.println("Message -> " + tmp.getMessage() );
			
		}
	}
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("error")) {
			tempError = new ToodledoError();
			tempError.setId(Integer.parseInt(attributes.getValue("id")));
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("error")) {
			//add it to the list
			tempError.setMessage(tempVal);
			myError.add(tempError);
		}
	}
	
	public static void main(String[] args){
		SAXParserError spe = new SAXParserError();
		spe.runExample();
	}
	
}



