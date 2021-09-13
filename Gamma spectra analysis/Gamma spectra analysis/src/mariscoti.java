import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class mariscoti {	
	static ArrayList<String> CauntsFile=new ArrayList<String>();
	static ArrayList<String> ChannelLissee=new ArrayList<String>();
	static ArrayList<String> deriveLissee=new ArrayList<String>();
	static ArrayList<String> ErreurDeriveLissee=new ArrayList<String>();

	private static BufferedReader brc;
	static float Nk;	
	//-------lire le contenu de fichier------------------------------
			public static  void ContenuFile() throws IOException{				
				   
				      
				 for(int j=0;j<Mainn.lengthofdata-Mainn.offsetofdata;j++) {			  
					 int data = Mainn.tableauEntier[j];	
				  String xValue = Integer.toString(data);				 
		            CauntsFile.add(xValue);				  	  			  
			  }
			        
			}
			
			//--------------------------------------------------------------
	public static void deriv() throws IOException{
		 String mfenetre =Mainn.wt;//m
		 int m_fenetre = Integer.parseInt(mfenetre); 
	    
	     String zfois =Mainn.mt;//z
	     int z_fois = Integer.parseInt(zfois); 
	    
	   //--------------- la derivée seconde lissée par z et m----------------
        for (int i = z_fois*m_fenetre+1; i < CauntsFile.size()-z_fois*m_fenetre-1; i++) {
            float valeurFinal =(float) calculDeriv(z_fois, m_fenetre, i);
            
            ChannelLissee.add(Integer.toString(i));
            deriveLissee.add(Float.toString(valeurFinal));
            
        }
        //------------------erreur de la derivée seconde lissée-----------------
        for (int f = z_fois*m_fenetre+1; f < CauntsFile.size()-z_fois*m_fenetre-1; f++) {
            float valeurFinalError = (float) Math.sqrt(calculDerivError(z_fois, m_fenetre, f));
            ErreurDeriveLissee.add(Float.toString(valeurFinalError));
            
        }   
	}
	
	//-----------------la derive seconde lissée------------------------------------
	 public static long somme = 0;

	    public static long calculDeriv(int z, int m, int i) throws IOException { 
	        long valeur = 0;
	        for (int k = i - z * m - 1; k <= i + z * m + 1; k++) {
	            somme = 0 ;
	            calculConstantC(i, k, z, m);
	            Nk= Float.parseFloat(CauntsFile.get(k));
	            valeur += somme * Nk;  
	        }
	        return valeur;   
	    }

	    public static void calculConstantC(int i, int k, int z, int m) {
	        if (z == 0) {
	            if (Math.abs(k - i) >= 2) {
	                somme += 0;
	               
	            }
	            if (Math.abs(k - i) == 1) {
	                somme += 1;
	               
	            }
	            if (Math.abs(k - i) == 0) {
	                somme -= 2;
	               
	            }

	        } else {
	            for (int j = i - m; j <= i + m; j++) {
	                calculConstantC(j, k, z - 1, m);
	            }
	        }
	    }
	
	
	//--------------------------------Ereur de la derivée seconde lissée------------------
		 public static long sommeError = 0;

		    public static long calculDerivError(int ze, int me, int f) throws IOException {
		        long valeurError = 0;
		        for (int ke = f - ze * me - 1; ke <= f + ze * me + 1; ke++) {
		            sommeError = 0 ;
		            calculConstantCError(f, ke, ze, me);
		             Nk= Float.parseFloat(CauntsFile.get(ke));
		            valeurError += sommeError * Nk; 
		        }
		        return valeurError; 
		    }

		    public static void calculConstantCError(int f, int ke, int ze, int me) {
		        if (ze == 0) {
		            if (Math.abs(ke - f) >= 2) {
		                sommeError += 0;
		               
		            }
		            if (Math.abs(ke - f) == 1) {
		                sommeError += 1;
		               
		            }
		            if (Math.abs(ke - f) == 0) {
		            	sommeError += 4;
		               
		            }

		        } else {
		            for (int je = f - me; je <= f + me; je++) {
		                calculConstantCError(je, ke, ze - 1, me);
		            }
		        }
		    }
//-----------------------------------------------------------------------------------	
}
