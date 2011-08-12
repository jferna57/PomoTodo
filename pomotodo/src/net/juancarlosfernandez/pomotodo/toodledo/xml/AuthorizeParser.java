package net.juancarlosfernandez.pomotodo.toodledo.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AuthorizeParser extends DefaultHandler {

	private String xml;
	private String tempVal;
	private String token ;

	public AuthorizeParser(String xml) {
		this.xml = xml;
	}

	public String getToken() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			// parse the string and also register this class for call backs
			sp.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")), this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		return token;
	}


	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(localName.equalsIgnoreCase("token")) {
			token = tempVal;
		}
	}

}
