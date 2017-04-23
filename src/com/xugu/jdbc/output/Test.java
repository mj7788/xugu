package com.xugu.jdbc.output;

import java.util.Vector;
import com.xugu.jdbc.bean.XGIndexBean;
import com.xugu.jdbc.bean.XGTableBean;

public class Test
{

	public static void main(String[] args)
	{
		TableOut to = new TableOut();
		Vector<XGTableBean> tables = to.getTables();
		for(XGTableBean table : tables){
			System.out.println("ģʽ����"+table.getSchemaName()+"\t"+"����:"+table.getTableName());
			System.out.println(table.getField());
			for(XGIndexBean index : table.getIndexs()){
				System.out.println("��������"+index.getIndexName());
				for(String s:index.getIndexCol()){
					System.out.println("������"+s);
				}
			}
		}
	}
}
