package bearcatcoin;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;;

public class BearcatCoin {

	public static ArrayList<Block> bearcatChain = new ArrayList<Block>();
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		bearcatChain.add(new Block("Hi im the first block", "0"));		
		TimeUnit.SECONDS.sleep(1);
		bearcatChain.add(new Block("Yo im the second block",bearcatChain.get(bearcatChain.size()-1).hash)); 
		TimeUnit.SECONDS.sleep(1);
		bearcatChain.add(new Block("Hey im the third block",bearcatChain.get(bearcatChain.size()-1).hash));
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(bearcatChain);		
		System.out.println(blockchainJson);
	}
	
public static Boolean checkValidChain(){
		
		Block currentBlock;    
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//  this will loop through all the blocks within the bearcatChain to check validity
		for(Block blocks : bearcatChain) {
			currentBlock = bearcatChain.get(i);
			previousBlock = bearcatChain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.getHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			
		}
		return true;
	}

}
