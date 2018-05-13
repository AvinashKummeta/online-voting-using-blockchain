package chain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
// candidate class
public class Candidateclass {
	public void add_Candidate(int id1,String name1)
	{
		int votes=0;
		try
		{
			// driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//  creating connection
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "avinash");
			// statement obj creation to execute the query
			Statement st = c.createStatement();
			//System.out.println("insert into candidates values("+id1+",'"+name1+"',"+votes+")");
			st.executeQuery("insert into candidates values("+id1+",'"+name1+"',"+votes+")");
			System.out.println("Candidate successfully added to candiadte list");			
			  st.close();
			  c.close();
		}catch(Exception e)
		{
			System.out.println("Exception occured");
		}
	}
}
