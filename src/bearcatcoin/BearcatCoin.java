package bearcatcoin;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.GsonBuilder;;

public class BearcatCoin {

	public static ArrayList<Block> bearcatChain = new ArrayList<Block>();
	
    //  This is a decently "difficult" difficulty for testing as it must solve for 6 zeros in the nonce 
	// It is a static difficulty for now as testing var diffs would be hard :P 
	public static int diff = 6;  
	
	public static int chainSize = 2;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		bearcatChain.add(new Block("Hi im the first block", "0"));	
		System.out.println("Trying to discover the genesis block... ");
		bearcatChain.get(0).mine(diff);
		
		for (int i = 1; i < chainSize; i++){
			
			bearcatChain.add(new Block(Integer.toString(i), bearcatChain.get(bearcatChain.size()-1).hash));
			System.out.println("Attempting to mine block: " + i);
			bearcatChain.get(i).mine(diff);
			TimeUnit.SECONDS.sleep(1);
		}
		
		System.out.println("Is Blockchain Valid? " + checkValidChain());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(bearcatChain);	
		System.out.println("The bearcat coin chain: ");
		System.out.println(blockchainJson);
	}
	
	public static Boolean checkValidChain(){
		Block currentBlock;    
		Block previousBlock;
		int blockNum = 1;  //  Start at the genesis block
		String hashTarget = new String(new char[diff]).replace('\0', '0');
		
		//  this will loop through all the blocks within the bearcatChain to check validity
		for(Block n : bearcatChain) {
			currentBlock = n;
			previousBlock = bearcatChain.get(blockNum - 1);
			
			//  This compares the actual hash and calculated hash we generate:
			if(!currentBlock.hash.equals(currentBlock.getHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			
			//  This compares the previous hash and actual previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			
			//  This checks if the hash generated is for an unsolved block (i.e. orphaned block) 
			if(!currentBlock.hash.substring( 0, diff).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			
			//  iterate through the blocks 
			blockNum++;
			
		}
		return true;
	}

}
