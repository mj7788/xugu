package com.xugu.jdbc.output;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestMeta
{

	    //�������    
	    private static String DRIVER = "com.xugu.cloudjdbc.Driver";    
	    //���url    
	    private static String URL = "jdbc:xugu://127.0.0.1:5138/system";    
	    //����������ݿ���û���    
	    private static String USER = "SYSDBA";    
	    //����������ݿ������    
	    private static String PASS = "SYSDBA";    
	  
	    static {    
	        try {     
	            //��ʼ��JDBC���������������ص�jvm��    
	            Class.forName(DRIVER);    
	        } catch (ClassNotFoundException e) {    
	            e.printStackTrace();    
	        }    
	    }    
	      
	    public static Connection getConnection(){    
	        Connection conn = null;    
	        try {     
	            //�������ݿ�    
	              
	           /* 
	            * ���ÿɻ�ȡREMARK��ע��Ϣ 
	           Properties props =new Properties(); 
	           props.put("remarksReporting","true"); 
	           props.put("user", USER); 
	           props.put("password", PASS); 
	           conn =DriverManager.getConnection(URL,props);*/  
	              
	            conn = DriverManager.getConnection(URL,USER,PASS);    
	            conn.setAutoCommit(true);  
	        } catch (SQLException e) {    
	            e.printStackTrace();    
	        }    
	        return conn;    
	    }    
	  
	    //�ر�����  
	    public static void close(Object o){    
	        if (o == null){    
	            return;    
	        }    
	        if (o instanceof ResultSet){    
	            try {    
	                ((ResultSet)o).close();    
	            } catch (SQLException e) {    
	                e.printStackTrace();    
	            }    
	        } else if(o instanceof Statement){    
	            try {    
	                ((Statement)o).close();    
	            } catch (SQLException e) {    
	                e.printStackTrace();    
	            }    
	        } else if (o instanceof Connection){    
	            Connection c = (Connection)o;    
	            try {    
	                if (!c.isClosed()){    
	                    c.close();    
	                }    
	            } catch (SQLException e) {    
	                e.printStackTrace();    
	            }    
	        }      
	    }    
	      
	      
	    public static void close(ResultSet rs, Statement stmt,     
	            Connection conn){    
	        close(rs);    
	        close(stmt);    
	        close(conn);    
	    }    
	      
	    public static void close(ResultSet rs,     
	            Connection conn){    
	        close(rs);     
	        close(conn);    
	    }    
	      
	      
	      
	    /**
	     * @throws SQLException  
	     * @Description: ��ȡ���ݿ������Ϣ 
	     * @author: chenzw  
	     * @CreateTime: 2014-1-27 ����5:09:12  
	     * @throws 
	     */  
	    public static void getDataBaseInfo() throws SQLException {    
	        Connection conn =  getConnection();  
	        ResultSet rs = null;  
	        try{    
	             DatabaseMetaData dbmd = conn.getMetaData();  
	             System.out.println("���ݿ���֪���û�: "+ dbmd.getUserName());      
	             System.out.println("���ݿ��ϵͳ�����Ķ��ŷָ��б�: "+ dbmd.getSystemFunctions());      
	             System.out.println("���ݿ��ʱ������ں����Ķ��ŷָ��б�: "+ dbmd.getTimeDateFunctions());      
	             System.out.println("���ݿ���ַ��������Ķ��ŷָ��б�: "+ dbmd.getStringFunctions());      
	             System.out.println("���ݿ⹩Ӧ������ 'schema' ����ѡ����: "+ dbmd.getSchemaTerm());      
	             System.out.println("���ݿ�URL: " + dbmd.getURL());      
	             System.out.println("�Ƿ�����ֻ��:" + dbmd.isReadOnly());      
	             System.out.println("���ݿ�Ĳ�Ʒ����:" + dbmd.getDatabaseProductName());      
	             System.out.println("���ݿ�İ汾:" + dbmd.getDatabaseProductVersion());      
	             System.out.println("�������������:" + dbmd.getDriverName());      
	             System.out.println("��������İ汾:" + dbmd.getDriverVersion());    
	               
	             System.out.println("���ݿ���ʹ�õı�����");      
	             rs = dbmd.getTableTypes();      
	             while (rs.next()) {      
	                 System.out.println(rs.getString("TABLE_TYPE"));      
	             }      
	        }catch (SQLException e){    
	            e.printStackTrace();    
	        } finally{  
	            rs.close();
	            conn.close();
	        }   
	    }   
	      
	    /**
	     * @throws SQLException  
	     * @Description:������ݿ�������Schemas(��Ӧ��oracle�е�Tablespace) 
	     * @author: chenzw  
	     * @CreateTime: 2014-1-27 ����5:10:35  
	     * @throws 
	     */  
	    public static void getSchemasInfo() throws SQLException{    
	        Connection conn =  getConnection();  
	        ResultSet rs = null;  
	        try{    
	            DatabaseMetaData dbmd = conn.getMetaData();  
	            rs = dbmd.getSchemas();     
	            while (rs.next()){       
	                String tableSchem = rs.getString("TABLE_SCHEM");   
	                System.out.println(tableSchem);       
	            }       
	        } catch (SQLException e){    
	            e.printStackTrace();       
	        } finally{  
	        	rs.close();
	            conn.close();  
	        }    
	    }    
	      
	    /**
	     * @throws SQLException  
	     * @Description: ��ȡ���ݿ������еı���Ϣ 
	     * @author: chenzw  
	     * @CreateTime: 2014-1-27 ����5:08:28  
	     * @throws 
	     */  
	    public static void getTablesList() throws SQLException {    
	        Connection conn =  getConnection();  
	        ResultSet rs = null;  
	        try {    
	            /** 
	             * ������������,ʹ�ÿɻ�ȡ�����REMARK(��ע) 
	             */  
//	            conn.setRemarksReporting(true);   
	            DatabaseMetaData dbmd = conn.getMetaData();  
	            String[] types = { "TABLE" };    
	            rs = dbmd.getTables(null, null, "%", types);    
	            while (rs.next()) {    
	                String tableName = rs.getString("TABLE_NAME");  //����    
	                String tableType = rs.getString("TABLE_TYPE");  //������    
	                String remarks = rs.getString("REMARKS");       //��ע    
	                System.out.println(tableName + " - " + tableType + " - " + remarks);    
	            }    
	        } catch (SQLException e) {    
	            e.printStackTrace();    
	        } finally{  
	        	rs.close();
	            conn.close();  
	        }   
	    }    
	      
	    /**
	     * @throws SQLException  
	     * @Description: ��ȡĳ����Ϣ 
	     * @author: chenzw  
	     * @CreateTime: 2014-1-27 ����3:26:30  
	     * @throws 
	     */  
	    public static void getTablesInfo() throws SQLException{  
	        Connection conn =  getConnection();  
	        ResultSet rs = null;  
	        try {  
	            /** 
	             * ������������,ʹ�ÿɻ�ȡ�����REMARK(��ע) 
	             */  
//	            ((OracleConnection)conn).setRemarksReporting(true);   
	            DatabaseMetaData dbmd = conn.getMetaData();  
	            /** 
	             * ��ȡ���������ʹ�õı�������� 
	             * ����ԭ��:ResultSet getTables(String catalog,String schemaPattern,String tableNamePattern,String[] types); 
	             * catalog - �����ڵ��������;""��ʾ��ȡû��������,null��ʾ��ȡ���������С� 
	             * schema - �����ڵ�ģʽ����(oracle�ж�Ӧ��Tablespace);""��ʾ��ȡû��ģʽ����,null��ʶ��ȡ����ģʽ����; �ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             * tableNamePattern - ������;�ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             * types - ����������; "TABLE"��"VIEW"��"SYSTEM TABLE"��"GLOBAL TEMPORARY"��"LOCAL TEMPORARY"��"ALIAS" �� "SYNONYM";null��ʾ�������еı�����;�ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%");  
	             */  
	            rs = dbmd.getTables(null, null, "CUST_INTER_TF_SERVICE_REQ", new String[]{"TABLE","VIEW"});   
	  
	  
	            while(rs.next()){  
	                 String tableCat = rs.getString("TABLE_CAT");  //�����(��Ϊnull)   
	                 String tableSchemaName = rs.getString("TABLE_SCHEM");//��ģʽ������Ϊ�գ�,��oracle�л�ȡ���������ռ�,�������ݿ�δ֪       
	                 String tableName = rs.getString("TABLE_NAME");  //����    
	                 String tableType = rs.getString("TABLE_TYPE");  //������,���͵������� "TABLE"��"VIEW"��"SYSTEM TABLE"��"GLOBAL TEMPORARY"��"LOCAL TEMPORARY"��"ALIAS" �� "SYNONYM"��  
	                 String remarks = rs.getString("REMARKS");       //��ע    
	                   
	                 System.out.println(tableCat + " - " + tableSchemaName + " - " +tableName + " - " + tableType + " - "   
	                        + remarks);    
	            }  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	        }finally{  
	        	rs.close();
	            conn.close();
	        }  
	    }  
	  
	    /**
	     * @throws SQLException  
	     * @Description: ��ȡ��������Ϣ 
	     * @author: chenzw  
	     * @CreateTime: 2014-1-27 ����5:12:53  
	     * @throws 
	     */  
	    public static void getPrimaryKeysInfo() throws SQLException {    
	        Connection conn =  getConnection();  
	        ResultSet rs = null;  
	        try{    
	            DatabaseMetaData dbmd = conn.getMetaData();  
	            /** 
	             * ��ȡ�Ը�����������е����� 
	             * ����ԭ��:ResultSet getPrimaryKeys(String catalog,String schema,String table); 
	             * catalog - �����ڵ��������;""��ʾ��ȡû��������,null��ʾ��ȡ���������С� 
	             * schema - �����ڵ�ģʽ����(oracle�ж�Ӧ��Tablespace);""��ʾ��ȡû��ģʽ����,null��ʶ��ȡ����ģʽ����; �ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             * table - ������;�ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             */  
	            rs = dbmd.getPrimaryKeys(null, null, "CUST_INTER_TF_SERVICE_REQ");    
	              
	            while (rs.next()){    
	                String tableCat = rs.getString("TABLE_CAT");  //�����(��Ϊnull)   
	                String tableSchemaName = rs.getString("TABLE_SCHEM");//��ģʽ������Ϊ�գ�,��oracle�л�ȡ���������ռ�,�������ݿ�δ֪       
	                String tableName = rs.getString("TABLE_NAME");  //����    
	                String columnName = rs.getString("COLUMN_NAME");//����    
	                short keySeq = rs.getShort("KEY_SEQ");//���к�(������ֵ1��ʾ��һ�е�������ֵ2���������ڵĵڶ���)    
	                String pkName = rs.getString("PK_NAME"); //��������      
	                  
	                System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName + " - " + columnName + " - "  
	                       + keySeq + " - " + pkName);       
	            }    
	        }catch (SQLException e){    
	            e.printStackTrace();    
	        }finally{  
	        	rs.close();
	            conn.close();
	        }  
	    }    
	      
	    /**
	     * @throws SQLException  
	     * @Description: ��ȡ��������Ϣ 
	     * @author: chenzw  
	     * @CreateTime: 2014-1-27 ����5:12:04  
	     * @throws 
	     */  
	    public static void getIndexInfo() throws SQLException {   
	        Connection conn =  getConnection();  
	        ResultSet rs = null;  
	        try{    
	            DatabaseMetaData dbmd = conn.getMetaData();  
	            /** 
	             * ��ȡ�������������ͳ����Ϣ������ 
	             * ����ԭ��:ResultSet getIndexInfo(String catalog,String schema,String table,boolean unique,boolean approximate) 
	             * catalog - �����ڵ��������;""��ʾ��ȡû��������,null��ʾ��ȡ���������С� 
	             * schema - �����ڵ�ģʽ����(oracle�ж�Ӧ��Tablespace);""��ʾ��ȡû��ģʽ����,null��ʶ��ȡ����ģʽ����; �ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             * table - ������;�ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             * unique - �ò���Ϊ trueʱ,������Ψһֵ������; �ò���Ϊ falseʱ,������������; 
	             * approximate - �ò���Ϊtrueʱ,�������ǽӽ�������ֵ����Щ����ֵ�����ֵ;�ò���Ϊ falseʱ,Ҫ�����Ǿ�ȷ���; 
	             */  
	            rs = dbmd.getIndexInfo(null, null, "CUST_INTER_TF_SERVICE_REQ", false, true);    
	            while (rs.next()){    
	                String tableCat = rs.getString("TABLE_CAT");  //�����(��Ϊnull)   
	                String tableSchemaName = rs.getString("TABLE_SCHEM");//��ģʽ������Ϊ�գ�,��oracle�л�ȡ���������ռ�,�������ݿ�δ֪       
	                String tableName = rs.getString("TABLE_NAME");  //����    
	                boolean nonUnique = rs.getBoolean("NON_UNIQUE");// ����ֵ�Ƿ���Բ�Ψһ,TYPEΪ tableIndexStatisticʱ����ֵΪ false;  
	                String indexQualifier = rs.getString("INDEX_QUALIFIER");//������𣨿���Ϊ�գ�,TYPEΪ tableIndexStatistic ʱ�������Ϊ null;   
	                String indexName = rs.getString("INDEX_NAME");//���������� ;TYPEΪ tableIndexStatistic ʱ��������Ϊ null;  
	                /** 
	                 * �������ͣ�  
	                 *  tableIndexStatistic - �˱�ʶ������������һ�𷵻صı�ͳ����Ϣ  
	                 *  tableIndexClustered - ��Ϊ��Ⱥ����  
	                 *  tableIndexHashed - ��Ϊɢ������  
	                 *  tableIndexOther - ��Ϊĳ��������ʽ������  
	                 */  
	                short type = rs.getShort("TYPE");//��������;  
	                short ordinalPosition = rs.getShort("ORDINAL_POSITION");//��������˳���;TYPEΪ tableIndexStatistic ʱ�����к�Ϊ��;  
	                String columnName = rs.getString("COLUMN_NAME");//����;TYPEΪ tableIndexStatisticʱ������Ϊ null;  
	                String ascOrDesc = rs.getString("ASC_OR_DESC");//������˳��:�����ǽ���[A:����; B:����];����������в���֧��,����Ϊ null;TYPEΪ tableIndexStatisticʱ��������Ϊ null;  
	                int cardinality = rs.getInt("CARDINALITY");   //����;TYPEΪ tableIndexStatistic ʱ,���Ǳ��е�����;����,����������Ψһֵ��������     
	                int pages = rs.getInt("PAGES"); //TYPEΪ tableIndexStatisicʱ,�������ڱ��ҳ��,�����������ڵ�ǰ������ҳ����  
	                String filterCondition = rs.getString("FILTER_CONDITION"); //����������,����еĻ�(����Ϊ null)��  
	                  
	                System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName + " - " + nonUnique + " - "   
	                       + indexQualifier + " - " + indexName + " - " + type + " - " + ordinalPosition + " - " + columnName   
	                       + " - " + ascOrDesc + " - " + cardinality + " - " + pages + " - " + filterCondition);       
	            }       
	        } catch (SQLException e){    
	            e.printStackTrace();       
	        } finally{  
	        	rs.close();
	            conn.close();
	        }    
	    }    
	      
	       
	    /**
	     * @throws SQLException  
	     * @Description: ��ȡ������ֵ��Ϣ 
	     * @author: chenzw  
	     * @CreateTime: 2014-1-27 ����2:55:56  
	     * @throws 
	     */  
	    public static void getColumnsInfo() throws SQLException{  
	        Connection conn =  getConnection();  
	        ResultSet rs = null;  
	          
	        try{  
	            /** 
	             * ������������,ʹ�ÿɻ�ȡ���е�REMARK(��ע) 
	             */  
//	            ((OracleConnection)conn).setRemarksReporting(true);   
	            DatabaseMetaData dbmd = conn.getMetaData();  
	            /** 
	             * ��ȡ����ָ�������ʹ�õı��е������� 
	             * ����ԭ��:ResultSet getColumns(String catalog,String schemaPattern,String tableNamePattern,String columnNamePattern) 
	             * catalog - �����ڵ��������;""��ʾ��ȡû��������,null��ʾ��ȡ���������С� 
	             * schema - �����ڵ�ģʽ����(oracle�ж�Ӧ��Tablespace);""��ʾ��ȡû��ģʽ����,null��ʶ��ȡ����ģʽ����; �ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             * tableNamePattern - ������;�ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             * columnNamePattern - ������; ""��ʾ��ȡ����Ϊ""����(��Ȼ��ȡ����);null��ʾ��ȡ���е���;�ɰ������ַ�ͨ���("_"),����ַ�ͨ���("%"); 
	             */  
	            rs =dbmd.getColumns(null, null, "CUST_INTER_TF_SERVICE_REQ", null);  
	              
	            while(rs.next()){  
	                String tableCat = rs.getString("TABLE_CAT");  //����𣨿���Ϊ�գ�                    
	                String tableSchemaName = rs.getString("TABLE_SCHEM");  //��ģʽ������Ϊ�գ�,��oracle�л�ȡ���������ռ�,�������ݿ�δ֪       
	                String tableName_ = rs.getString("TABLE_NAME");  //����    
	                String columnName = rs.getString("COLUMN_NAME");  //����    
	                int dataType = rs.getInt("DATA_TYPE");     //��Ӧ��java.sql.Types��SQL����(������ID)       
	                String dataTypeName = rs.getString("TYPE_NAME");  //java.sql.Types��������(����������)  
	                int columnSize = rs.getInt("COLUMN_SIZE");  //�д�С    
	                int decimalDigits = rs.getInt("DECIMAL_DIGITS");  //С��λ��   
	                int numPrecRadix = rs.getInt("NUM_PREC_RADIX");  //������ͨ����10��2�� --δ֪  
	                /** 
	                 *  0 (columnNoNulls) - ���в�����Ϊ�� 
	                 *  1 (columnNullable) - ��������Ϊ�� 
	                 *  2 (columnNullableUnknown) - ��ȷ�������Ƿ�Ϊ�� 
	                 */  
	                int nullAble = rs.getInt("NULLABLE");  //�Ƿ�����Ϊnull    
	                String remarks = rs.getString("REMARKS");  //������    
	                String columnDef = rs.getString("COLUMN_DEF");  //Ĭ��ֵ    
	                int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");    // ���� char ���ͣ��ó��������е�����ֽ���   
	                int ordinalPosition = rs.getInt("ORDINAL_POSITION");   //�����е���������1��ʼ��    
	                /**  
	                 * ISO��������ȷ��ĳһ�е��Ƿ��Ϊ��(��ͬ��NULLABLE��ֵ:[ 0:'YES'; 1:'NO'; 2:''; ]) 
	                 * YES -- ���п����п�ֵ;  
	                 * NO -- ���в���Ϊ��; 
	                 * ���ַ���--- ��֪�������Ƿ��Ϊ�� 
	                 */    
	                String isNullAble = rs.getString("IS_NULLABLE");    
	                    
	                /**  
	                 * ָʾ�����Ƿ����Զ�����  
	                 * YES -- �������Զ������� 
	                 * NO -- ���в����Զ����� 
	                 * ���ִ�--- ����ȷ�������Ƿ��Զ����� 
	                 */    
	                //String isAutoincrement = rs.getString("IS_AUTOINCREMENT");   //�ò������Ա���      
	                System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName_ + " - " + columnName +   
	                        " - " + dataType + " - " + dataTypeName + " - " + columnSize + " - " + decimalDigits + " - "   
	                        + numPrecRadix + " - " + nullAble + " - " + remarks + " - " + columnDef + " - " + charOctetLength  
	                        + " - " + ordinalPosition + " - " + isNullAble );   
	                  
	            }  
	        }catch(SQLException ex){  
	            ex.printStackTrace();  
	        }finally{  
	        	rs.close();
	            conn.close();
	        }  
	    }  
	      
	    /**
	     * @throws SQLException  
	     * @Description: TODO 
	     * @author: chenzw  
	     * @CreateTime: 2014-1-17 ����2:47:45 
	     * @param args  
	     * @throws  
	     */  
	    public static void main(String[] args) throws SQLException {  
	        getDataBaseInfo();  //��ȡ���ݿ���Ϣ  
	        getSchemasInfo(); //��ȡ���ݿ�����Schema  
	        getTablesList();  //��ȡĳ�û������еı�  
	        getTablesInfo();  //��ȡ����Ϣ  
	        getPrimaryKeysInfo(); //��ȡ��������Ϣ  
	        getIndexInfo();  //��ȡ��������Ϣ  
	        getColumnsInfo(); //��ȡ������ֵ��Ϣ  
	    }  
	}  

