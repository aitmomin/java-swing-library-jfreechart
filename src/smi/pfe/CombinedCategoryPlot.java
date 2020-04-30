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
 import java.awt.Font;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import static smi.pfe.APP.getConn;

public class CombinedCategoryPlot extends ApplicationFrame {

  public CombinedCategoryPlot(String titel) throws SQLException {
  super(titel);


  final JFreeChart chart = createChart();
  final ChartPanel chartPanel = new ChartPanel(chart);
  chartPanel.setPreferredSize(
  new java.awt.Dimension(600,400));
  setContentPane(chartPanel);
  }

  public CategoryDataset createDatasetTeam1() throws SQLException {

  final DefaultCategoryDataset result = new DefaultCategoryDataset();
  Statement st = getConn().createStatement();
  ResultSet rs = st.executeQuery("SELECT Add_ip,COUNT(Protocole) as \"nombre\" FROM paquet p,machine m WHERE p.MachineID=m.ID and Protocole like 'UDP' GROUP BY Add_ip");
  int i=0;
  while(rs.next()){
      result.addValue(rs.getInt(2),rs.getString(1), "" + (i + 1));
      i++;
  }
  return result;
  }

  public CategoryDataset createDatasetTeam2() throws SQLException {

  final DefaultCategoryDataset result = new DefaultCategoryDataset();

  Statement st = getConn().createStatement();
  ResultSet rs = st.executeQuery("SELECT Add_ip,COUNT(Protocole) as \"nombre\" FROM paquet p,machine m WHERE p.MachineID=m.ID and Protocole like 'TCP' GROUP BY Add_ip");
  int i=0;
  while(rs.next()){
      result.addValue(rs.getInt(2),rs.getString(1), "" + (i + 1));
      i++;
  }
  return result;
  }

  private JFreeChart createChart() throws SQLException {

  final CategoryDataset dataset1 = createDatasetTeam1();
  final NumberAxis rangeAxis1 = new NumberAxis("Protocole UDP");
  rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
  final BarRenderer renderer1 = new BarRenderer();
  renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
  final CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1, renderer1);
  subplot1.setDomainGridlinesVisible(true);


  final CategoryDataset dataset2 = createDatasetTeam2();
  final NumberAxis rangeAxis2 = new NumberAxis("Protocole TCP");
  rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
  final BarRenderer renderer2 = new BarRenderer();
  renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
  final CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2, renderer2);
  subplot2.setDomainGridlinesVisible(true);



  final CategoryAxis domainAxis = new CategoryAxis("@IP machines");
  final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);

  plot.add(subplot1, 1);
  plot.add(subplot2, 1);

  final JFreeChart chart = new JFreeChart("les statistiques d'utilisation des protocoles TCP/UDP",
          new Font("SansSerif", Font.BOLD,15), plot,  true);
  
  return chart;
  }

} 
