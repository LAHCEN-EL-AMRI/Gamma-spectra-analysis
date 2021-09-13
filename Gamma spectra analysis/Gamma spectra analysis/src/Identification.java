
public class Identification {
	public static void identif() {
	//-------------for idntification ----------
	Mainn.radinucleidNuc.clear(); Mainn.nucleidEnergyNuc.clear();
	Mainn.nucIntensityNuc.clear(); Mainn.hilflifeNuc.clear();
	
	String pth = Mainn.fieldIdentif1.getText();
	Mainn.fieldIdentif1.setText(pth);						
	String tlr = Mainn.fieldIdentif2.getText();
	Mainn.fieldIdentif2.setText(tlr);
	
	float plusProche, crrespndEnergy0=0; 
	int size=0;
	if(Mainn.analyse=="g3") {size=treatmentLog.EnergyAxisPeakCmpr.size();}
	if(Mainn.analyse=="g") {size=treatment1.EnergyAxisPeak.size();}
	for(int jS=0;jS<size;jS++) {
	  String nceid =""; String enrgyNuc =""; String intensityNuc ="";
	  String hilflifeeNuc =""; float plusProche1 = 1000;
	  
	  if(Mainn.analyse=="g3") {crrespndEnergy0 =	Float.parseFloat(treatmentLog.EnergyAxisPeakCmpr.get(jS));}
	  if(Mainn.analyse=="g") {crrespndEnergy0 =	Float.parseFloat(treatment1.EnergyAxisPeak.get(jS));}
	  
	  for(int jn=0;jn<Library.nucleidEnergy.size();jn++) {
		String radinucleid0=Library.radinucleid.get(jn);  
		float enrgNuceild0 = Float.parseFloat(Library.nucleidEnergy.get(jn));		    				
		String nucIntensity0=Library.nucIntensity.get(jn);
		String hilflife0=Library.hilflife.get(jn);		    				
		float tole = Float.parseFloat(Mainn.fieldIdentif2.getText());
		if(crrespndEnergy0<=enrgNuceild0+tole && crrespndEnergy0>=enrgNuceild0-tole ) {
			plusProche=Math.abs(crrespndEnergy0-enrgNuceild0);
			if(plusProche<plusProche1) {
				nceid=radinucleid0;	 enrgyNuc =Float.toString(enrgNuceild0);
				intensityNuc =nucIntensity0; hilflifeeNuc =hilflife0;
				plusProche1=plusProche;
			}		
		}			    				  
	  }if(nceid.equals("")) {
		  Mainn.radinucleidNuc.add(nceid); Mainn.nucleidEnergyNuc.add(enrgyNuc);
		  Mainn.nucIntensityNuc.add(intensityNuc); Mainn.hilflifeNuc.add(hilflifeeNuc);
		  }else {
			  Mainn.radinucleidNuc.add(nceid); Mainn.nucleidEnergyNuc.add(enrgyNuc+" Kev");
			  Mainn.nucIntensityNuc.add(intensityNuc+" %"); Mainn.hilflifeNuc.add(hilflifeeNuc);
		  }}
	//-----------------------------------------
}
}
