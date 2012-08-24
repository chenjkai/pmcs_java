package pmcs.xml.reader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import pmcs.exception.parser.ReadXmlBytesException;
import pmcs.util.Util;

/**
 * 从crossbow网关9005端口读取xml字节流
 * 
 * @author steven
 */
public class XmlReader {
	
	/**
	 * 从socket读取xml字节流
	 * @param socket 
	 * @return
	 * @throws IOException 
	 * @throws ReadXmlBytesException 
	 */
	public byte[] readXmlBytesFromSocket(Socket socket) throws IOException, ReadXmlBytesException{
		BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
		byte[] lengthBytes = new byte[4];
//		bis.read(lengthBytes, 0, 4);
		
		if (bis.read(lengthBytes, 0, 4) == 4) {
			int length = Util.bytesToint(lengthBytes);
			byte[] buffer = new byte[length];
			int rest = length;
			byte[] messageBytes = new byte[length];
			int read = 0;
			while (rest > 0) {
				read = bis.read(buffer);
				if(read > length || rest < 0){
					throw new ReadXmlBytesException("读取xml字节流溢出");
				}else {
					System.arraycopy(buffer, 0, messageBytes, length - rest, read);
					rest -= read;
				}
			}
			 for(int i=0;i<messageBytes.length;i++){
				 System.out.print((char)(messageBytes[i]&0xff));
			 }
			 System.out.println("");
			 return messageBytes;
		} else {
			throw new ReadXmlBytesException("获取数据长度失败");
		}
	}
}
