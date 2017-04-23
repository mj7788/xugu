package com.xugu.jdbc.bean;

import java.util.HashMap;


public class TableBean
{
	    private String db_name;
	    private Integer db_id;
	    private String schema_name;
	    private String table_name;
	    private Integer table_id;
	    private HashMap<String, String> field = new HashMap<>();
	    
		public String getDb_name()
		{
			return db_name;
		}
		public void setDb_name(String db_name)
		{
			this.db_name = db_name;
		}
		public Integer getDb_id()
		{
			return db_id;
		}
		public void setDb_id(Integer db_id)
		{
			this.db_id = db_id;
		}
		public String getSchema_name()
		{
			return schema_name;
		}
		public void setSchema_name(String schema_name)
		{
			this.schema_name = schema_name;
		}
		public String getTable_name()
		{
			return table_name;
		}
		public void setTable_name(String table_name)
		{
			this.table_name = table_name;
		}
		public Integer getTable_id()
		{
			return table_id;
		}
		public void setTable_id(Integer table_id)
		{
			this.table_id = table_id;
		}
		public HashMap<String, String> getField()
		{
			return field;
		}
		public void setField(HashMap<String, String> field)
		{
			this.field = field;
		}
	
}
