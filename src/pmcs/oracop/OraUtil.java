package pmcs.oracop;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import pmcs.configuration.Configuration;
import pmcs.exception.configuration.CanNotPassEmptyKey;
import pmcs.exception.configuration.KeyNotExitException;
import pmcs.util.Util;

/**
 * oracle 数据库操作工具类
 * 
 * @author steven
 * 
 */
public class OraUtil {
	private static Logger logger = Util.getLogger(OraUtil.class);

	private Connection con = null;
	private Configuration cfg = null;
	private Statement st = null;
	private PreparedStatement pst = null;
	private String uri = null;
	private String username = null;
	private String password = null;
	private String sid = null;
 
	public OraUtil() {
		super();
		try {
			cfg = Configuration.getConfiguration();
			uri = cfg.getProperty("uri");
			username = cfg.getProperty("username");
			password = cfg.getProperty("password");
			sid = cfg.getProperty("sid");
			if (uri == null || username == null || password == null || sid == null) {
				throw new KeyNotExitException("data base parameters");
			}
		} catch (CanNotPassEmptyKey e) {
			e.printStackTrace();
		} catch (KeyNotExitException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * open the database session with Statement
	 */
	private void openSessionWithStatement() {
		try {
			logger.info("创建数据库连接:" + uri);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(uri, username, password);
			st = con.createStatement();
			logger.info("创建数据库连接成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/** 
	 * open the database session with PreparedStatement
	 */
	private void openSessionWithPreparedStatement(String sql) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(uri, username, password);
			pst = con.prepareStatement(sql);
			logger.info("成功创建数据库连接:" + uri);
		} catch (ClassNotFoundException e) {
			logger.warn("成功创建数据库连接失败:" + uri, e);
			e.printStackTrace();
		} catch (SQLException e) {
			logger.warn("成功创建数据库连接失败:" + uri, e);
			e.printStackTrace();
		}

	}

	
	/**
	 * close the database session
	 */
	private void closeSessionWithStatement() {
		if (st != null) {
			try {
				st.close();
				st = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("关闭数据库连接成功");
	}
	/**
	 * close the database session
	 */
	private void closeSessionWithPreparedStatement() {
		if (pst != null) {
			try {
				pst.close();
				pst = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("关闭数据库连接成功");
	}
	/**
	 * 判断改表存不存在
	 * @param tableName 表名
	 * @return 存在：true 不存在：false
	 */
	public boolean isTableExit(String tableName) {
		openSessionWithStatement();
		ResultSet rsTables = null;
		try {
			logger.info("查询数据表" + tableName + "是否存在");
			DatabaseMetaData meta = con.getMetaData();
			rsTables = meta.getTables(sid, null, tableName,  
	                    new String[] { "TABLE" });
			 if (rsTables.next()) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeSessionWithStatement();
			if (rsTables != null) {
				try {
					rsTables.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * 执行sql语句
	 * 
	 * @param str
	 *            所执行sql语句
	 */
	public void executeUpdate(String str) {
		try {
			openSessionWithPreparedStatement(str);
			pst.executeUpdate();
			logger.info("执行数据库操作成功，sql语句：" + str);
		} catch (SQLException e) {
			logger.info("执行数据库操作失败，sql语句：" + str);
			e.printStackTrace();
		}finally{
			closeSessionWithPreparedStatement();
		}
	}
}
