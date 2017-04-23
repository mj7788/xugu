package com.xugu.jdbc.bean;

public class IndexBean
{
    private Integer db_id;
    private Integer table_id;
    private String index_name;
    
	public Integer getDb_id()
	{
		return db_id;
	}
	public void setDb_id(Integer db_id)
	{
		this.db_id = db_id;
	}
	public Integer getTable_id()
	{
		return table_id;
	}
	public void setTable_id(Integer table_id)
	{
		this.table_id = table_id;
	}
	public String getIndex_name()
	{
		return index_name;
	}
	public void setIndex_name(String index_name)
	{
		this.index_name = index_name;
	}
   
}
