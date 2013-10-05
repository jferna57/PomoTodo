package net.juancarlosfernandez.pomotodo.toodledo.xml;

import net.juancarlosfernandez.pomotodo.toodledo.exception.IncorrectUserPasswordException;
import net.juancarlosfernandez.pomotodo.toodledo.exception.MissingPasswordException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class GetUserIdParser extends DefaultHandler {

    private String xml;
    private StringBuilder tempVal;
    private String userId = null;

    public GetUserIdParser(String xml) {
        this.xml = xml;
    }

    public String getUserId() throws IncorrectUserPasswordException, MissingPasswordException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            sp.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")), this);

        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        if (userId.equals("0")) {
            throw new MissingPasswordException("Missing password");
        } else if (userId.equals("1")) {
            throw new IncorrectUserPasswordException("Username or password incorrect, or user nonexistant");
        }
        return userId;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        tempVal = new StringBuilder();
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equalsIgnoreCase("userid")) {
            userId = tempVal.toString().trim();
        }
    }
}
