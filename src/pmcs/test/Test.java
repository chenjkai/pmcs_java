package pmcs.test;

import pmcs.oracop.OraOp;
import pmcs.util.Util;

public class Test {
	
	public static void main(String[] args) {
		
		OraOp oraOp = new OraOp();
		oraOp.dbCreateTable(0);
		oraOp.dbCreateTable(1);
		oraOp.dbCreateTable(2);
		oraOp.dbCreateTable(3);
		oraOp.dbCreateTable(4);
		oraOp.dbCreateTable(5);
		
		System.out.println(Util.getCurrentTime());
	}
	
}
