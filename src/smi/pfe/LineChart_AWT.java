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
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import static smi.pfe.APP.getConn;
/*
Year 	Number OF Schools
1970 	15
1980 	30
1990 	60
2000 	120
2013 	240
2014 	300
*/

public class LineChart_AWT extends ApplicationFrame
{
   public LineChart_AWT( String applicationTitle , String chartTitle ) throws SQLException
   {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "temps de transmission","Taille des paquets (octets)",
         createDataset(),
         PlotOrientation.HORIZONTAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 600 , 400 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset( ) throws SQLException
   {
      Statement st = getConn().createStatement();
      ResultSet rs = st.executeQuery("select taille,date from paquet where date like '%05/2017';");
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      while(rs.next()){
          dataset.addValue( rs.getInt(1) , "Paquets" ,rs.getString(2) );
      }
           
      return dataset;
   }
   
}