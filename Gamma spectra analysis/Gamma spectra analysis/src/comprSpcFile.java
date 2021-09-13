import java.awt.SystemColor;
import java.awt.TextField;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.swing.JLabel;

public class comprSpcFile {
	static short offsetofdataCmpr;
    static short lengthofdataCmpr;
	static int[] tableauEntierCmpr;
	static String nameFileCmpr;
	public static void SpcFile() {
	//---------------Info extracted from spectrum file-------------------
	File file = new File(Mainn.fichierCmpr);
	  FileInputStream fis = null;
	try {
		fis = new FileInputStream(file);
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	  byte [] arr = new byte[(int)file.length()];
	  try {
		fis.read(arr);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	  nameFileCmpr = file.getName();
	  ByteBuffer bb = ByteBuffer.wrap(arr);
	      bb.order(ByteOrder.LITTLE_ENDIAN);
	    
	      
	    //---------offset of data and length of data--------
	      bb.position(64);
	      lengthofdataCmpr = bb.getShort();// Je dois verifier	      
		  bb.position(66);
		  offsetofdataCmpr = bb.getShort(); // Je dois verifier

	    //---------Data of spectrum--------------------------      
		  bb.position(128+16+12+10+10+42+12+8+10+8+128+128+8+12+12+8+1112);
	      tableauEntierCmpr= new int[lengthofdataCmpr-offsetofdataCmpr];
	      
	      for(int j=0;j<lengthofdataCmpr-offsetofdataCmpr;j++) {
	    	  float data = bb.getFloat();
	          int dataa = (int) data;
	    	  tableauEntierCmpr[j]=dataa; 
	    	  //System.out.println("data :  "+tableauEntier[j] );
	    	  
	      }
	      
	   
    //--------------------------------------------------------------------
	
	
	
	}	
}
