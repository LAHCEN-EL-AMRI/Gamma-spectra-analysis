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

public class comprChnFile {
	static short offsetofdataCmpr;
    static short lengthofdataCmpr;
	static int[] tableauEntierCmpr;
	static String nameFileCmpr;
	public static void ChnFile() {
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
	      bb.position(28);
	      offsetofdataCmpr = bb.getShort();
	     // Mainn.offsetofdataS = Short.toString(offsetofdata);
	      lengthofdataCmpr = bb.getShort();
	     // Mainn.lengthofdataS = Short.toString(lengthofdata);
	      //System.out.println("offsetofdata   :"+offsetofdataS+"        "+"lengthofdata   :"+lengthofdataS );
	      
	    //---------Data of data--------------------------      
	      
	      tableauEntierCmpr= new int[lengthofdataCmpr-offsetofdataCmpr];
	      
	      for(int j=0;j<lengthofdataCmpr-offsetofdataCmpr;j++) {
	    	  int data =bb.getInt();
	    	  tableauEntierCmpr[j]=data; 
	    	  //System.out.println("data :  "+tableauEntier[j] );
	    	  
	      }
	      
	   
    //--------------------------------------------------------------------
	
	
	
	}	
}
