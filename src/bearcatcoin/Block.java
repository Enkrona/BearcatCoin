package bearcatcoin;

import java.util.Date;

public class Block {

	//Date d = new Date(Long.parseLong(dateString));
	
	public String hash; // current hash of the present block as a Dig. Sig. 
	public String previousHash;  //  hash of the prior block 
	private String data; // the actual data contained inside the block 
	private long timeStamp; // using epoch seconds 
	private Date formatedDate; 

	
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = getHash();
		this.formatedDate = new Date(timeStamp);
	}
	
	public String getHash() {
		//  Perform the action of generating a new hash of a block based on the data, timestamp and prior block
		//  In essence without the prior hash it wouldn't be a blockchain would it? ;) 
		String calculatedhash = StringUtilHelper.applySha256( 
				previousHash + Long.toString(timeStamp) + data );
		return calculatedhash;
	}
	
}
