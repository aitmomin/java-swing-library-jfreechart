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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import static smi.pfe.APP.getConn;

/*
Car 	Speed 	User Rating 	Millage    Safety
Fiat 	1.0 	3.0             5.0         5.o
Audi 	5.0 	6.0             10.0        4.0
Ford 	4.0 	2.0             3.0         6.0
*/
public class BarChart_AWT extends ApplicationFrame
{
   public BarChart_AWT( String applicationTitle , String chartTitle ) throws SQLException
   {
      super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "@IP des machines",            
         "Nombre de paquets",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 600 , 400 ) );        
      setContentPane( chartPanel ); 
   }
   private CategoryDataset createDataset( ) throws SQLException
   {
              
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
      
      Statement st = getConn().createStatement();
      ResultSet rs = st.executeQuery("SELECT ip_src,COUNT(*) FROM `paquet` GROUP BY Ip_src;");
        while(rs.next()){
            dataset.addValue(rs.getInt(2),rs.getString(1),"@IP Source");        
        }
      
//      ResultSet rss = st.executeQuery("select Ip_dest,COUNT(*) FROM `paquet` GROUP BY Ip_dest;");               
//        while(rss.next()){
//            dataset.addValue(rss.getInt(2),rss.getString(1),"");
//        }      

      return dataset; 
   }
  
}