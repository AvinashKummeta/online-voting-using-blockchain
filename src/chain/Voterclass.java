package chain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// voterclass
public class Voterclass {
	
	public void add_Voter(int id1,String name1,String pno,String mail)
	{
		try
		{
			// driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//  creating connection
			
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "avinash");
			
			// statement obj creation to execute the query
			Statement st = c.createStatement();
			//System.out.println("insert into voters values("+id1+",'"+name1+"','"+pno+"','"+mail+"')");
			st.executeQuery("insert into voters values("+id1+",'"+name1+"','"+pno+"','"+mail+"')");
			System.out.println("Voter successfully added to voters list");			
  
			  st.close();
			  c.close();
		}catch(Exception e)
		{
			System.out.println("Exception occured");
		}
	}

}
