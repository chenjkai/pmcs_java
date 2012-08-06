package pmcs.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pmcs.exception.PmcsNullPointerException;
import pmcs.exception.util.UnCorrectToGetDomainException;

public class Util {

	public static boolean isIp(String domain) {
		String reg = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(domain);
		return matcher.matches();
	}

	public static boolean isDomain(String domain) {
		String reg = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(domain);
		return matcher.matches();
	}

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
}
