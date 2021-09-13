

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

import org.jfree.chart.*;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.*;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.*;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTreeUI.MouseInputHandler;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import java.awt.TextField;

import javax.swing.SwingConstants;

public class VisualiseEnergie extends JPanel  {	
	 
    static Crosshair xCrosshair; 
    static Crosshair yCrosshair;
           XYPlot plot;
           XYLineAndShapeRenderer renderer;
           NumberAxis rangeAxis;
           ValueAxis domainAxis;
	static JFreeChart chartV,chart1V;
	static  ChartPanel chartPanelV,chartPanel1V;
	private BufferedReader br;
	public VisualiseEnergie() throws IOException {
        XYDataset datasetV = createDataset();
        chartV = createChart(datasetV);        
        chartPanelV = new ChartPanel(chartV, true, true, true, false, true){  
        	@Override
            public Dimension getPreferredSize() {
        		
                return new Dimension(Mainn.panelV.getWidth(), Mainn.panelV.getHeight());
            }
        	
        };       
        chartPanelV.setFillZoomRectangle(false);
        chartPanelV.setMouseWheelEnabled(true);                
        add(chartPanelV);  
         
    }
      
	private XYDataset createDataset() throws IOException {
      
		XYSeries series = new XYSeries("data");
	

	  int je=Mainn.offsetofdata;
	  for(int j=0;j<Mainn.lengthofdata-Mainn.offsetofdata;j++) {
		  
		String Asv =Mainn.Asv1;
  	    float av =(float) Float.parseFloat(Asv);// a de energie    	    
  	    String Bsv =Mainn.Bsv1;
  	    float bv =(float) Float.parseFloat(Bsv);// b de energie   	    
  	    String Csv =Mainn.Csv1;
  	    float cv =(float) Float.parseFloat(Csv);// c de energie  	    
  	    String Dsv =Mainn.Dsv1;
  	    float dv =(float) Float.parseFloat(Dsv);// d de energie
  	    float enrgv=(float) (av+bv*je+cv*Math.pow(je, 2)+dv*Math.pow(je, 3));            
          series.add(je, enrgv);
          je=je+1;
	  }
 
        XYSeriesCollection datasetV = new XYSeriesCollection();
        datasetV.addSeries(series);
        return datasetV ;
        
    }

	private JFreeChart createChart( XYDataset datasetV) {
    
          chartV = ChartFactory.createXYLineChart(
            "",      // chart title
            "Channel",                      // x axis label
            "E(kev)",                      // y axis label
            datasetV,                  // data
            PlotOrientation.VERTICAL,
            false ,                     // include legend
            true,                     // tooltips
            true                     // urls
        );
         
 	     chartV.setBackgroundPaint(Color.BLACK);
 	     chartV.getTitle().setPaint(Color.white);         
	     chartV.setBackgroundPaint(Color.BLACK); 
 	     
 		 plot = chartV.getXYPlot();         
 		 plot.setBackgroundPaint(Color.BLACK);
         plot.setDomainGridlinePaint(Color.white);//Ligne-vertical
         plot.setRangeGridlinePaint(Color.white);  //ligne-horizontal    
         //plot.setOutlinePaint(Color.GRAY);//Cadre de graph
         plot.setOutlineStroke(new BasicStroke(2.5f));// ?  
         plot.setDomainCrosshairVisible(true);//pour voir la droite vertical
         plot.setDomainCrosshairPaint(Color.white);         
         plot.setRangeCrosshairVisible(true);//pour voir la droite horizontal
         plot.setRangeCrosshairPaint(Color.white);
         renderer = new XYLineAndShapeRenderer();         
         renderer.setSeriesPaint(0,Color.RED); //COLEUR-DATA
         renderer.setSeriesLinesVisible(0, false);//relient-les-poits-entre-eux-par-ligne
         renderer.setSeriesShape(0, new Ellipse2D.Double(-0.5, -0.5, 1, 1));// la forme de poits      
         renderer.setSeriesStroke(0, new BasicStroke(3));//epesseur-de-coleur   
         plot.setRenderer(renderer);
         rangeAxis = (NumberAxis) plot.getRangeAxis();
         rangeAxis.setTickLabelPaint(Color.WHITE);   // LA COULEUR DE nombre de l'axe vertical      
         rangeAxis.setLabelPaint(Color.white);  // la couleur son titre 
         domainAxis = plot.getDomainAxis();
         domainAxis.setTickLabelPaint(Color.white); // LA COULEUR DE nombre de l'axe horizontal        
         domainAxis.setLabelPaint(Color.white);  //la couleur son titre       
        return chartV;
        }
	

 
	
}


    