package chain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Blockchain {
	
	public static void main(String[] args)
	{
        int n = 0,cand=0;
        
        System.out.println("Select one of the below");
        System.out.println("1->admin");  
        System.out.println("2->user");
        Scanner scanner = new Scanner(System.in);
        int selected = scanner.nextInt();
        // admin
        if(selected == 1)
        {
        	System.out.println("Enter  the password");
        	String pass = scanner.next();
        	if(pass.equals("admin@123")){              // pass for admin
 	        	while(true)
	        	{
	        		System.out.println("Do you want to add an voter or Candiadate");
	        		System.out.println("1->yes   2->no ");
	        		int sel = scanner.nextInt();
	        		if(sel==2)
	        		{
	        			break;
	        		}
	        		System.out.println("Select one of these");
	        		System.out.println("1->voter   2->candiadte");
	        	    int temp1 = scanner.nextInt();
	        	   // adding the voter to voters list 
	        	    if(temp1==1)
	        	    {
		        			Voterclass vc = new Voterclass();     // object of voter class
		        			System.out.println("Enter the id of the voter");
		        			int id = scanner.nextInt();
		        			System.out.println("Enter the name of the voter");
		        			String name = scanner.next();
		        			System.out.println("Enter the pno of the voter");
		        			String phno = scanner.next();
		        			System.out.println("Enter the gmail of the voter");
		        			String gmail = scanner.next();
		        			vc.add_Voter(id, name, phno, gmail);
		        		
	        	    }
	        	    else
	        	    {
	        	    	// adding candidate to candidates list
	        	    	Candidateclass cc = new Candidateclass();    // object of candidateclass
	        	    	System.out.println("Enter the id of the candiadte");
	        			int id = scanner.nextInt();
	        			System.out.println("Enter the name of the candidate");
	        			String name = scanner.next();
	        			cc.add_Candidate(id, name);  
	        	    }
	        	}
             } 	
         }
         
            // generating private keys for the voters            
        	Hashid hd = new Hashid();
        	hd.connectToDb();
        	
        	try
    		{
    			// driver class
    			Class.forName("oracle.jdbc.driver.OracleDriver");
    			//  creating connection
    			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "avinash");
    			
    			// statement obj creation to execute the query
    			Statement stat = c.createStatement();
    			//System.out.println("statement created");
    			ResultSet v = stat.executeQuery("select count(*) as value from voters");  //n -> no of voters
    			v.next();
    			n = v.getInt(1);     // n -> no of voters
    			v = stat.executeQuery("select count(*) as value from candidates"); // cand -> no of candidates
    			v.next();
    			cand = v.getInt(1);
    			  v.close();	  
    			  stat.close();
    			  c.close();
    		}catch(Exception e)
    		{
    			System.out.println("Exception occured");
    		}
        	
        	// no of voters
        	
        	System.out.println("No of voters - > "+n);
        	System.out.println("No of candidates - > "+cand);
        	
        	// array of candidates
          String candidates[] = new String[cand];   // store the candidates in strings
          int vote_count[] = new 	int[cand];
         try
		 {
			// driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//  creating connection
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "avinash");
			// statement obj creation to execute the query
			Statement stat = c.createStatement();
			ResultSet v = stat.executeQuery("select * from candidates");
			int temp2=0;
			while(v.next())
			{
				candidates[temp2] = v.getString(2);
				vote_count[temp2] = 0;
				//System.out.println(candidates[temp2]+" "+vote_count[temp2]);
				temp2++;
			}
			  v.close();	  
			  stat.close();
			  c.close();
		}catch(Exception e)
		{
			System.out.println("Exception occured");
		} 
        	int x=0;
    		Block[] b = new Block[n];
    		//Scanner scanner = new Scanner(System.in);
            // sha-256   
    		Hashit hs = new Hashit();
    		//System.out.println("Enter hash of genesis block");
    		//String pq = scanner.next();
    		while(x<n)
    		{
    			System.out.println("Do you want to vote");
    			System.out.println("1->yes  2->no");
    			int temp = scanner.nextInt();   
    			if(temp==2)
    			{
    				System.out.println("Voting has completed");
    				System.out.println("results");
    				for(int i=0;i<cand;i++)
    				{
    					System.out.println(candidates[i]+" got "+vote_count[i]+" votes");
    				}
    				// results;
    				break;
    			}
    			//System.out.println("Enter your name\n");
    			//String voter_name = scanner.next();
    			System.out.println("Enter your privatekey\n");
    			String hash_value = scanner.next();
    			int f=0;    // flag
    			
    			try
    			{
    				Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "avinash");
    			  	Statement stat = connection.createStatement();
    			  	//System.out.println("select * from hashedids where hid='"+hash_value+"'");
    			  	ResultSet rs = stat.executeQuery("select * from hashedids where hid='"+hash_value+"'");
    			  	if(!rs.next())
    			  	{
    			  		System.out.println("You are not registered");
    			  	}else
    			  	{
    			  		
    			  		//System.out.println(rs.getInt(2));
    			  		if(rs.getInt(2)==0)
    			  		{
    			  			System.out.println("you have already voted");
    			  		}else
    			  		{
    			  			f=1;
    			  			System.out.println("Choose any one candidate");
    			  			
    			  			for(int i=0;i<cand;i++)
    			  			{
    			  				System.out.println(i+"->"+candidates[i]);
    			  			}
    			  			
    			  			int vote = scanner.nextInt();
    			  			vote_count[vote]++;
    			  			if(x==0)       // genesis block
    						{
    			  				
    			  				b[0] = new Block();
    							b[0].prev_hash = "0000000";
    			  				//b[0].prev_hash = pq;
    			  				//System.out.println(b[0].prev_hash);
    							b[0].data = candidates[vote];
    							System.out.println("voted to "+b[0].data);
    							b[0].hash = hs.hashing(b[0].data+b[0].prev_hash);
    						}else
    						{
    							b[x] = new Block();
    							System.out.println("entered");
    							b[x].prev_hash = b[x-1].hash;
    					    	b[x].data=candidates[vote];
    							System.out.println("voted to "+b[x].data);
    							b[x].hash = hs.hashing(b[x].data+b[x].prev_hash);      // sha-256 function should be used
    						
    						}
    			  			//System.out.println("update hashedids set checked=0 where hid ='"+hash_value +"'");
    	    			  	stat.executeUpdate("update hashedids set checked=0 where hid ='"+hash_value +"'");	
    	    			  	System.out.println("updated");
    	    			  	Filewrite obj = new Filewrite();
    	        			if(f==1)
    	        			{
    	        				obj.wirte_To_Files(b[x].prev_hash, b[x].hash,hash_value);	
    	        			}
    	    			  	x++;
    			  		}
    			  	}	
    			}catch(Exception e){
    				System.out.println("error occurred");
    			}
    			
    		}		
	}
}
