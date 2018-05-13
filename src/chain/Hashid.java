package chain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Hashid {
	
	//public static void main(String[] args){
	// class to create hash for each voters in database
	public void connectToDb()
	{
		try
		{
			
			// driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//  creating connection
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "avinash");
			// statement obj creation to execute the query
			Statement st = c.createStatement();
			//System.out.println("statement created");
			
			String newtable = "create table hashedids(hid char(256),checked int)";   // table containing hash of voters
			st.executeQuery(newtable);
			ResultSet rs = st.executeQuery("select * from voters");
			// obj class for sha-256 hashing
			Hashit h = new Hashit();
			
			while(rs.next())
			{
				Statement s = c.createStatement();
				int id1 = rs.getInt(1);
				//System.out.println(rs.getInt(1));
				String h1 = h.hashing(String.valueOf(id1));
				//System.out.println("insert into hashedids values('"+h1+"',1)");
				FileWriter fileWriter1 = new FileWriter("file1.txt",true);
				BufferedWriter out = new BufferedWriter(fileWriter1);
    			out.write(h1+" ");
    			//out.newLine();
    			out.close();
			    s.executeQuery("insert into hashedids values('"+h1+"',1)");	
				
			 }
			rs = st.executeQuery("select email from voters");
			while(rs.next())
			{
				FileWriter fileWriter2 = new FileWriter("file2.txt",true);
				BufferedWriter out = new BufferedWriter(fileWriter2);
    			out.write(rs.getString(1)+" ");
    			//out.newLine();
    			out.close();
			}
			  rs.close();	  
			  st.close();
			  c.close();
		}catch(Exception e)
		{
			System.out.println("Exception occured");
		}
	}
}
