package net.juancarlosfernandez.pomotodo.toodledo.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.juancarlosfernandez.pomotodo.toodledo.data.Priority;
import net.juancarlosfernandez.pomotodo.toodledo.data.Repeat;
import net.juancarlosfernandez.pomotodo.toodledo.data.Status;
import net.juancarlosfernandez.pomotodo.toodledo.data.Todo;
import net.juancarlosfernandez.pomotodo.toodledo.util.TdDate;
import net.juancarlosfernandez.pomotodo.toodledo.util.TdDateTime;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetTodosParser extends DefaultHandler {

	private String xml;
	StringBuilder tempVal;
	private Todo tmp_;

	ArrayList<Todo> todoList;

	public GetTodosParser(String xml) {
		this.xml = xml;
		todoList = new ArrayList<Todo>();
	}

	public ArrayList<Todo> getTodos() {
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

		return todoList;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		tempVal = new StringBuilder();
		
		// Changed qName to localName
		if (localName.equalsIgnoreCase("task")) {
			tmp_ = new Todo();
		} else if (localName.equalsIgnoreCase("context")) {
			tmp_.setContext(Integer.parseInt(attributes.getValue("id")));
		} else if (localName.equalsIgnoreCase("goal")) {
			tmp_.setGoal(Integer.parseInt(attributes.getValue("id")));
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tempVal.append(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if (localName.equalsIgnoreCase("task")) {
			todoList.add(tmp_);
		} else if (localName.equalsIgnoreCase("id")) {
			tmp_.setId(Integer.parseInt(tempVal.toString()));
		} else if (localName.equalsIgnoreCase("parent")) {
			tmp_.setParent(Integer.parseInt(tempVal.toString()));
		}  else if (localName.equalsIgnoreCase("title")) {
			tmp_.setTitle(tempVal.toString());
		} else if (localName.equalsIgnoreCase("tag")) {
			tmp_.setTag(tempVal.toString());
		} else if (localName.equalsIgnoreCase("folder")) {
			tmp_.setFolder(Integer.parseInt(tempVal.toString()));
		} else if (localName.equalsIgnoreCase("added")) {
			tmp_.setAdded(new TdDate(tempVal.toString()));
		} else if (localName.equalsIgnoreCase("modified")) {
			tmp_.setModified(new TdDateTime(tempVal.toString()));
		} else if (localName.equalsIgnoreCase("startdate")) {
			tmp_.setStartDate(new TdDate(tempVal.toString()));
		} else if (localName.equalsIgnoreCase("duedate")) {
			tmp_.setDueDate(new TdDate(tempVal.toString()));
		}  else if (localName.equalsIgnoreCase("completed")) {
			tmp_.setCompleted(new TdDate(tempVal.toString()));
		} else if (localName.equalsIgnoreCase("repeat")) {
			tmp_.setRepeat(Repeat.ValueFromInt.get(Integer.parseInt(tempVal.toString())));
		}  else if (localName.equalsIgnoreCase("status")) {
			tmp_.setStatus(Status.ValueFromInt.get(Integer.parseInt(tempVal.toString())));
		} else if (localName.equalsIgnoreCase("star")) {
			int star = Integer.parseInt(tempVal.toString());
			if (star == 0)
				tmp_.setStar(false);
			else 
				tmp_.setStar(true);
		} else if (localName.equalsIgnoreCase("priority")) {		
			tmp_.setPriority(Priority.ValueFromInt.get(Integer.parseInt(tempVal.toString())));
		} else if (localName.equalsIgnoreCase("length")) {
			tmp_.setLength(Integer.parseInt(tempVal.toString()));
		}  else if (localName.equalsIgnoreCase("note")) {
			tmp_.setNote(tempVal.toString());
		}
	}

}
