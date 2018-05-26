package cn.origin.util.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接类
 */
public class DatabaseConnection {
	public static final String DATABASE_DRIVER = "org.gjt.mm.mysql.Driver" ;
	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/db" ;
	public static final String DATABASE_USER = "root" ;
	public static final String DATABASE_PASSWORD = "mysql" ;
	private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<Connection>() ;
	private static Connection rebuildConnection() throws Exception {	
		Class.forName(DATABASE_DRIVER) ;
		Connection conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD) ;
		return conn ;
	}

	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = THREAD_LOCAL.get() ;
		if (conn == null) {
			try {
				conn = rebuildConnection() ;
				THREAD_LOCAL.set(conn); // 进行连接的信息保存
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn ;
	}

	/**
	 * 关闭连接
	 */
	public static void close() {
		Connection conn = THREAD_LOCAL.get() ;
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		THREAD_LOCAL.remove(); 
	}
}
