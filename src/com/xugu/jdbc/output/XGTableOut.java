package com.xugu.jdbc.output;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;
import com.xugu.jdbc.bean.TableBean;




public class XGTableOut
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
			String url = "jdbc:xugu://"+ip+":"+port+"/"+dbname+"?"+"user="+user+"&"+"password="+pwd;
			con = DriverManager.getConnection(url);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	//获取表结构信息
	public Vector<TableBean> gettable(){
		Connection connection  = getCon();
		Vector<TableBean> tables = new Vector<>();
		String sql = "select distinct field_num,db_name,a.db_id,schema_name,table_name,a.table_id,col_name,type_name from sys_tables a,sys_databases b,sys_schemas c,sys_columns d "
                +"where a.db_id=b.db_id and a.schema_id = c.schema_id and a.table_id=d.table_id and a.db_id=d.db_id order by db_id";
		try
		{
			int count = 0;
			TableBean table = null;
			Statement s = connection.createStatement();
		    ResultSet rs = s.executeQuery(sql);
		    while(rs.next()){
			   	if(count==0){
			   		table = new TableBean();
			   		table.setDb_name(rs.getString("db_name"));
					table.setDb_id(rs.getInt("db_id"));
					table.setSchema_name(rs.getString("schema_name"));
					table.setTable_name(rs.getString("table_name"));
					table.setTable_id(rs.getInt("table_id"));
			   	}
			   	table.getField().put(rs.getString("col_name"), rs.getString("type_name"));
			   	tables.add(table);
			   	count++;
			   	if(count==rs.getInt("field_num")){
			   		count = 0;
			   	}
			 rs.close();
			 s.close();
		   }
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return tables;
	}
	//获取index
	public void getindexes(){
		Connection connection = getCon();
		String sql = "";
		try
		{
			Statement s  = connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()){
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
