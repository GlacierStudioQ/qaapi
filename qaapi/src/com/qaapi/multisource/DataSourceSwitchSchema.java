package com.qaapi.multisource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qaapi.DBInfo;
import com.qaapi.multisource.NowSchemaHolder;

import static com.qaapi.multisource.NowSchemaHolder.DFT_DB;;

/**
 * 
 * 根据具体要求更换schema不同的数据源
 * @author IceAsh
 *
 */
public class DataSourceSwitchSchema implements DataSource {

	private static Map<String, DataSource> sources = new HashMap<String, DataSource>();
	private DataSource dataSourceBase;

	private DataSource getDataSource() {
		String dbName = NowSchemaHolder.get();
		return createSource(dbName);
	}
	
	/**
	 * 本质上也是读取jdbc.properties，
	 * 构造一个dataSource，只有scheam与baseSource不同
	 * 
	 * @param dbName
	 * @return
	 */
	private DataSource createSource(String dbName) {
		// 没有明确指出的使用默认数据源
		if (StringUtils.isEmpty(dbName) || DFT_DB.equals(dbName)) {
			System.out.println("db => use default db");
			return dataSourceBase;
			
		// 明确指出的使用已经保存的数据源
		} if(sources.get(dbName) != null){
			System.out.println("db => use saved db " + dbName);
			return sources.get(dbName);
			
		// 明确指出但未保存的，新建并保存
		}else {
			try{
				String driver = DBInfo.get("jdbc.driverClassName");
				String host = DBInfo.get("jdbc.host");
				String user = DBInfo.get("jdbc.username");
				String password = DBInfo.get("jdbc.password");
	
				ComboPooledDataSource ds = new ComboPooledDataSource();
				ds.setDriverClass(driver);
				String url = "jdbc:mysql://"+host+"/"+dbName+"?useUnicode=true&characterEncoding=UTF-8";
				ds.setJdbcUrl(url);
				ds.setUser(user);
				ds.setPassword(password);
				
				ds.setMinPoolSize(3);
				ds.setInitialPoolSize(1);
				// 估计之前是因为链接的idle的时间过长导致了connection reset的出现，加了下面两个参数。
				ds.setMaxIdleTime(60);
				ds.setIdleConnectionTestPeriod(60);
				ds.setMaxStatements(0);
				
				sources.put(dbName, ds);
				System.out.println("db => use just created db : " + dbName);
				return ds;
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
	}

	public Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return getDataSource().getConnection(username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return getDataSource().getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return getDataSource().getLoginTimeout();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		getDataSource().setLogWriter(out);

	}

	public void setLoginTimeout(int seconds) throws SQLException {
		getDataSource().setLoginTimeout(seconds);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return getDataSource().isWrapperFor(iface);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return getDataSource().unwrap(iface);
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public DataSource getDataSourceBase() {
		return dataSourceBase;
	}

	public void setDataSourceBase(DataSource dataSourceBase) {
		this.dataSourceBase = dataSourceBase;
	}
	
}
