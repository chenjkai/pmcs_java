package pmcs;

import pmcs.connector.Connector;

public class Pmcs {
	
	public static void main(String[] args) {
		Connector connector = new Connector();
		try {
			connector.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
