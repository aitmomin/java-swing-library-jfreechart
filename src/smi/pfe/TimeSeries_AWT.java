/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smi.pfe;

/**
 *
 * @author TM161
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.general.SeriesException; 
import org.jfree.data.time.Second; 
import org.jfree.data.time.TimeSeries; 
import org.jfree.data.time.TimeSeriesCollection; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;
import static smi.pfe.APP.getConn;

public class TimeSeries_AWT extends ApplicationFrame 
{
   public TimeSeries_AWT( final String title ) throws SQLException
   {
      super( title );         
      final XYDataset dataset = createDataset( );         
      final JFreeChart chart = createChart( dataset );         
      final ChartPanel chartPanel = new ChartPanel( chart );         
      chartPanel.setPreferredSize( new java.awt.Dimension( 600 , 400 ) );         
      chartPanel.setMouseZoomable( true , false );         
      setContentPane( chartPanel );
   }

   private XYDataset createDataset( ) throws SQLException 
   {
      final TimeSeries series = new TimeSeries( "Random Data" );         
      Second current = new Second( );         
      double value = 100.0;         
      for (int i = 0; i < 4000; i++)    
      {
         try 
         {
            value = value + Math.random( ) - 0.5;                 
            series.add(current, new Double( value ) );                 
            current = ( Second ) current.next( ); 
         }
         catch ( SeriesException e ) 
         {
            System.err.println("Error adding to series");
         }
      }
//            Statement st = getConn().createStatement();
//            ResultSet rs = st.executeQuery("select temps,taille from paquet;");
//            while(rs.next()){
//                series.add(Second.parseSecond(rs.getString(1)), Double.parseDouble(rs.getString(2)));
//            }
      return new TimeSeriesCollection(series);
   }     

   private JFreeChart createChart( final XYDataset dataset ) 
   {
      return ChartFactory.createTimeSeriesChart(             
      "Computing Test", 
      "Temps",              
      "Taille",              
      dataset,             
      false,              
      false,              
      false);
   }

   
}  
