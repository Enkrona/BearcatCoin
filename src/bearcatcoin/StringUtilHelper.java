package bearcatcoin;

import java.security.MessageDigest;

public class StringUtilHelper {

	// This code was copied and slightly modified from http://www.baeldung.com/sha-256-hashing-java
	public static String applySha256(String input){		
		//  Attempt to perform the action of hashing the String input in a try / catch and throw an execption if it fails
		try {
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer();
			
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			
			return hexString.toString();
		}
		
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
