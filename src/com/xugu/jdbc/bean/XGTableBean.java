package com.xugu.jdbc.bean;

import java.util.HashMap;
import java.util.Vector;

public class XGTableBean
{
    private String DBName;
    private String SchemaName;
    private String TableName;
    private HashMap<String,String> field;
    private Vector<XGIndexBean> indexs;
    private Vector<XGConstraintBean> constraints;
	public String getDBName()
	{
		return DBName;
	}
	public void setDBName(String dBName)
	{
		DBName = dBName;
	}
	public String getSchemaName()
	{
		return SchemaName;
	}
	public void setSchemaName(String schemaName)
	{
		SchemaName = schemaName;
	}
	public String getTableName()
	{
		return TableName;
	}
	public void setTableName(String tableName)
	{
		TableName = tableName;
	}
	public HashMap<String, String> getField()
	{
		return field;
	}
	public void setField(HashMap<String, String> field)
	{
		this.field = field;
	}
	public Vector<XGIndexBean> getIndexs()
	{
		return indexs;
	}
	public void setIndexs(Vector<XGIndexBean> indexs)
	{
		this.indexs = indexs;
	}
}
