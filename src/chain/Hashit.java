package chain;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// sha-256
public class Hashit {

	public static String hashing(String pass) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(pass.getBytes());
        byte b[] = md.digest();	
        StringBuffer sb = new StringBuffer();
        for(byte b1:b)
        {
        	sb.append(Integer.toHexString(b1 & 0xff).toString());
        }
        return sb.toString();
	}
	
	/*public static void main(String args[])
	{
		Hashit ob = new Hashit();
		String pass = "hello";
		try
		{
			System.out.println(ob.hashing(pass));	
		}catch(Exception e)
		{
			System.out.println("error");
		}
		
	}*/
}
