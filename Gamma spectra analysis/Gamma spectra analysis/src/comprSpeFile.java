import static org.junit.Assert.assertArrayEquals;
import java.awt.SystemColor;
import java.awt.TextField;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class comprSpeFile {
	static short offsetofdataCmpr;
    static short lengthofdataCmpr;
	static int[] tableauEntierCmpr;
	static String nameFileCmpr;
	private static Scanner s;

	public static void SpeFile() throws IOException {
	//---------------Info extracted from spectrum file-------------------
	//File file = new File(Mainn.fichierCmpr);
	File file =	new File(Mainn.fichierCmpr);
    s = new Scanner(file);

	  nameFileCmpr = file.getName();

//---------$DATA:--------------------------  
	  String $DATA;
	     for(int j=0;j<80000;j++) {$DATA=s.next();if($DATA.equals("$DATA:")) {break;}}
	      
	      offsetofdataCmpr = s.nextShort();
	      lengthofdataCmpr = s.nextShort();
	      tableauEntierCmpr= new int[lengthofdataCmpr-offsetofdataCmpr]; 
	      for(int j=0;j<lengthofdataCmpr-offsetofdataCmpr;j++) {
	    	  int data =s.nextInt();
	    	  tableauEntierCmpr[j]=data; 
	    	  //System.out.println("data :  "+tableauEntier[j] );
	    	  
	      }
	}	
}
