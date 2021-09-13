

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.general.Series;
import org.jfree.data.xy.*;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import java.awt.TextField;

import javax.swing.SwingConstants;

public class treatment1 extends JPanel {	
	
	       static XYPlot plot;
           XYLineAndShapeRenderer renderer;
    static ArrayList<String> DomainAxisPeak;
    static ArrayList<String> RangAxisPeak;
    static ArrayList<String> BckgrdAxisPeak;
    static ArrayList<String> FwhmAxisPeak;
    static ArrayList<String> ErrorAxisPeak;
    static ArrayList<String> EnergyAxisPeak;
    static ArrayList<String> AreayAxisPeak;
    static ArrayList<String> ForIdentification1;
    static NumberAxis rangeAxis;
    static ValueAxis domainAxis;
	static  ChartPanel chartPanel;
	static  Marker start,start1,start2;
	int clor=0,ll=0;
	protected static JFreeChart chart;
	static String results="";
	static String resultsfile="";
	static Crosshair xCrosshair;
	       String lin=System.getProperty("line.separator"); 
	       String eps="                                                 ";
	       String epsfile="               ";
	       String contenuAre0="           Channel"+eps+"Energy"+eps+"FWHM"+eps+"Net area                                        Background                                       Error (%)";
	       String contenuArefile="      Channel"+epsfile+"Energy"+epsfile+"FWHM"+epsfile+"Net area"+epsfile+"Background"+epsfile+"Error (%)";                                           
	private BufferedReader br;
	
	public treatment1() throws IOException {
		mariscoti.CauntsFile.clear();
		mariscoti.ChannelLissee.clear();
		mariscoti.deriveLissee.clear();
		mariscoti.ErreurDeriveLissee.clear();
		
		Mainn.forRaport="Treatmnt";
		DomainAxisPeak        = new ArrayList<String>(); //pour zoomer chaque pic
        RangAxisPeak          = new ArrayList<String>(); //pour zoomer chaque pic
        BckgrdAxisPeak        = new ArrayList<String>(); //pour zoomer chaque pic
        FwhmAxisPeak          = new ArrayList<String>(); //pour zoomer chaque pic
        ErrorAxisPeak         = new ArrayList<String>(); //pour zoomer chaque pic
        EnergyAxisPeak        = new ArrayList<String>(); //pour zoomer chaque pic
        AreayAxisPeak         = new ArrayList<String>(); //pour zoomer chaque pic
        ForIdentification1    = new ArrayList<String>(); //pour identification;
        DomainAxisPeak.clear();
        RangAxisPeak.clear();
        BckgrdAxisPeak.clear();
        FwhmAxisPeak.clear();
        ErrorAxisPeak.clear();
        EnergyAxisPeak.clear();
        AreayAxisPeak.clear();
        ForIdentification1.clear();
        		
		mariscoti.ContenuFile();
		mariscoti.deriv();
		
        XYDataset dataset = createDataset();
         chart = createChart(dataset);  
         chartPanel = new ChartPanel(chart, true, true, true, false, true){ 
        	@Override
            public Dimension getPreferredSize() {
                return new Dimension(Mainn.panel.getWidth(), Mainn.panel.getHeight());
            }
        	
        };         
        chartPanel.setFillZoomRectangle(false);
        chartPanel.setMouseWheelEnabled(true);
        add(chartPanel);
        
  //---------------------------------ZOOM----------------------------------------------------------------------------------     
        double up=domainAxis.getUpperBound();
        chartPanel.addChartMouseListener(new ChartMouseListener() {

        	@Override
            public void chartMouseClicked(ChartMouseEvent cme) {
        		Point2D po = chartPanel.translateScreenToJava2D(cme.getTrigger().getPoint());
                Rectangle2D plotArea = chartPanel.getScreenDataArea();
                // XYPlot plot = (XYPlot) chart.getPlot(); // your plot
                double chartX = plot.getDomainAxis().java2DToValue(po.getX(), plotArea, plot.getDomainAxisEdge());
                double chartY = plot.getRangeAxis().java2DToValue(po.getY(), plotArea, plot.getRangeAxisEdge());
        	
                double a0= rangeAxis.getLowerBound();
                double a1= rangeAxis.getUpperBound();
                
        		double b0=domainAxis.getLowerBound();
        		double b1=domainAxis.getUpperBound();
        		//System.out.println(( "   tst  :"+cme.getEntity()));
        		if( cme.getEntity() instanceof XYItemEntity ){
        		     int nnn=((XYItemEntity) cme.getEntity()).getSeriesIndex();
        		     //if(nnn!=2 ){((XYSeriesCollection) dataset).removeSeries(nnn);//pour suprimer le pic en cliquant dessus}
        		 }
        		
        		if( chartY<a1-(a1-a0)/6 && chartY>a0+(a1-a0)/6){
            		if(chartX>=b0+(b1-b0)/2){
            			domainAxis.setRange(b0+(b1-b0)/9,b1+(b1-b0)/9);
            			/*plot.clearDomainMarkers();
                        start2 = new ValueMarker(b0+(b1-b0)/9+(b1-b0)/2);
                        start2.setPaint(Color.orange);
                        start2.setLabel("Move");
                        start2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                        start2.setLabelPaint(Color.orange);
                        start2.setLabelTextAnchor(TextAnchor.TOP_CENTER);
                        plot.addDomainMarker(start2);*/	
            		}if(chartX<b0+(b1-b0)/2){
            			domainAxis.setRange(b0-(b1-b0)/9,b1-(b1-b0)/9);
            			/*plot.clearDomainMarkers();
                        start2 = new ValueMarker(b0-(b1-b0)/9+(b1-b0)/2);
                        start2.setPaint(Color.orange);
                        start2.setLabel("Move");
                        start2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                        start2.setLabelPaint(Color.orange);
                        start2.setLabelTextAnchor(TextAnchor.TOP_CENTER);
                        plot.addDomainMarker(start2);*/
            			
            		}}
            		
            		
            		plot.clearRangeMarkers();
            		if(chartY>=a1-(a1-a0)/6 || chartY<=a0+(a1-a0)/6){
                		if(chartY>=a1-(a1-a0)/6){
                			rangeAxis.setRange(a0,a1-(a1-a0)/9);
                		}if(chartY<=a0+(a1-a0)/6){
                			rangeAxis.setRange(a0,a1+(a1-a0)/9);
                			
                		}}
            	
                }

			@Override
			public void chartMouseMoved(ChartMouseEvent arg0) {
				double a2= rangeAxis.getLowerBound();
                double a3= rangeAxis.getUpperBound();
                
        		double b2=domainAxis.getLowerBound();
        		double b3=domainAxis.getUpperBound();
        		
        		plot.clearRangeMarkers();
        		start = new ValueMarker(a3-(a3-a2)/6);        		
                start.setPaint(Color.red);
                start.setLabel("Zoom_upp");
                start.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
                start.setLabelPaint(Color.red);
                start.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
                plot.addRangeMarker(start);

    			start1 = new ValueMarker(a2+(a3-a2)/6);
                start1.setPaint(Color.red);
                start1.setLabel("Zoom_lower");
                start1.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
                start1.setLabelPaint(Color.red);
                start1.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
                plot.addRangeMarker(start1);

    			/*plot.clearDomainMarkers();
                start2 = new ValueMarker(b2+(b3-b2)/2);
                start2.setPaint(Color.orange);
                start2.setLabel("Move");
                start2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                start2.setLabelPaint(Color.orange);
                start2.setLabelTextAnchor(TextAnchor.TOP_CENTER);
                plot.addDomainMarker(start2);*/
  
			}		
        }); 
//---------------------------------Minimize_zoom_domainAxis-----------------------------------------     
        
        chartPanel.getPopupMenu().add(new JMenuItem("Minimize zoom")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {

        		double b0m=domainAxis.getLowerBound();
        		double b1m=domainAxis.getUpperBound();        
        		domainAxis.setRange(b0m-(b1m-b0m)/4,b1m+(b1m-b0m)/4);				
			}
		
		}); 
     
//---------------------------------Move_down-----------------------------------------     
        
