/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 *
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 *
 */

/*
    This class was based on the WorldMapGraphDemo.java
    It was totally adapted for traffic simulation based on real OCM maps and directed graphs
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.MultiLayerTransformer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ChainedTransformer;

public class Simulation
{
    public static void createAndShowGUI()
    {
        //Using EPSG:3857
        Coordinate CenterMap = new Coordinate(-8573920.0,4705040.0);
        double Interval = 80;   //Size of each quadrant
        int MonitorWidth = 1366;
        int MonitorHeight = 768;
        int HorizontalSquares = 4;    //Map quadrants
        int VerticalSquares = 4;      //Map quadrants
        String imageLocation = "/basicMap.png";

        Map<String, String[]> map = new HashMap<>();

        JFrame f = new JFrame();
        Graph<String, Number> graph = getGraph(map);
        int ImageWidth, ImageHeight;

        MapImage basicMap = new MapImage(imageLocation);
        ImageIcon icon;
        icon = basicMap.getMapImageDimensions();

        ImageHeight = basicMap.mapImageheight;
        ImageWidth = basicMap.mapImagewidth;

        Dimension layoutSize = new Dimension(ImageWidth, ImageHeight);
        Dimension monitorDimension = new Dimension(MonitorWidth, MonitorHeight);

        StaticLayout<String, Number> layout = new StaticLayout<>(graph,
                new ChainedTransformer<>(new Transformer[]{
                                new NodeTransformer(map),
                                new LatLonPixelTransformer(layoutSize, CenterMap, Interval,HorizontalSquares, VerticalSquares)
                        }));

        layout.setSize(layoutSize);

        VisualizationViewer<String,Number> vv = new VisualizationViewer<>(layout,
                monitorDimension);

        //Set Edge Shape to Line
        vv.getRenderContext().setEdgeShapeTransformer(
                new EdgeShape.Line());

        //Draw Map Layout
        if (icon != null) vv.addPreRenderPaintable(new VisualizationViewer.Paintable() {
            public void paint(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, icon.getIconWidth(), icon.getIconHeight(), vv);
            }

            public boolean useTransform() {
                return false;
            }
        });

        AnimatedCar ferrari = new AnimatedCar(10,20,Color.BLUE);
        BufferedImage image = ferrari.MovingCar();
        Number edge = graph.getEdges().iterator().next();

        final ImageAtEdgePainter<String, Number> imageAtEdgePainter =
                new ImageAtEdgePainter<>(vv, edge, image);

        //Animation

        Timer t = new Timer(20, new ActionListener()
        {
            long prevMillis = 0;
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (prevMillis == 0)
                {
                    prevMillis = System.currentTimeMillis();
                }

                long dtMs = System.currentTimeMillis() - prevMillis;
                double dt = dtMs / 1000.0;  //change for speed
                double phase = 0.5 + Math.sin(dt) * 0.5;
                imageAtEdgePainter.setImageLocation(phase);
                vv.repaint();
            }
        });

        t.start();

        vv.addPostRenderPaintable(imageAtEdgePainter);

        f.getContentPane().add(vv);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    static class ImageAtEdgePainter<V, E> implements VisualizationViewer.Paintable
    {
        private final VisualizationViewer<V, E> vv;
        private final E edge;
        private final BufferedImage image;
        private double imageLocation;

        ImageAtEdgePainter(
                VisualizationViewer<V, E> vv,
                E edge,
                BufferedImage image)
        {
            this.vv = vv;
            this.edge = edge;
            this.image = image;
        }

        public void setImageLocation(double imageLocation)
        {
            this.imageLocation = imageLocation;
        }

        @Override
        public void paint(Graphics gr)
        {
            Shape shape = getTransformedEdgeShape(vv, vv.getGraphLayout(), edge);
            Point2D p = computePointAt(shape, 0.2, imageLocation);
            gr.drawImage(image, (int)p.getX(), (int)p.getY(), null);
        }
        @Override
        public boolean useTransform()
        {
            return true;
        }
    }

    private static double computeLength(Shape shape, double flatness)
    {
        double length = 0;
        PathIterator pi = shape.getPathIterator(null, flatness);
        double[] coords = new double[6];
        double previous[] = new double[2];
        while (!pi.isDone())
        {
            int segment = pi.currentSegment(coords);
            switch (segment)
            {
                case PathIterator.SEG_MOVETO:
                    previous[0] = coords[0];
                    previous[1] = coords[1];
                    break;

                case PathIterator.SEG_LINETO:
                    double dx = previous[0]-coords[0];
                    double dy = previous[1]-coords[1];
                    length += Math.sqrt(dx*dx+dy*dy);
                    previous[0] = coords[0];
                    previous[1] = coords[1];
                    break;
            }
            pi.next();
        }
        return length;
    }

    public static Point2D computePointAt(
            Shape shape, double flatness, double alpha)
    {
        alpha = Math.min(1.0, Math.max(0.0, alpha));
        double totalLength = computeLength(shape, flatness);
        double targetLength = alpha * totalLength;
        double currentLength = 0;
        PathIterator pi = shape.getPathIterator(null, flatness);
        double[] coords = new double[6];
        double previous[] = new double[2];
        while (!pi.isDone())
        {
            int segment = pi.currentSegment(coords);
            switch (segment)
            {
                case PathIterator.SEG_MOVETO:
                    previous[0] = coords[0];
                    previous[1] = coords[1];
                    break;

                case PathIterator.SEG_LINETO:
                    double dx = previous[0]-coords[0];
                    double dy = previous[1]-coords[1];
                    double segmentLength = Math.sqrt(dx*dx+dy*dy);
                    double nextLength = currentLength + segmentLength;
                    if (nextLength >= targetLength)
                    {
                        double localAlpha =
                                (currentLength - targetLength) / segmentLength;
                        //System.out.println("current "+currentLength+" target "+targetLength+" seg "+segmentLength);
                        double x = previous[0] + localAlpha * dx;
                        double y = previous[1] + localAlpha * dy;
                        return new Point2D.Double(x,y);
                    }
                    previous[0] = coords[0];
                    previous[1] = coords[1];
                    currentLength = nextLength;
                    break;
            }
            pi.next();
        }
        return null;
    }

    // This method is taken from JUNG ShapePickSupport.java
    private static <V, E>  Shape getTransformedEdgeShape(
            VisualizationViewer<V, E> vv, Layout<V, E> layout, E e) {
        Pair<V> pair = layout.getGraph().getEndpoints(e);
        V v1 = pair.getFirst();
        V v2 = pair.getSecond();
        boolean isLoop = v1.equals(v2);
        RenderContext<V, E> rc = vv.getRenderContext();
        MultiLayerTransformer multiLayerTransformer =
                rc.getMultiLayerTransformer();
        Point2D p1 = multiLayerTransformer.transform(
                Layer.LAYOUT, layout.transform(v1));
        Point2D p2 = multiLayerTransformer.transform(
                Layer.LAYOUT, layout.transform(v2));
        if(p1 == null || p2 == null)
            return null;
        float x1 = (float) p1.getX();
        float y1 = (float) p1.getY();
        float x2 = (float) p2.getX();
        float y2 = (float) p2.getY();
        AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);
        Shape edgeShape =
                rc.getEdgeShapeTransformer().transform(
                        Context.<Graph<V,E>,E>getInstance(
                                vv.getGraphLayout().getGraph(),e));
        if(isLoop) {
            Shape s2 = rc.getVertexShapeTransformer().transform(v2);
            Rectangle2D s2Bounds = s2.getBounds2D();
            xform.scale(s2Bounds.getWidth(),s2Bounds.getHeight());
            xform.translate(0, -edgeShape.getBounds2D().getHeight()/2);
        } else {
            float dx = x2 - x1;
            float dy = y2 - y1;
            double theta = Math.atan2(dy,dx);
            xform.rotate(theta);
            float dist = (float) Math.sqrt(dx*dx + dy*dy);
            xform.scale(dist, 1.0f);
        }
        edgeShape = xform.createTransformedShape(edgeShape);
        return edgeShape;
    }

    private static Graph<String, Number> getGraph(Map<String, String[]> map)
    {
        Graph<String, Number> g = new DirectedSparseMultigraph<>();

        //play with these values

        map.put("POINT1", new String[]{"4705190", "-8574039"});
        map.put("POINT2", new String[]{"4705190", "-8573817"});
        //map.put("POINT3", new String[]{"4704960", "-8574039"});
        map.put("POINT4", new String[]{"4704960", "-8573817"});

        for (String node : map.keySet()) g.addVertex(node);

        //g.addEdge(1,"POINT1","POINT2",EdgeType.DIRECTED);
        //g.addEdge(2,"POINT1","POINT3",EdgeType.DIRECTED);

        g.addEdge(3,"POINT2","POINT4",EdgeType.DIRECTED);
        //g.addEdge(4,"POINT1","POINT3",EdgeType.DIRECTED);

        return g;
    }

    static class NodeTransformer implements Transformer<String, String[]> {

        Map<String, String[]> map;

        public NodeTransformer(Map<String, String[]> map) {
            this.map = map;
        }

        public String[] transform(String node) {
            return map.get(node);
        }
    }
    //this method was adapted from JUNG WorldMapGraphDemo.class
    static class LatLonPixelTransformer implements Transformer<String[], Point2D> {
        Dimension Dimension;
        Coordinate Origin;
        Double Interval;
        int HorizontalSquares;
        int VerticalSquares;

        public LatLonPixelTransformer(Dimension d, Coordinate Origin, Double Interval, int HorizontalSquares, int VerticalSquares) {
            this.Dimension = d;
            this.Origin = Origin;
            this. Interval = Interval;
            this.HorizontalSquares = HorizontalSquares;
            this.VerticalSquares = VerticalSquares;
        }

        public Point2D transform(String[] latlon) {

            double latitude = 0;
            double longitude = 0;

            latitude = Double.parseDouble(latlon[0]);
            latitude = Math.abs(Origin.y) - Math.abs(latitude);
            latitude *= Dimension.height / (Interval * VerticalSquares);

            longitude = Double.parseDouble(latlon[1]);
            longitude = Math.abs(Origin.x) - Math.abs(longitude);
            longitude *= (Dimension.width / (Interval * HorizontalSquares));

            if (latitude > 0) {
                latitude = Dimension.height / 2 + latitude;
            } else {
                latitude = Dimension.height / 2 + latitude;
            }

            if (longitude > 0) {
                longitude = Dimension.width / 2 + longitude;
            } else longitude = Dimension.width / 2 + longitude;

            return new Point2D.Double(longitude, latitude);
        }
    }
}