import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TaskHandler {
    private final String[] sexes = new String[]{"male", "female"};
    private final String[] embarkPlaces = new String[]{"S", "C", "Q"};

    public void printAllTickets(DBHandler dbHandler) throws SQLException {
        var tickets = dbHandler.findTicketsList();
        for (var ticket : tickets)
            System.out.println(ticket);
    }

    public void printDelta(DBHandler dbHandler){
        System.out.println(dbHandler.findDelta());
    }

    public JFreeChart createBarChart(DBHandler dbHandler) throws SQLException {
        var dataset = new DefaultCategoryDataset();
        for (var place : embarkPlaces)
            for (var sex : sexes){
                dataset.setValue(dbHandler.getAverageFare(sex, place), sex, place);
                System.out.println(dbHandler.getAverageFare(sex, place));
            }
        var chart = ChartFactory.createBarChart(
                "Средние цены билета",
                null,
                "Цена",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.black);
        var plot = chart.getCategoryPlot();
        var br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(0);
        var domain = plot.getDomainAxis();
        domain.setLowerMargin(0.25);
        domain.setUpperMargin(0.25);

        var frame = new JFrame("Таблица средних цен");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var cp = new ChartPanel(chart){

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 800);
            }
        };
        frame.add(cp);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return chart;
    }
}
