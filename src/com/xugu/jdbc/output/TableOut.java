package com.xugu.jdbc.output;

import java.io.FileInputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import com.xugu.jdbc.bean.XGConstraintBean;
import com.xugu.jdbc.bean.XGIndexBean;
import com.xugu.jdbc.bean.XGTableBean;



public class TableOut
{
	public Connection getCon(){
		Connection con = null;
		try
		{
			Class.forName("com.xugu.cloudjdbc.Driver");
			Properties pro = new Properties();
			FileInputStream fis = new FileInputStream("xugu.properties");
			pro.load(fis);
			String ip = pro.getProperty("ip");
			String port =  pro.getProperty("port");
			String dbname = pro.getProperty("dbname");
			String user = pro.getProperty("user");
			String pwd = pro.getProperty("pwd");
			String url = "jdbc:xugu://"+ip+":"+port+"/"+dbname;
			con = DriverManager.getConnection(url,user,pwd);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	public Vector<XGTableBean> getTables(){
		Vector<XGTableBean> tables = new Vector<>();
		Connection connection = getCon();
		try
		{
			DatabaseMetaData dmd =  connection.getMetaData();
			ResultSet rs = dmd.getTables(null, "%", "%", new String[] {"table"});
			while(rs.next()){
				HashMap<String, String> field = new HashMap<>();
				Vector<XGIndexBean> indexs = new Vector<>();
				Vector<XGConstraintBean> constrs = new Vector<>();
				XGTableBean table = new XGTableBean();
				String tableName = rs.getString("TABLE_NAME");
				String schemName = rs.getString("TABLE_SCHEM");
				table.setSchemaName(schemName);
				table.setTableName(tableName);
				ResultSet rs1 = dmd.getColumns(null, schemName, tableName, "%");
				while(rs1.next()){
					String colName = rs1.getString("COLUMN_NAME");
					String type = rs1.getString("TYPE_NAME");
					String isNull = rs1.getString("IS_NULLABLE");
					String def = rs1.getString("COLUMN_DEF");
					String isIdentity = rs1.getString("IS_AUTOINCREMENT");
					//用户自建类型还没有考虑
					switch (type)
					{
						case "VARCHAR":
						case "CHAR":
						case "INTERVAL YEAR":
						case "INTERVAL MONTH":
						case "INTERVAL DAY":
						case "INTERVAL HOUR":
						case "INTERVAL MINUTE":
							if(!rs1.getString("COLUMN_SIZE").equals("-1")){
								type = type+"("+rs1.getString("COLUMN_SIZE")+")";
							}
							break;
						case "NUMERIC":
						case "INTERVAL SECOND":
							type = type+"("+rs1.getString("COLUMN_SIZE")+","+rs1.getString("DECIMAL_DIGITS")+")";
							break;
						case "INTERVAL YEAR TO MONTH":
						case "INTERVAL DAY TO HOUR":
						case "INTERVAL DAY TO MINUTE":
						case "INTERVAL HOUR TO MINUTE":
							String s = type.substring(0,type.lastIndexOf(" TO"));
							String s1 = type.substring(type.indexOf(" TO"),type.length());
							type = s+"("+rs1.getInt("COLUMN_SIZE")+")"+s1;
							break;
						case "INTERVAL DAY TO SECOND":
						case "INTERVAL HOUR TO SECOND":
						case "INTERVAL MINUTE TO SECOND":
							String s2 = type.substring(0,type.lastIndexOf(" TO"));
							String s3 = type.substring(type.indexOf(" TO"),type.length());
							type = s2+"("+rs1.getInt("COLUMN_SIZE")+")"+s3+"("+rs1.getString("DECIMAL_DIGITS")+")";
							break;
						default:
							break;
					}
					//自增细节还有待查看
					if(isIdentity.equals("YES")){
						type += " identity(1,1)";
					}
					if(isNull.equals("NO")){
						type += " NOT NULL";
					}
					if(def != null){
						type += " default "+def;
					}
					field.put(colName, type);
				}
		        //唯一索引还有待查看
				ResultSet rs2 = dmd.getIndexInfo(null, schemName, tableName, false, false);
				while(rs2.next()){
				   XGIndexBean index = null;
				   String indexName = rs2.getString("INDEX_NAME");
				   if(indexs.size() == 0){
					   Vector<String> indCol = new Vector<>();
					   index = new XGIndexBean();
					   index.setIndexName(indexName);
					   indCol.add(rs2.getString("COLUMN_NAME"));
					   index.setIndexType(rs2.getString("INDEX_QUALIFIER"));
					   index.setIndexCol(indCol);
				   }else if(indexs.lastElement().getIndexName().equals(indexName)){//如果索引是按顺序出来的last有效，如果顺序是乱的需要遍历整个indexs,设立一个flag。
					   indexs.lastElement().getIndexCol().add(rs2.getString("COLUMN_NAME")); 
				   }else{
					   Vector<String> indCol = new Vector<>();
					   index=new XGIndexBean();
					   index.setIndexName(indexName);
					   indCol.add(rs2.getString("COLUMN_NAME"));
					   index.setIndexType(rs2.getString("INDEX_QUALIFIER"));
					   index.setIndexCol(indCol);
					}
				   if(index != null){
						 indexs.add(index);
				   }
				}
				table.setField(field);
				table.setIndexs(indexs);
				tables.add(table);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return tables;
	}
}
