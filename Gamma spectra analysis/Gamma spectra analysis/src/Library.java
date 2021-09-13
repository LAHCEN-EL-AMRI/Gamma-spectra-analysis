import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
	static ArrayList<String> radinucleid;
	static ArrayList<String> nucleidEnergy;
	static ArrayList<String> nucIntensity;   
    static ArrayList<String> hilflife;
    
	
	private static Scanner s;

	public static void Librar() throws IOException {
		
		radinucleid    = new ArrayList<String>(); 		
		nucleidEnergy  = new ArrayList<String>();
		nucIntensity   = new ArrayList<String>();
		hilflife       = new ArrayList<String>(); 
		radinucleid.clear();
		nucleidEnergy.clear();
		nucIntensity.clear();
		hilflife.clear();
	
		 s = new Scanner(new File(Mainn.fieldIdentif1.getText()));		
		 
	      while (s.hasNext()){
	    	
	    	  radinucleid.add(s.next());	    	  
	    	  nucleidEnergy.add(s.next());
	    	  nucIntensity.add(s.next());
	    	  hilflife.add(s.next()+" "+s.next());	    	  
	    	 // s.nextFloat();	    	  
	      }
	    
	}
	
	
}