        chartPanel.getPopupMenu().add(new JMenuItem("Move down")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
			
				double a0m= rangeAxis.getLowerBound();
                double a1m= rangeAxis.getUpperBound();          
        		rangeAxis.setRange(a0m-(a1m-a0m)/6,a1m);
        		
			}
		
		});                
     
//---------------------------------Delet_peak-----------------------------------------   
   /*     JMenuItem  Gs = new JMenuItem("Delet");
        
        chartPanel.addChartMouseListener(new ChartMouseListener() {
        	
        	@Override
            public void chartMouseClicked(ChartMouseEvent e2) {}
        	
        	
        	@Override
			public void chartMouseMoved(ChartMouseEvent e3) {
        		
        		if( e3.getEntity() instanceof XYItemEntity ){
        			
        			
        			int n3=((XYItemEntity) e3.getEntity()).getSeriesIndex();
        			
        			if(n3!=2){
        				Gs.setVisible(true);
        			    chartPanel.getPopupMenu().add(Gs).addActionListener(new ActionListener() {
            			      public void actionPerformed(ActionEvent e4) {	
            			    	 
       		                       ((XYSeriesCollection) dataset).removeSeries(n3);       		                              		                             		                       
       		                       ((Component) dataset).repaint();
       		                   
            			       }
                		
            		     }); 
        			       }else{Gs.setVisible(false);}
        			
        			    
       		       }else{Gs.setVisible(false);}
       		
             }
		
        });
       
        
        
     */  
       
 //----------------------------------------Ajust_peak-detected-----------------------------------------------------------       
        
       JMenuItem  Gsa = new JMenuItem("Ajust peak");
        
        chartPanel.addChartMouseListener(new ChartMouseListener() {
        	
        	@Override
            public void chartMouseClicked(ChartMouseEvent e2a) {}
        	
        	@Override
			public void chartMouseMoved(ChartMouseEvent e3a) {        		
        		if( e3a.getEntity() instanceof XYItemEntity ){       			
        			int n3a=((XYItemEntity) e3a.getEntity()).getSeriesIndex();       			
        			if(n3a!=2){
        				  
        				   Gsa.setVisible(true);
        			            chartPanel.getPopupMenu().add(Gsa).addActionListener(new ActionListener() {
        			            	@Override      
        			            	public void actionPerformed(ActionEvent e4a) {	
            			    	                    //AjustPeak.AjustPeak1();
       		                                        // ((XYSeriesCollection) dataset).removeSeries(n3a);      		                       
       		                                        //renderer.setSeriesItemLabelsVisible(n3a, false);      		                    
       		                                        // System.out.println(((XYSeriesCollection) dataset).getX(n3a, 0));
       		                                        //System.out.println(((XYSeriesCollection) dataset).getEndX(n3a, 0));      		        
       		                                        // ((Component) dataset).repaint();    		                       
       		                                        // XYSeriesCollection dataset = new XYSeriesCollection();
       		                                        // ((XYSeriesCollection) dataset).getEndX(n3a, n3a);
       		                                        // dataset.addSeries(series1);      		                     		                   
            			                    }                		
            		          }); 
        			     }else{Gsa.setVisible(false);}        			        			    
       		       }else{Gsa.setVisible(false);}      		
             }	
        	
        	
        	
        });
        
  //--------------------------------------------------------------------------------------------      
    
        chartPanel.addMouseListener(new MouseAdapter() {       	
    		public void mouseReleased(MouseEvent ez) {
	
			}             
            });     
    }

	private XYDataset createDataset() throws IOException {
		
		ArrayList<String> xd=new ArrayList<String>();
        ArrayList<String> yd=new ArrayList<String>();
        ArrayList<String> xs_m=new ArrayList<String>();
        ArrayList<String> ys_m=new ArrayList<String>();
        ArrayList<String> yND=new ArrayList<String>();
        ArrayList<XYSeries> Srie=new ArrayList<XYSeries>();
        
        //----pour supprimer le contenu des tableaux quand on analyse autre fois sur le meme interface graphique
        xd.clear();
        yd.clear();
        xs_m.clear();
        ys_m.clear();
        yND.clear();
        Srie.clear();
        
      //----------------------------------------------------------
        XYSeries series4 = new XYSeries("A");
        XYSeries series5 = new XYSeries("B");
        XYSeries series6 = new XYSeries("C");
        XYSeries series2 = new XYSeries("D");	
		XYSeries series3 = new XYSeries("E");
		XYSeries series7 = new XYSeries("F");

		int sp=0;//Ppour les single pic
	    for(sp=0;sp<1000;sp++){ 
	        	
	    XYSeries series = new XYSeries("a"+sp,false,true);        	
	    Srie.add(series);}
		
		XYSeries seriesDernier = new XYSeries("dernier",false,true);
        
		
		      int i=0;//?
		      int je=Mainn.offsetofdata;
		  for(int j=0;j<Mainn.lengthofdata-Mainn.offsetofdata;j++) {
		  i=i+1;//?
		  
		  int data = Mainn.tableauEntier[j];		          		  
          String xValue = Integer.toString(data);
          String jee=Float.toString(je);
                xd.add( jee); 
         		yd.add(xValue);
          float x = Float.parseFloat(xValue);
          if(Mainn.rdbtnmntmView_5.isSelected()){series2.add(je, x);}	
          je=je+1;
	  }
	  
	    String wmey =Mainn.wt;
	    int m0 = Integer.parseInt(wmey); //m
	    
	    String mey =Mainn.mt;
	    int z0 = Integer.parseInt(mey); //z
	    
	    String atech =Mainn.tech;
	    float tlim =(float) Float.parseFloat(atech); 
	    
	    String ali =Mainn.li;
	    int lmin = Integer.parseInt(ali); 
	    
	    String alm =Mainn.lm;
	    int lmax = Integer.parseInt(alm); 
	    
	    String kect =Mainn.k_ecart;
	    float k =(float) Float.parseFloat(kect); // k de ecart type
	    
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
	    
	    String At1s1 =Mainn.At1s;
	    float at =(float) Float.parseFloat(At1s1);// a de tail
	    
	    String At2s1 =Mainn.At2s;
	    float bt =(float) Float.parseFloat(At2s1);// b de tail

        float ecart= (float) 1.4;	
        int j4,l=0;
        float x0mzc=0,Nsmzc=0,ErrorNsmzc=0,Nmin=0,ErrorNmin=0,Emin = 0,t = 0,dernierpicX = 0,dernierpicY=0,tallder,enrgder,ND=0,NDm = 0,FwHm=0,lmn,lmx;
        	
        for(j4=0;j4<mariscoti.ChannelLissee.size();j4++){
        		           x0mzc= Float.parseFloat(mariscoti.ChannelLissee.get(j4));
        		           Nsmzc=Float.parseFloat(mariscoti.deriveLissee.get(j4));
        		           ErrorNsmzc=Float.parseFloat(mariscoti.ErreurDeriveLissee.get(j4));       		
        		           ND=Float.parseFloat(yd.get(j4+m0*z0+1));    		
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
        				            ecart=(float) ((ae+be*Math.sqrt(enrgder)));//pour le dernier pic
        				            tallder=(at+bt*(enrgder));
        				            dernierpicX=Emin+tallder+k*ecart+20;
        				            dernierpicY=0;
        				            String Xs_m = Double.toString(Emin);
        				            String Ns_m = Double.toString(Nmin);
        				            String NDp = Double.toString(NDm);
        				            xs_m.add(Xs_m);
        				            ys_m.add(Ns_m);
        				            yND.add(NDp);  
        				            
        				            DomainAxisPeak.add(Xs_m);//Pour zommer chaque pic
        				            RangAxisPeak.add(NDp);//Pour zommer chaque pic
        				            
        				            Nmin=0;	l=0; t=0;      			
        				       }else {l=0;Nmin=0;t=0;}
        		
        			
        	}}

        	String dernpicx = Double.toString(dernierpicX);
            String dernpicY = Double.toString(dernierpicY);
            xs_m.add(dernpicx);
            ys_m.add(dernpicY);
            
           // DomainAxisPeak.add(dernpicx);//Pour zommer chaque pic
           // DomainAxisPeak.add(dernpicY);//Pour zommer chaque pic
        	
        	
        	//------------------------Soustraction les aires des pics--------------------------
        	int ia=0,ja=0,jg=0,jc=0,jcc=0;
        	String result="";
        	String resultfile="";
        	float Xa,Xa1,Xa_1,Na,Na1,Pd = 0,Pg=0,xd1 = 0,yd1=0,N=0,ng=0,nd=0,Bg=0,Bd=0,areat=0,backgrounds,Area_net,eror=0;
        	float xdm1=0,ydm1=0,nmg=0,nmd=0,Nm=0,Bmg=0,Bmd=0,areatm = 0,Pg_1=0,Pd1=0,MBg=0,MBd=0,Eg=0,Ed=0,am=0,bm=0,drt=0;
        	float NPg=0,NPd=0,npm=0,ht=0,bck_drt=0,ecartc,ecartg = 0,ecartd = 0,tall1,talld = 0,tallg = 0, tallPd1=0;
        	float enrgc,enrgc1,enrgcg,enrgcd,enrgcpd1;
        	
        	DecimalFormat df0 = new DecimalFormat("00.00E00");
        	DecimalFormat tr = new DecimalFormat("000");
        	
        	for(ia=0;ia<xs_m.size()-1;ia++){
        		
        		           Xa= Float.parseFloat(xs_m.get(ia));
        		           Na= Float.parseFloat(ys_m.get(ia));
        		           Xa1= Float.parseFloat(xs_m.get(ia+1));
        		           Na1= Float.parseFloat(ys_m.get(ia+1));
        		           if(ia==0){Xa_1=0;}else{Xa_1= Float.parseFloat(xs_m.get(ia-1));}
        		           //multiple pic
        		           enrgc=(float) (ce+de*Xa+c0*Math.pow(Xa, 2)+d0*Math.pow(Xa, 3));
        		           ecartc=(float) (ae+be*Math.sqrt(enrgc));
        		          // tall=(at+bt*(ce+de*Xa));
        		           
        		           enrgc1=(float) (ce+de*Xa1+c0*Math.pow(Xa1, 2)+d0*Math.pow(Xa1, 3));
        		           tall1=(at+bt*(enrgc1));
        		           if(Xa1-Xa<(int) k*ecartc+tall1){//attention tall1 pas tall
        			               if(jc==0){jg=ia;}
       		       		
        			               Pg=Float.parseFloat(xs_m.get(jg));
        			               NPg=Float.parseFloat(ys_m.get(jg));
        			               enrgcg=(float) (ce+de*Pg+c0*Math.pow(Pg, 2)+d0*Math.pow(Pg, 3));
        			               ecartg=(float) ((ae+be*Math.sqrt(enrgcg)));
        			               tallg=(at+bt*(enrgcg));
        		
        			               Pd= Float.parseFloat(xs_m.get( ia+1));
        			               NPd= Float.parseFloat(ys_m.get( ia+1));
        			               enrgcd=(float) (ce+de*Pd+c0*Math.pow(Pd, 2)+d0*Math.pow(Pd, 3));
        			               ecartd=(float) ((ae+be*Math.sqrt(enrgcd)));
        			               talld=(at+bt*(enrgcd));
        		
        			               Pd1= Float.parseFloat(xs_m.get( ia+2));
        			               enrgcpd1=(float) (ce+de*Pd1+c0*Math.pow(Pd1, 2)+d0*Math.pow(Pd1, 3));
        			               tallPd1=(at+bt*(enrgcpd1));
        			               if(ia==0 || jg==0 ||(ia==0 && jg==0)){Pg_1=0;}else{Pg_1= Float.parseFloat(xs_m.get(jg-1));}       		
        			               jc=jc+1;
        			               jcc=0;
                           }
				//___________________________________multipl-pic________________________________________________________________
				
				          else{
				                            if(jcc==0 && Pg>7*ecartc){//3*ecart pour éviter la premier dessigne en graphe				
				                                      //System.out.println("Multiple        Pg =  " +Pg   +"   Pd     "  +Pd );
				                                      int im=0,withd;
				                                      
				                                      withd=(int)(ecartg*k/2);
				                                      float withg=(ecartg*k/2);;
				                                      for(im=0;im<yd.size()-1;im++){
				                                          xdm1=Float.parseFloat(xd.get(im));
				                                          ydm1= Float.parseFloat(yd.get( im));				                                        
				                                          if(Pg-Pg_1>(int) k*ecartg+tallg+5 && xdm1>= Pg-withg-tallg-5 && xdm1<Pg-withg-tallg){nmg=nmg+1; Bmg=Bmg+ydm1;Eg= (int) (Pg-withg-tallg); if(Mainn.rdbtnmntmView_2.isSelected()){series4.add(xdm1, ydm1);}}
				                                          else if(Pg-Pg_1>(int) k*ecartg+tallg+3 && xdm1>=Pg-withg-tallg-3 && xdm1<Pg-withg-tallg){nmg=nmg+1; Bmg=Bmg+ydm1;Eg=(int) (Pg-withg-tallg); if(Mainn.rdbtnmntmView_2.isSelected()){series4.add(xdm1, ydm1);}}
				                                          else if(Pg-Pg_1>=(int) (k*ecartg+tallg) && xdm1>=Pg-withg-tallg-1 && xdm1<Pg-withg-tallg){nmg=nmg+1; Bmg=Bmg+ydm1;Eg=(int) (Pg-withg-tallg);if(Mainn.rdbtnmntmView_2.isSelected()){series4.add(xdm1, ydm1);}}
				
				                                          if((int)(Pg-withg-tallg)<=xdm1 && xdm1<=(int)(Pd+withd+0.5)){Nm=Nm+1;areatm=areatm+ydm1;if(Mainn.rdbtnmntmView_1.isSelected()){series6.add(xdm1, ydm1);}} 
				
				                                          if(Pd1-Pd>(int) k*ecartd+tallPd1+5 && xdm1>Pd+withd && xdm1<=Pd+withd+5){nmd=nmd+1; Bmd=Bmd+ydm1;Ed=(int) (Pd+withd);if(Mainn.rdbtnmntmView_2.isSelected()){series5.add(xdm1, ydm1);}}
				                                          else if(Pd1-Pd>(int) k*ecartd+tallPd1+3 && xdm1>Pd+withd && xdm1<=Pd+withd+3){nmd=nmd+1; Bmd=Bmd+ydm1;Ed=(int) (Pd+withd);if(Mainn.rdbtnmntmView_2.isSelected()){series5.add(xdm1, ydm1);}}
				                                          else if(Pd1-Pd>=(int) k*ecartd+tallPd1 && xdm1>Pd+withd && xdm1<=Pd+withd+1){nmd=nmd+1; Bmd=Bmd+ydm1;Ed=(int) (Pd+withd);if(Mainn.rdbtnmntmView_2.isSelected()){series5.add(xdm1, ydm1);}}
				                                          } 
				                                      // System.out.println("BBBBB   Eg=" +Eg   +"  Ed="+Ed+"  nmg="+nmg+ "  Bmg="+Bmg+"   nmd="+nmd+"    Bmd="+Bmd+"  Nm="+Nm+"  jg="+jg );
				                                      MBg=Bmg/nmg;MBd=Bmd/nmd;
				                                      
				                                      am=(MBd-MBg)/(Ed-Eg);
				                                      int ima,Nma=0;
				                                      float xdm1a,ydm1a,areatma=0;
				                                      for(ima=0;ima<yd.size()-1;ima++){
				                                          xdm1a=Float.parseFloat(xd.get(ima));
				                                          ydm1a= Float.parseFloat(yd.get(ima));
				                                          if(Pg-withg-tallg<=xdm1a && xdm1a<=Pd+withd){Nma=Nma+1;areatma=areatma+ydm1a;
				                                          drt=am*xdm1a+(MBg-am*Eg);bck_drt=bck_drt+drt;
				                                          if(Mainn.rdbtnmntmView_3.isSelected()){series7.add(xdm1a, drt);}
				                                          } 
				                                        }
				                                      int i1m=0,hn=0;
				                                      float Pi=0,NPi=0,drti=0,h=0,area_netm=0,area_net_Pi=0,erorm=0,erorPi=0;
				                                      float A_gauss=0,gauss=0,drtgBck=0,gaust=0,eror_area_net=0,Dge=0,Dgy=0,drtg=0,hPi=0,area_net_gaus=0;
				                                      for(i1m=0;i1m<yd.size()-1;i1m++){
				                                          if(i1m<=ia && i1m>=jg){ 
				                                                 int ih=jg;	
				                                                 if(hn==0){
					                                                       for(ih=i1m;ih<=ia;ih++){					
				                                                                 Pi=Float.parseFloat(xs_m.get(ih));
	        		                                                             NPi=Float.parseFloat(yND.get(ih));
	        		                                                             drti=am*Pi+(MBg-am*Eg);
	        		                                                             h=NPi-drti;
	        		                                                             ht=(int)ht+h;
	        		                                                       }
					                                                       hn=hn+1;
	        		                                               }
					                                              Pi=Float.parseFloat(xs_m.get(i1m));
	        		                                              NPi=Float.parseFloat(yND.get(i1m));
	        		                                              drti=am*Pi+(MBg-am*Eg);
	        		                                              h=(NPi-drti)/ht;
	        		                                              hPi=NPi-drti;
	        		                                              area_netm=areatm-bck_drt;
	        		                                              erorm=(float) Math.sqrt(areatm+Nm*Nm/4*(Bmg/(nmg*nmg)+Bmd/(nmd*nmd)))/area_netm*100;
	        		                                              area_net_Pi=h*area_netm;
	        		                                              erorPi=area_net_Pi*erorm/area_netm;
	        		                                             int ig;
	        		                                             float ecarti,enrgi,talli,withi;
	        		                                             enrgi=(float) (ce+de*Pi+c0*Math.pow(Pi, 2)+d0*Math.pow(Pi, 3));
	        		                                             ecarti=(float) ((ae+be*Math.sqrt(enrgi)));
	        		                                             talli=(at+bt*(enrgi));
	        		                                             withi=(ecarti*k/2);
	        		                                             for(ig=(int) (Pi-(withi-(int)(talli-0.5)+1));ig<=(int) Pi+(withi-1);ig++){            
	        		                                            	              
	        		                                            	              if((int)Pi-(withi-talli+1)>(int)(withi-talli+1)+6){//pour eviter le le probleme de premier pic
	        		                                            	                        Dge= Float.parseFloat(xd.get(ig));
	        			                                                                    Dgy= Float.parseFloat(yd.get(ig));
	        			                                                                    drtg=am*Dge+(MBg-am*Eg);
	        			                                                                    gauss =  (float) ((hPi*Math.exp(-(Dge-Pi)*(Dge-Pi)/(2*ecarti*ecarti)))+drtg);    			                                                           
	        			                                                                    if(ig==xd.size()-1){break;}
	        			                                                                    if(Mainn.rdbtnmntmView_4.isSelected()){
	        			                                                                    	if(clor<=1000){
	        			                                                                    		Srie.get(clor).add(Dge, gauss);	
	        			                                                                         }else{seriesDernier.add(Dge, gauss);}
	        			                                                                    }
	        			                                                                    area_net_gaus=area_net_gaus+gauss-drtg;
	        			                                                                    drtgBck=drtgBck+drtg;
	        			                                                                    gaust=gaust+gauss;     
	        		                    	                                       }	              
	        		                                              }//}
	        		                                             
	        		                                             
	        		                                             clor=clor+1;
	        		                                             eror_area_net=(float) (Math.sqrt(gaust+drtgBck)*100/area_net_gaus);
	        		                                             String espace="                                            ";
	        		                                             String espacefile="             ";
	        		                                             String espaceId="                             ";
	        		                                             ll=ll+1;
	       				                                         //result=result+lin+"M"+"    "+Pi+espace+enrgi+espace+ecarti+espace+area_net_gaus+espace+bck_drt+espace+erorPi+lin;
	       				                                         result=result+lin+tr.format(ll)+"M"+"    "+df0.format(Pi)+espace+df0.format(enrgi)+espace+df0.format(ecarti)+espace+df0.format(area_net_gaus)+espace+df0.format(drtgBck)+espace+df0.format( eror_area_net);
	       				                                         resultfile=resultfile+lin+tr.format(ll)+"M"+"    "+df0.format(Pi)+espacefile+df0.format(enrgi)+espacefile+df0.format(ecarti)+espacefile+df0.format(area_net_gaus)+espacefile+df0.format(drtgBck)+espacefile+df0.format( eror_area_net);
	       				                                      
	       				                                         ForIdentification1.add(tr.format(ll)+"M"+"    "+df0.format(Pi)+espaceId+df0.format(enrgi)+espaceId+df0.format(ecarti)+espaceId+df0.format(area_net_gaus)+espaceId+df0.format(drtgBck)+espaceId+df0.format( eror_area_net));
	       				                                       
	       				                                         BckgrdAxisPeak.add(Float.toString((MBg+MBd)/2));//pour zommer chaque pic
	        		                                             FwhmAxisPeak.add(Float.toString(ecarti));//pour zommer chaque pic
	        		                                             EnergyAxisPeak.add(Float.toString(enrgi));
	        		                                             AreayAxisPeak.add(Float.toString(area_net_gaus));
	        		                                             ErrorAxisPeak.add(Float.toString(eror_area_net));
	       				                                         
	       				                                         area_net_gaus=0;drtgBck=0; eror_area_net=0;gaust=0;
	        		                                             
				                                      }
		                                          }
		                                   // System.out.println("Multiple   Pg=" +Pg   +"  Pd="+Pd+"  nmg="+nmg+"   nmd="+nmd+"  Nm="+Nm+"  jg="+jg );
		                                   areatm=0;Bmg=0;Bmd=0;Nm=0;nmg=0;nmd=0;drt=0;ht=0;h=0;erorPi=0;drtg=0;bck_drt=0;
		                                   jcc=jcc+1;
                                          }	
				//___________________________________single-pic__________________________________________________________________________________
				else{//System.out.println("Single =  " +Xa  );
				int is=0, withj;
				float ecartj = 0,enrgj = 0,enrgj1,tallj = 0,tallj1;
				for(is=0;is<yd.size()-1;is++){  
				           xd1=Float.parseFloat(xd.get(is));
        		           yd1= Float.parseFloat(yd.get( is));
        		           enrgj=(float) (ce+de*Xa+c0*Math.pow(Xa, 2)+d0*Math.pow(Xa, 3));
        		           ecartj=(float) ((ae+be*Math.sqrt(enrgj)));
                           withj=(int)(ecartj*k/2);
                           tallj=(at+bt*(enrgj));
                           enrgj1=(float) (ce+de*Xa1+c0*Math.pow(Xa1, 2)+d0*Math.pow(Xa1, 3));
                           tallj1=(at+bt*(enrgj1));
				
        		          if(Xa-Xa_1>(int) k*ecartj+tallj+5 && xd1>=Xa-withj-tallj-5 && xd1<Xa-withj-tallj){ng=ng+1; Bg=Bg+yd1; if(Mainn.rdbtnmntmView_2.isSelected()){series4.add(xd1, yd1);}}
        		          else if(Xa-Xa_1>(int) k*ecartj+tallj+3 && xd1>=Xa-withj-tallj-3 && xd1<Xa-withj-tallj){ng=ng+1; Bg=Bg+yd1; if(Mainn.rdbtnmntmView_2.isSelected()){series4.add(xd1, yd1);}}
        		          else if(Xa-Xa_1>=(int) k*ecartj+tallj && xd1>=Xa-withj-tallj-1 && xd1<Xa-withj-tallj){ng=ng+1; Bg=Bg+yd1;if(Mainn.rdbtnmntmView_2.isSelected()){series4.add(xd1, yd1);}}
				
        		          if(Xa-withj-tallj<=xd1 && xd1<=Xa+withj){N=N+1;areat=areat+yd1;if(Mainn.rdbtnmntmView.isSelected()){    series3.add(xd1, yd1);   }} 
				
        		          if(Xa1-Xa>(int) k*ecartj+tallj1+5 && xd1>Xa+withj && xd1<=Xa+withj+5){nd=nd+1; Bd=Bd+yd1;if(Mainn.rdbtnmntmView_2.isSelected()){series5.add(xd1, yd1);}}
        		          else if(Xa1-Xa>(int) k*ecartj+tallj1+3 && xd1>Xa+withj && xd1<=Xa+withj+3){nd=nd+1; Bd=Bd+yd1;if(Mainn.rdbtnmntmView_2.isSelected()){series5.add(xd1, yd1);}}
        		          else if(Xa1-Xa>=(int) k*ecartj+tallj1 && xd1>Xa+withj && xd1<=Xa+withj+1){nd=nd+1; Bd=Bd+yd1;if(Mainn.rdbtnmntmView_2.isSelected()){series5.add(xd1, yd1);}}
				}
				backgrounds=(int) (N*(Bg/ng+Bd/nd)/2);
				
				
				Area_net=areat-backgrounds;
				eror=(float) Math.sqrt(areat+N*N/4*(Bg/(ng*ng)+Bd/(nd*nd)))/Area_net*100;
				String espace="                                            ";
				String espacefile="             ";
				String espaceId="                             ";
				ll=ll+1;
				result=result+lin+tr.format(ll)+"S "+"   "+df0.format(Xa)+espace+df0.format(enrgj)+espace+df0.format(ecartj)+espace+df0.format(Area_net)+espace+df0.format(backgrounds)+espace+df0.format(eror);
				resultfile=resultfile+lin+tr.format(ll)+"S "+"   "+df0.format(Xa)+espacefile+df0.format(enrgj)+espacefile+df0.format(ecartj)+espacefile+df0.format(Area_net)+espacefile+df0.format(backgrounds)+espacefile+df0.format(eror);
				
				ForIdentification1.add(tr.format(ll)+"S "+"   "+df0.format(Xa)+espaceId+df0.format(enrgj)+espaceId+df0.format(ecartj)+espaceId+df0.format(Area_net)+espaceId+df0.format(backgrounds)+espaceId+df0.format(eror));
				
				BckgrdAxisPeak.add(Float.toString((Bg/ng+Bd/nd)/2));//pour zommer chaque pic
			    FwhmAxisPeak.add(Float.toString(ecartj));//pour zommer chaque pic
			    EnergyAxisPeak.add(Float.toString(enrgj));
			    AreayAxisPeak.add(Float.toString(Area_net));
                ErrorAxisPeak.add(Float.toString(eror));
				
				areat=0;
				Bg=0;Bd=0;N=0;ng=0;nd=0;backgrounds=0;Area_net=0;eror=0;
				 
				}
				
				jc=0;
				}
        	}
        results=contenuAre0+lin+result;
        resultsfile=contenuArefile+lin+resultfile;
            
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series4);
        dataset.addSeries(series5);
        dataset.addSeries(series6); 
        dataset.addSeries(series3);
        dataset.addSeries(series2);
        dataset.addSeries(series7);
        
        int spd=0;
        for(spd=0;spd<1000;spd++){ 
        	dataset.addSeries(Srie.get(spd));
        	
        	}
        dataset.addSeries(seriesDernier);
        return dataset ;
        
    }
	private JFreeChart createChart( XYDataset dataset) {
    
          chart = ChartFactory.createXYLineChart(
            "",      // chart title
            "Channel",                      // x axis label
            "Caunts",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            false ,                     // include legend
            true,                     // tooltips
            false                     // urls
        ); 
         new Color(255, 250, 240);
 	     chart.setBackgroundPaint(Color.BLACK);
 		 plot = chart.getXYPlot();
         new Color(255, 250, 240);
 		 plot.setBackgroundPaint(Color.BLACK);
         plot.setDomainGridlinePaint(Color.BLACK);//Ligne-vertical
         plot.setRangeGridlinePaint(Color.BLACK);  //ligne-horizontal    
         plot.setOutlineStroke(new BasicStroke(2.5f));
         
         //this.xCrosshair.setValue();
         
         //plot.setDomainCrosshairVisible(true);//pour voir la droite vertical
         //plot.setDomainCrosshairPaint(Color.red);         
        // plot.setDomainCrosshairStroke(new BasicStroke(2f));// epesseur de la droit vertical 
         
         renderer = new XYLineAndShapeRenderer();
         renderer.setSeriesPaint(0,Color.BLUE);
         renderer.setSeriesPaint(1,Color.BLUE);
         renderer.setSeriesPaint(2,Color.GREEN);
         renderer.setSeriesPaint(3,Color.RED);//
         renderer.setSeriesPaint(4,Color.GREEN); 
         renderer.setSeriesPaint(5,Color.GRAY);
         renderer.setSeriesPaint(6,Color.CYAN);
          
          int spd1=7;
          for(spd1=7;spd1<1000;spd1++){ 
        	  renderer.setSeriesPaint(spd1,Color.RED);
        	  renderer.setSeriesShape(spd1, new Ellipse2D.Double(-1, -1, 2, 2));
        	  renderer.setSeriesPaint(spd1+1,Color.CYAN);
        	  renderer.setSeriesShape(spd1+1, new Ellipse2D.Double(-1, -1, 2, 2));
        	  spd1=spd1+1;
          	}
         renderer.setSeriesLinesVisible(0, false);//relient-les-poits-entre-eux-par-ligne
         renderer.setSeriesLinesVisible(1, false);
         renderer.setSeriesLinesVisible(2, false);
         renderer.setSeriesLinesVisible(3, false );
         renderer.setSeriesLinesVisible(4, false);
         renderer.setSeriesLinesVisible(5, false);
         renderer.setSeriesLinesVisible(6, true);
        // renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6));
         renderer.setSeriesShape(0, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(1, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(2, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(3, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(4, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(5, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(6, new Ellipse2D.Double(-1, -1, 2, 2));
        //.setSeriesShape(1, new Rectangle(-8,-8,8,8));
        // renderer.setSeriesShape(2, new Rectangle(-2,-2,4,4));
        // renderer.setSeriesShape(4, new Rectangle());
         renderer.setSeriesStroke(0, new BasicStroke(6));//epesseur-de-coleur  
         renderer.setSeriesStroke(1, new BasicStroke(6));
         renderer.setSeriesStroke(2, new BasicStroke(6));
         renderer.setSeriesStroke(3, new BasicStroke(3));
         renderer.setSeriesStroke(4, new BasicStroke(6));
         renderer.setSeriesStroke(5, new BasicStroke(6));
         renderer.setSeriesStroke(6, new BasicStroke(2));
         plot.setRenderer(renderer);
         rangeAxis = (NumberAxis) plot.getRangeAxis();
         rangeAxis.setTickLabelPaint(Color.WHITE);         
         rangeAxis.setLabelPaint(Color.white);                  
        
         domainAxis = plot.getDomainAxis();
         domainAxis.setTickLabelPaint(Color.white);         
         domainAxis.setLabelPaint(Color.white);
         chart.getTitle().setPaint(Color.white);         
 	     chart.setBackgroundPaint(Color.BLACK);        
        return chart;
        } 
}
    