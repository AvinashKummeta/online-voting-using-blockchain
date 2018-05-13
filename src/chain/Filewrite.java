package chain;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class Filewrite {

	public void wirte_To_Files(String hash1,String hash2,String hashvalue)
	
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
			
			ResultSet rs = st.executeQuery("select id from voters");
			while(rs.next())
			{
			  //  System.out.println(rs.getInt(1)+".txt");   
				String fname = rs.getInt(1)+".txt";        // list of files with file name as their ids
				FileWriter fileWriter = new FileWriter(fname,true);
				BufferedWriter out = new BufferedWriter(fileWriter); 
				//out.write(hash1+" ");   // prev hash
				out.write(hashvalue+" ");
				out.write(hash2+" ");   // present hash of the block
				out.newLine();    
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

