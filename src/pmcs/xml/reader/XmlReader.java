package pmcs.xml.reader;

import java.io.IOException;
import java.io.InputStream;
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
		InputStream bis = socket.getInputStream();
		byte[] lengthBytes = new byte[4];
		if (bis.read(lengthBytes, 0, 4) == 4) {
			int length = Util.bytesToint(lengthBytes);
			int rest = length;
			byte[] messageBytes = new byte[length];
			int read = 0;
			while (rest > 0) {
				byte[] buffer = new byte[rest];
				read = bis.read(buffer);
				if(read > length || rest < 0){
					throw new ReadXmlBytesException("读取xml字节流溢出");
				}
				else {
					System.arraycopy(buffer, 0, messageBytes, length - rest, read);
					rest -= read;
				}
			}
			return messageBytes;
		} else {
			throw new ReadXmlBytesException("获取数据长度失败");
		}
	}
}
