package pmcs.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReadAllBytes {		
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("116.224.150.48",9005);
			BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
			while(true){
				System.out.println((char)(is.read()&0xff));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
