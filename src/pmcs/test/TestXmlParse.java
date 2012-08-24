package pmcs.test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import pmcs.exception.parser.ParseException;
import pmcs.exception.parser.ReadXmlBytesException;
import pmcs.xml.parser.XmlParser;
import pmcs.xml.reader.XmlReader;


public class TestXmlParse {
	
	public static void main(String[] args) {
		
		try {
			
			XmlParser xsp = new XmlParser();
			Socket socket = new Socket("192.168.6.196",9005);
			while (true) {
				xsp.parse(new XmlReader().readXmlBytesFromSocket(socket));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ReadXmlBytesException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
