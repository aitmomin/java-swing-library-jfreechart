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
import javax.swing.JPanel; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart; 
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import static smi.pfe.APP.getConn;
//import static smi.pfe.APP.pan3;

 
public class PieChart_AWT extends ApplicationFrame 
{
   public PieChart_AWT( String title ) throws SQLException 
   {
      super( title ); 
      setContentPane(createDemoPanel( ));
   }
   private static PieDataset createDataset( ) throws SQLException  
   {
       Statement st = getConn().createStatement();
       // chaque application et le nombre de fois
       ResultSet rs = st.executeQuery("select application.Type,count(segment.ApplicationID) \n" +
                                        "from application,segment \n" +
                                        "WHERE application.ID=segment.ApplicationID \n" +
                                        "GROUP BY application.Type;");
      DefaultPieDataset dataset = new DefaultPieDataset( );
      while(rs.next()){
          dataset.setValue( rs.getString(1), rs.getInt(2) );  
      } 
      return dataset;         
   }
   private static JFreeChart createChart( PieDataset dataset )
   {
       
      JFreeChart chart = ChartFactory.createPieChart(      
         "Types des applications",  // chart title 
         dataset,        // data    
         true,           // include legend   
         true, 
         false);

      return chart;
   }
   public static JPanel createDemoPanel( ) throws SQLException
   {
      JFreeChart chart = createChart(createDataset( ) );
      ChartPanel cp = new ChartPanel( chart ); 
      return cp; 
   }   

}


