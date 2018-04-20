package bearcatcoin;

import java.util.Date;

public class Block {
	
	public String hash; // current hash of the present block as a Dig. Sig. 
	public String previousHash;  //  hash of the prior block 
	private String data; // the actual data contained inside the block 
	private long timeStamp; // using epoch seconds 
	private int nonce;
	private Date formatedDate; 

	
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		//  These functions run after data is finalized in the block 
		this.hash = getHash();
		this.formatedDate = new Date(timeStamp);
	}
	
	public String getHash() {
		//  Perform the action of generating a new hash of a block based on the data, timestamp and prior block
		//  In essence without the prior hash it wouldn't be a blockchain would it? ;) 
		String calcHash = StringUtilHelper.applySha256( 
				previousHash + Long.toString(timeStamp) + data + Integer.toString(nonce) );
		return calcHash;
	}
	
	public void mine(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = getHash();
		}
		
		System.out.println("Block Mined!!! : " + hash);
	}
	
}
