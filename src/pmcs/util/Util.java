package pmcs.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 工具类
 * @author steven
 *
 */
public class Util {
	/**
	 * 判断domain是否是符合规范的ip地址
	 * @param domain ip字符串
	 * @return boolean
	 */
	public static boolean isIp(String domain) {
		String reg = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(domain);
		return matcher.matches();
	}
	
	/**
	 * 判断字符串是不是符合规范的域名
	 * @param domain 域名字符串
	 * @return boolean
	 */
	public static boolean isDomain(String domain) {
		String reg = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(domain);
		return matcher.matches();
	}
	
	/**
	 * 根据域名获取ip地址
	 * @param domain 域名
	 * @return ip地址
	 */
	public static String getIpByDomain(String domain) {
		try {
			InetAddress myServer = InetAddress.getByName(domain);
			if (myServer != null) {
				String[] server = myServer.toString().split("/");
				if (server.length == 2)
					return server[1];
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * convert 4 byte to int
	 * 
	 * @param num
	 * @return the int convert from bytes
	 */
	public static int bytesToint(byte[] num) {
		int result = num[0] & 0xFF;
		result |= ((num[1] << 8) & 0xFF00);
		result |= ((num[2] << 16) & 0xFF0000);
		result |= ((num[3] << 24) & 0xFF000000);
		return result;
	}
	
	/**
	 * 获取系统当前时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime(){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 return df.format(new Date());
	}
	
	/**
	 * 
	 * @return oracle time
	 */
	public static String getOraCurrentTimeString(){
		return "to_date('" + getCurrentTime() + "','YYYY/MM/DD HH24:MI:SS')";
	}
}
