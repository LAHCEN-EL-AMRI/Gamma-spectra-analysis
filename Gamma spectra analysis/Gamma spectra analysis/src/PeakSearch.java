import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PeakSearch {	
	static String  peaksearch="";
	static ArrayList<String> centrePic;
    static ArrayList<String> energyPic;
    static ArrayList<String> RangPic;
    static ArrayList<String> AirPic;
//------------------------------------------------------------------------
		    public static void PeakRearchERE() throws IOException {
		    	//Mainn.analyse="marker";
		    	Mainn.mntmPeakSearch.setSelected(true);

		    	mariscoti.CauntsFile.clear();
				mariscoti.ChannelLissee.clear();
				mariscoti.deriveLissee.clear();
				mariscoti.ErreurDeriveLissee.clear();
				centrePic        = new ArrayList<String>(); //pour Marker pic
				energyPic        = new ArrayList<String>(); //pour Marker pic
				AirPic           = new ArrayList<String>(); //pour Marker pic
				RangPic        = new ArrayList<String>(); 
				centrePic.clear();
				energyPic.clear();
				AirPic.clear();
				RangPic.clear();
				mariscoti.ContenuFile();
				mariscoti.deriv();
				
			    String atech =Mainn.tech;
			    float tlim =(float) Float.parseFloat(atech); 
			    
			    String ali =Mainn.li;
			    int lmin = Integer.parseInt(ali); 
			    
			    String alm =Mainn.lm;
			    int lmax = Integer.parseInt(alm); 
			    
			    String As1 =Mainn.As;
			    float ce =(float) Float.parseFloat(As1);// a de energie
			    
			    String Bs1 =Mainn.Bs;
			    float de =(float) Float.parseFloat(Bs1);// b de energie
			    
			    String Cs1 =Mainn.Cs;
			    float c0 =(float) Float.parseFloat(Cs1);// c de energie
			    
			    String Ds1 =Mainn.Ds;
			    float d0 =(float) Float.parseFloat(Ds1);// d de energie
			    
			    String AC1s1 =Mainn.AC1s;
			    float ae =(float) Float.parseFloat(AC1s1);// a de FWHM
			    
			    String AC2s1 =Mainn.AC2s;
			    float be =(float) Float.parseFloat(AC2s1);// b de FWHM
			    
			    String wmey =Mainn.wt;
			    int m0 = Integer.parseInt(wmey); //m
			    
			    String mey =Mainn.mt;
			    int z0 = Integer.parseInt(mey); //z
		    	
		        int j,l=0,i=0;
		        float x0mzc=0,Nsmzc=0,ErrorNsmzc=0,Nmin=0,ErrorNmin=0,Emin = 0,t = 0,enrgder,ND,NDm = 0;
		        DecimalFormat df = new DecimalFormat("000");
		        DecimalFormat df1 = new DecimalFormat("0000");
		        DecimalFormat df2 = new DecimalFormat("0000.00");
		        String lin=System.getProperty("line.separator"); 
		        peaksearch="N° peak    Centroid_Channel    Energy_kev"+lin;
		        String espas="                        ";
		        	
		        for(j=0;j<mariscoti.ChannelLissee.size();j++){
		        		           x0mzc= Float.parseFloat(mariscoti.ChannelLissee.get(j));
		        		           Nsmzc=Float.parseFloat(mariscoti.deriveLissee.get(j));
		        		           ErrorNsmzc=Float.parseFloat(mariscoti.ErreurDeriveLissee.get(j));      
		        		           ND=(float) (Mainn.tableauEntier[j+m0*z0+1]);
		        		           if(Nsmzc<0){
		        		               l=l+1;
		        		               if(Math.abs(Nsmzc)>Nmin){        				
		        		                   Nmin=Math.abs(Nsmzc);  
		        		                   ErrorNmin=Math.abs(ErrorNsmzc);       		                   
		        		                   Emin=x0mzc;   
		        		                   NDm=ND;
		        		                   t=Nmin / ErrorNmin;
		        		                   }}        				
		        		           else{

		        			          if(l>=lmin && l<lmax && t>=tlim){       			
		        				            enrgder=(float) (ce+de*Emin+c0*Math.pow(Emin, 2)+d0*Math.pow(Emin, 3));
		        				            float ecartRang=(float) ((ae+be*Math.sqrt(enrgder)));
		        				            i=i+1;
		        				           
		        				           peaksearch=peaksearch+lin+df.format(i)+espas+df1.format(Emin)+espas+df2.format(enrgder);
		        				           centrePic.add(Float.toString(Emin)); // marker for each peak
		        				           energyPic.add(Float.toString(enrgder));//marker for each peak
		        				           AirPic.add(Float.toString(NDm));//air correspd au centre
		        				           RangPic.add(Float.toString(ecartRang));
		        				             	            
		        				            Nmin=0;	l=0; t=0;      			
		        				       }else {l=0;Nmin=0;t=0;}
		        		
		        			
		        	                   }
		        		           }			    	
		        
		    }
	
	
//-----------------------------------------------------------------------------------	
}
