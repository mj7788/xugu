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
		    	    System.out.println("ģʽ����"+schemaName+"\t������"+tableName);
//		    		ResultSet rs = dbmd.getColumns(null, schemaName, tableName, "%");
//		    		while(rs.next()){
//		    			System.out.println("�ֶ�����"+rs.getString("COLUMN_NAME")+"\t�ֶ��������ͣ�"+rs.getString("TYPE_NAME")+"\t���ݾ���:"+rs.getString("COLUMN_SIZE")+"\t���ݱ��:"
//		    		+rs.getString("DECIMAL_DIGITS")+"\t��ֵԼ��:"+rs.getString("IS_NULLABLE")+"\tĬ��ֵ:"+rs.getString("COLUMN_DEF")+"\t�Ƿ��Զ�����"+rs.getString("IS_AUTOINCREMENT"));
//		    		}
//		    		ResultSet rs1 = dbmd.getPrimaryKeys(null,schemaName, tableName);
//		    		while(rs1.next()){
//		    			System.out.println("����������:"+rs1.getString("COLUMN_NAME ")+"\tPK_NAME:"+rs1.getString("PK_NAME")+"\tKEY_SEQ:"+rs1.getString("KEY_SEQ"));
//		    		}
		    		ResultSet rs2 =  dbmd.getImportedKeys(null,schemaName,tableName);
		    		while(rs2.next()){
		    			System.out.println("���������:"+rs2.getString("FKCOLUMN_NAME")+"\t�������ģʽ:"+rs2.getString("PKTABLE_SCHEM")+"\t������ñ���:"+rs2.getString("PKTABLE_NAME")+"\t�������������:"+rs2.getString("PKCOLUMN_NAME")+"\tKEY_SEQ��"+rs2.getString("KEY_SEQ"));
		    		}
		    		ResultSet rs3 = dbmd.getCrossReference(null, "%", "%", null, schemaName, tableName);
		    		while(rs3.next()){
		    			System.out.println("���������:"+rs3.getString("FKCOLUMN_NAME")+"\t�������ģʽ����"+rs3.getString("PKTABLE_SCHEM")+"\t������ñ�����"+rs3.getString("PKTABLE_NAME")+"\t�������������"+rs3.getString("PKCOLUMN_NAME"));
		    		}
//		    		ResultSet rs3 = dbmd.getIndexInfo(null, schemaName, tableName, false, false);
//		    		while(rs3.next()){
//		    			System.out.println("�����Ƿ�Ψһ:"+rs3.getString("NON_UNIQUE")+"\t��������:"+rs3.getString("INDEX_NAME")+"\t��������:"+rs3.getString("TYPE")+"\t��������"+rs3.getString("COLUMN_NAME")+"\t�������"+rs3.getString("INDEX_QUALIFIER"));
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
			    throw new Exception("ORACLE���ݿ�ģʽ������Ϊ��");
			}
			return schema.toUpperCase().toString();

	}
}
