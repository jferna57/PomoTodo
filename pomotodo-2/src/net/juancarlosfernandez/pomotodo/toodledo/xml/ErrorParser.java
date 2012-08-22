package net.juancarlosfernandez.pomotodo.toodledo.xml;

import net.juancarlosfernandez.pomotodo.toodledo.error.ToodledoError;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ErrorParser extends DefaultHandler {

    private String xml;
    private String tempVal;
    private ToodledoError tempError = null;

    public ErrorParser(String xml) {
        this.xml = xml;
    }

    public ToodledoError getError() {
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

        return tempError;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //reset
        tempVal = "";
        if (localName.equalsIgnoreCase("error")) {
            tempError = new ToodledoError();
            tempError.setId(Integer.parseInt(attributes.getValue("id")));
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (localName.equalsIgnoreCase("error")) {
            tempError.setMessage(tempVal);
        }
    }

}
