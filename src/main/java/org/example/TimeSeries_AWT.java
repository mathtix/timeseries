package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;

import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TimeSeries_AWT extends ApplicationFrame {

    public TimeSeries_AWT( final String title ) {
        super( title );
        final XYDataset dataset = createDataset( );
        final JFreeChart chart = createChart( dataset );

        File file = new File( "./time-series.png");
        try {
            ChartUtils.saveChartAsPNG(file, chart, 1024,768);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new Dimension( 1024 , 768 ) );
        chartPanel.setMouseZoomable( true , false );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( ) {
        final TimeSeries series = new TimeSeries( "Random Data" );
        Second current = new Second( );
        double value = 100.0;

        for (int i = 0; i < 4000; i++) {

            try {
                value = value + Math.random( ) - 0.5;
                series.add(current,  value );
                current = ( Second ) current.next( );
            } catch ( SeriesException e ) {
                System.err.println("Error adding to series");
            }
        }

        return new TimeSeriesCollection(series);
    }

    private JFreeChart createChart( final XYDataset dataset ) {
        return ChartFactory.createTimeSeriesChart(
                "Computing Test",
                "Seconds",
                "Value",
                dataset,
                false,
                false,
                false);
    }

    public static void main( final String[ ] args ) {
        final String title = "Time Series Management";
        final TimeSeries_AWT demo = new TimeSeries_AWT( title );
        demo.pack( );
        RefineryUtilities.positionFrameRandomly( demo );
        demo.setVisible( true );
    }
}