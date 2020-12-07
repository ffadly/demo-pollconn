package co.id.dasa.poolconn.demopollconn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
//import service.Configuration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import database pooling connection 
import org.apache.commons.dbcp2.BasicDataSource;

@SpringBootApplication
public class DemoPollconnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoPollconnApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

		return args -> {

			MyThread myThread = new MyThread();
			myThread.threadname = "Thread ke 1";
			myThread.start();

			MyThread myThread2 = new MyThread();
			myThread2.threadname = "Thread ke 2";
			myThread2.start();

			MyThread myThread3 = new MyThread();
			myThread3.threadname = "Thread ke 3";
			myThread3.start();

			MyThread myThread4 = new MyThread();
			myThread4.threadname = "Thread ke 4";
			myThread4.start();
		};
	}

}

class MyThread extends Thread {

	public String threadname = "";
	public void run() {
		Connection connection = null;
		PreparedStatement prepStmt = null;
		int a= 0;
		while (true) {
			a++;
			try {
				String selectSQL = "SELECT count(*) as jumlah from database_connections;";
				connection = ConnectionSta.getInstance().getDataSource().getConnection();
				prepStmt = connection.prepareStatement(selectSQL);
				ResultSet rs = prepStmt.executeQuery();
				while (rs.next()) {
					System.out.println("Jumlah DB: " + rs.getInt("jumlah"));

				}
				System.out.println("NumActive: " + ConnectionSta.getInstance().getDataSource().getNumActive());
				System.out.println("NumIdle: " + ConnectionSta.getInstance().getDataSource().getNumIdle());
				System.out.println("call ke : " + a + " di thread "+threadname);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (prepStmt != null) {
					try {
						prepStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			if(connection != null){
			  	try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		  }
		}
    }
  }