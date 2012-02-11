/*
 *  Copyright 2011 www.juancarlosfernadez.net
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package net.juancarlosfernandez.pomotodo.toodledo.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.juancarlosfernandez.pomotodo.toodledo.data.AccountInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AccountInfoParser extends DefaultHandler {

	private String xml; 
	private StringBuilder tempVal; 
	private AccountInfo tmp_;
	
	private AccountInfo accountInfo; 
	
	public AccountInfoParser(String xml) {
		this.xml = xml;
		accountInfo = new AccountInfo();
	}
	
	public AccountInfo getAccountInfo() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			sp.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")), this);

		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		
		return accountInfo;
	}
	
	public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
	
		tempVal = new StringBuilder();
		
		if(localName.equalsIgnoreCase("account")) {
			tmp_ = new AccountInfo();
			//create a new instance of accountInfo
			tmp_.setUserId(attributes.getValue("userid"));
			tmp_.setAlias(attributes.getValue("alias"));
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal.append(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(localName.equalsIgnoreCase("account")) {
			accountInfo = tmp_;
		}
	}
}
