package com.xugu.jdbc.bean;

import java.util.Vector;

public class XGIndexBean
{
    private String indexName;
    private Vector<String> indexCol;
    private String indexType;
	public String getIndexName()
	{
		return indexName;
	}
	public void setIndexName(String indexName)
	{
		this.indexName = indexName;
	}
	public Vector<String> getIndexCol()
	{
		return indexCol;
	}
	public void setIndexCol(Vector<String> indexCol)
	{
		this.indexCol = indexCol;
	}
	public String getIndexType()
	{
		return indexType;
	}
	public void setIndexType(String indexType)
	{
		this.indexType = indexType;
	}
  
}
