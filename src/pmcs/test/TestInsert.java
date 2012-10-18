package pmcs.test;

import pmcs.oracop.OraUtil;
import pmcs.util.Util;

public class TestInsert {
	
	public static void main(String[] args) {
		OraUtil oraUtil = new OraUtil();
//		String sql = "insert into test2110 values ('steven',1,to_date('" + currentTime +"','YYYY/MM/DD HH24:MI:SS'))";
		String sql = "insert into test2110 values ('steven',1," + Util.getOraCurrentTimeString() + ")";
		System.out.println(sql);
		oraUtil.executeUpdate(sql);
	}

}
