package com.xugu.jdbc.output;


import java.sql.*;


public class TestMeta2
{
	private static final String driver="com.xugu.cloudjdbc.Driver";
	private static final String pwd="SYSDBA";
	private static final String user="SYSDBA";
	private static final String url = "jdbc:xugu://127.0.0.1:5138/SYSTEM" + "?user=" + user + "&password=" + pwd;
	private static final String driver1 = "com.mysql.jdbc.Driver"; 
	private static final String url1 = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=root";
	private static Connection getConnection=null;

	public static void main(String[] args) {
		getConnection=getConnections();
		try {
			DatabaseMetaData dbmd=getConnection.getMetaData();
		    ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[] { "TABLE"});
		    while (resultSet.next()) {
		    	    String schemaName= resultSet.getString("TABLE_SCHEM");
		    	    String tableName=resultSet.getString("TABLE_NAME");
		    	    System.out.println("模式名："+schemaName+"\t表名："+tableName);
//		    		ResultSet rs = dbmd.getColumns(null, schemaName, tableName, "%");
//		    		while(rs.next()){
//		    			System.out.println("字段名："+rs.getString("COLUMN_NAME")+"\t字段数据类型："+rs.getString("TYPE_NAME")+"\t数据精度:"+rs.getString("COLUMN_SIZE")+"\t数据标度:"
//		    		+rs.getString("DECIMAL_DIGITS")+"\t空值约束:"+rs.getString("IS_NULLABLE")+"\t默认值:"+rs.getString("COLUMN_DEF")+"\t是否自动递增"+rs.getString("IS_AUTOINCREMENT"));
//		    		}
//		    		ResultSet rs1 = dbmd.getPrimaryKeys(null,schemaName, tableName);
//		    		while(rs1.next()){
//		    			System.out.println("主键列名称:"+rs1.getString("COLUMN_NAME ")+"\tPK_NAME:"+rs1.getString("PK_NAME")+"\tKEY_SEQ:"+rs1.getString("KEY_SEQ"));
//		    		}
		    		ResultSet rs2 =  dbmd.getImportedKeys(null,schemaName,tableName);
		    		while(rs2.next()){
		    			System.out.println("外键列名称:"+rs2.getString("FKCOLUMN_NAME")+"\t外键引用模式:"+rs2.getString("PKTABLE_SCHEM")+"\t外键引用表名:"+rs2.getString("PKTABLE_NAME")+"\t外键引用列名称:"+rs2.getString("PKCOLUMN_NAME")+"\tKEY_SEQ："+rs2.getString("KEY_SEQ"));
		    		}
		    		ResultSet rs3 = dbmd.getCrossReference(null, "%", "%", null, schemaName, tableName);
		    		while(rs3.next()){
		    			System.out.println("外键列名称:"+rs3.getString("FKCOLUMN_NAME")+"\t外键引用模式名："+rs3.getString("PKTABLE_SCHEM")+"\t外键引用表名："+rs3.getString("PKTABLE_NAME")+"\t外键引用列名："+rs3.getString("PKCOLUMN_NAME"));
		    		}
//		    		ResultSet rs3 = dbmd.getIndexInfo(null, schemaName, tableName, false, false);
//		    		while(rs3.next()){
//		    			System.out.println("索引是否唯一:"+rs3.getString("NON_UNIQUE")+"\t索引名称:"+rs3.getString("INDEX_NAME")+"\t索引类型:"+rs3.getString("TYPE")+"\t索引列名"+rs3.getString("COLUMN_NAME")+"\t索引类别："+rs3.getString("INDEX_QUALIFIER"));
//		    		}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnections(){
		try {
			Class.forName(driver);
			getConnection=DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getConnection;
	}
	
	 public static String getSchema() throws Exception {
			String schema;
			schema =getConnection.getMetaData().getUserName();
			if ((schema == null) || (schema.length() == 0)) {
			    throw new Exception("ORACLE数据库模式不允许为空");
			}
			return schema.toUpperCase().toString();

	}
}
