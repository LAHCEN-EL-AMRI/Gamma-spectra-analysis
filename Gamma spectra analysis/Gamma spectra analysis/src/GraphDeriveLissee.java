

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

public class GraphDeriveLissee extends JPanel {	
	
    static ArrayList<String> ydd=new ArrayList<String>();
    static ArrayList<String> xdmd=new ArrayList<String>();   
    
    static float Nke;
	
	 XYPlot plot;
    XYLineAndShapeRenderer renderer;
    static NumberAxis rangeAxis;
    static ValueAxis domainAxis;
    
	
	static  ChartPanel chartPanel;
	static  Marker start,start1,start2;
	
	
	protected static JFreeChart chart;
	
	private BufferedReader br;
	public GraphDeriveLissee() throws IOException {
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
       // chartPanel.setPopupMenu(null);
        
        
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
        		
        		
        		
        		if( cme.getEntity() instanceof XYItemEntity ){
        		     int nnn=((XYItemEntity) cme.getEntity()).getSeriesIndex();
        		     //if(nnn!=2 ){((XYSeriesCollection) dataset).removeSeries(nnn);//pour suprimer le pic en cliquant dessus}
        		
        		 }
        		
        		if( chartY<a1-(a1-a0)/6 && chartY>a0+(a1-a0)/6){
            		if(chartX>=b0+(b1-b0)/2){
            			
            			domainAxis.setRange(b0+(b1-b0)/9,b1+(b1-b0)/9);
            			
            			plot.clearDomainMarkers();
                        start2 = new ValueMarker(b0+(b1-b0)/9+(b1-b0)/2);
                        start2.setPaint(Color.orange);
                        start2.setLabel("Move");
                        start2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                        start2.setLabelPaint(Color.orange);
                        start2.setLabelTextAnchor(TextAnchor.TOP_CENTER);
                        plot.addDomainMarker(start2);
            			
            			
            			
            		}if(chartX<b0+(b1-b0)/2){
            			domainAxis.setRange(b0-(b1-b0)/9,b1-(b1-b0)/9);
            			plot.clearDomainMarkers();
                        start2 = new ValueMarker(b0-(b1-b0)/9+(b1-b0)/2);
                        start2.setPaint(Color.orange);
                        start2.setLabel("Move");
                        start2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                        start2.setLabelPaint(Color.orange);
                        start2.setLabelTextAnchor(TextAnchor.TOP_CENTER);
                        plot.addDomainMarker(start2);
            			
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
                
                
    			plot.clearDomainMarkers();
                start2 = new ValueMarker(b2+(b3-b2)/2);
                start2.setPaint(Color.orange);
                start2.setLabel("Move");
                start2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
                start2.setLabelPaint(Color.orange);
                start2.setLabelTextAnchor(TextAnchor.TOP_CENTER);
                plot.addDomainMarker(start2);
				
                
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
     
   
        
        
        chartPanel.addMouseListener(new MouseAdapter() {       	
		    
        	
    		public void mouseReleased(MouseEvent ez) {
    			
    			
			}             
            });  
		  
       
	   
    }
    
  
	private XYDataset createDataset() throws IOException {
		
		ydd.clear();
	    xdmd.clear();
		
        XYSeries series1 = new XYSeries("A");
        XYSeries series2 = new XYSeries("B");
        XYSeries series3 = new XYSeries("C");
        XYSeries series4 = new XYSeries("D");
		
		
		
		XYSeries seriesDernier = new XYSeries("dernier",false,true);
		
		      int i=0;
		      int je=Mainn.offsetofdata;
		  for(int j=0;j<Mainn.lengthofdata-Mainn.offsetofdata;j++) {
		  i=i+1;
		  
		  int data = Mainn.tableauEntier[j];		          		  
          String xValue = Integer.toString(data);
          ydd.add(xValue);
               
          float x = Float.parseFloat(xValue);
          if(Mainn.rdbtnmntmView_5.isSelected()){series1.add(je, x);}
          je=je+1;
	  }
       	
//--------------------------------------------------------------
        String mfenetre =Mainn.wt;//m
		 int m_fenetre = Integer.parseInt(mfenetre); 
	    
	     String zfois =Mainn.mt;//z
	     int z_fois = Integer.parseInt(zfois); 
	    
		
       for (int i1 = z_fois*m_fenetre+1; i1 < ydd.size()-z_fois*m_fenetre-1; i1++) {
           float valeurFinal =(float) calculDeriv(z_fois, m_fenetre, i1);
           
           
           
           if(Mainn.rdbtnmntmView_5.isSelected()){
				 series2.add(i1+1, valeurFinal);		
    	         
    	         
    	         }
           
          
           
       }
       //erreur de la derivée seconde lissée
       for (int f = z_fois*m_fenetre+1; f < ydd.size()-z_fois*m_fenetre-1; f++) {
           float valeurFinalError = (float) Math.sqrt(calculDerivError(z_fois, m_fenetre, f));
          
           
           if(Mainn.rdbtnmntmView_5.isSelected()){
				 series3.add(f+1, valeurFinalError);
				 series4.add(f+1, 0);
  	         
  	         }
           
       }
//------------------------------------------------------------------    
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
        return dataset ;
        
    }
//_________________________________________________________________________________________
	
	
	//-----------------la derive seconde lissée------------------------------------
		 public static long somme = 0;

		    public static long calculDeriv(int z, int m, int i1) throws IOException {
		        
		        long valeur = 0;
		       
		        for (int k = i1 - z * m - 1; k <= i1 + z * m + 1; k++) {
		            somme = 0 ;
		           
		            calculConstantC(i1, k, z, m);
		            Nke= Float.parseFloat(ydd.get(k));
		            valeur += somme * Nke;
		           
		        }
		        return valeur;
		        
		    }

		    public static void calculConstantC(int i1, int k, int z, int m) {
		        if (z == 0) {
		            if (Math.abs(k - i1) >= 2) {
		                somme += 0;
		               
		            }
		            if (Math.abs(k - i1) == 1) {
		                somme += 1;
		               
		            }
		            if (Math.abs(k - i1) == 0) {
		                somme -= 2;
		               
		            }

		        } else {
		            for (int j = i1 - m; j <= i1 + m; j++) {
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
			             Nke= Float.parseFloat(ydd.get(ke));
			            valeurError += sommeError * Nke;
			           
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
//___________________________________________________________________________________________
	
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
         //plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
         plot.setDomainGridlinePaint(Color.BLACK);//Ligne-vertical
         plot.setRangeGridlinePaint(Color.BLACK);  //ligne-horizontal    
         //plot.setOutlinePaint(Color.BLACK);
         plot.setOutlineStroke(new BasicStroke(2.5f));
         renderer = new XYLineAndShapeRenderer();
         renderer.setSeriesPaint(0,Color.GREEN);
         renderer.setSeriesPaint(1,Color.RED);
         renderer.setSeriesPaint(2,Color.BLUE);
         renderer.setSeriesPaint(3,Color.white);
         renderer.setSeriesLinesVisible(0, false);//relient-les-poits-entre-eux-par-ligne
         renderer.setSeriesLinesVisible(1, true);
         renderer.setSeriesLinesVisible(2, true);
         renderer.setSeriesLinesVisible(3, true);
         renderer.setSeriesShape(0, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(1, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(2, new Ellipse2D.Double(-1, -1, 2, 2));
         renderer.setSeriesShape(3, new Ellipse2D.Double(0, 0, 0, 0));
         renderer.setSeriesStroke(0, new BasicStroke(5));//epesseur-de-coleur  
         renderer.setSeriesStroke(1, new BasicStroke(1));
         renderer.setSeriesStroke(2, new BasicStroke(1));
         renderer.setSeriesStroke(3, new BasicStroke(2));
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
    