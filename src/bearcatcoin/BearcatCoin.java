package bearcatcoin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.GsonBuilder;;

public class BearcatCoin {

	public static ArrayList<Block> bearcatChain = new ArrayList<Block>();
	
    //  This is a decently "difficult" difficulty for testing as it must solve for 6 zeros in the nonce 
	// It is a static difficulty for now as testing var diffs would be hard :P 
	public static int diff = 6;  
	
	public static void main(String[] args) throws IOException {
		bearcatChain.add(new Block("Hello World", "0"));	
		System.out.println("Trying to discover the genesis block... ");
		bearcatChain.get(0).mine(diff);
		
		//fixedChain(3);
		interactiveChain();
		
		System.out.println("Is Blockchain Valid? " + checkValidChain());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(bearcatChain);	
		System.out.println("The bearcat coin chain: ");
		System.out.println(blockchainJson);
	}
	
	//  This will generate a blockchain based on a fixed size
	public static void fixedChain(int size){
		for (int i = 1; i < size; i++){
			bearcatChain.add(new Block(Integer.toString(i), bearcatChain.get(i-1).hash));
			System.out.println("Attempting to mine block: " + i);
			bearcatChain.get(i).mine(diff);
		}
	}
	
	public static void interactiveChain() throws IOException{
		Boolean stop = false; // stop condition 
		Scanner inputReader = new Scanner(System.in); 
		String data; 
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("How large would you like the blockchain to be: ");
		
		try{
            int size = Integer.parseInt(br.readLine());
            int iter = 1;
            while (stop != true){
            	System.out.println("What data would you like to put in the current block? (Enter exit to quit) ");
    			data = inputReader.nextLine();
    			
    			if (data.equals("exit") || iter == size){
    				stop = true; 
    			}
    			
    			else{
					bearcatChain.add(new Block(data, bearcatChain.get(iter - 1).hash));
					System.out.println("Attempting to mine block: " + iter);
					bearcatChain.get(iter).mine(diff);
    			}
    			
    			iter++;
            	
    		}
        }
		
		catch(NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
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
			if(!previousBlock.hash.equals(n.previousHash) ) {
				if (blockNum == 2){
					//  null "if" to catch silly issues with iterators
					System.out.println("No issues found with Genesis Block!");
				}
				
				else {
					System.out.println("Previous Hashes not equal. Hashes are output below: ");
					System.out.println("Conflicting current block previous hash: " + currentBlock.previousHash);
					System.out.println("Conflicting actual previous block hash: " + previousBlock.hash);
					return false;
				}
				
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
