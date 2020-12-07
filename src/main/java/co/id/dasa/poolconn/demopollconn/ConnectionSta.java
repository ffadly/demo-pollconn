package co.id.dasa.poolconn.demopollconn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//import database pooling connection 
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author 1602116408
 */
public class ConnectionSta {

    private Connection connection = null;

    private String database = "database";
    private String hostname = "localhost";
    private String portnum = "3306";
    private String sid = "";
    //jdbc:mysql://DB_HOST:3306/reports
     //strurl+hostname+:+portnum+/+database
    private String strurl = "jdbc:mysql://";
    private String strdrv = "com.mysql.cj.jdbc.Driver";
    private String user = "root";
    private String pass = "Password";
    
    private String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    // static variable single_instance of type Singleton 
    private static ConnectionSta single_connection_sta = null; 

    //data source for connection pool
    private static BasicDataSource dataSource;
 
   
    public static ConnectionSta getInstance() 
    { 
        if (single_connection_sta == null) 
            single_connection_sta = new ConnectionSta(); 
  
        return single_connection_sta; 
    } 


    private ConnectionSta() {
        //dummy singleton 
    }

    public void loadProperties(){
       // Properties configProps = Configuration.loadProperties();
        // this.hostname = configProps.getProperty("sta_hostname");
        // this.portnum = configProps.getProperty("sta_portnum");
        // this.portnum = portnum == null ? "1521" : portnum;
        // this.sid = configProps.getProperty("sta_sid");
        // this.user = configProps.getProperty("sta_user");
        // this.pass = configProps.getProperty("sta_pass");
    }

    public BasicDataSource getDataSource() throws ClassNotFoundException
    {
        Class.forName(strdrv);
    
        if (dataSource == null)
        {
            BasicDataSource ds = new BasicDataSource();
           // strurl = strurl + hostname + ":" + portnum + ":" + sid;
            strurl = strurl+hostname+":"+portnum+"/"+database+timezone;
            ds.setDriverClassName(strdrv);
            ds.setUrl(strurl);
            ds.setUsername(user);
            ds.setPassword(pass);
 

            // Parameters for connection pooling
            ds.setMinIdle(10);
            ds.setMaxIdle(30);
             ds.setInitialSize(10);
            // ds.setMaxTotal(30);	
            ds.setMaxOpenPreparedStatements(30);
 
            dataSource = ds;
        }
        return dataSource;
    }

    public String getHostname() {
        return hostname;
    }

    

}
