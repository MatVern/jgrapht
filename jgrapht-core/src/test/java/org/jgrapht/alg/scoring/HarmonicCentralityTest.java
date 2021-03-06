/*
 * (C) Copyright 2017-2017, by Dimitrios Michail and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.alg.scoring;

import static org.junit.Assert.assertEquals;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.VertexScoringAlgorithm;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Test;

/**
 * Unit tests for harmonic centrality.
 * 
 * @author Dimitrios Michail
 */
public class HarmonicCentralityTest
{

    @Test
    public void testOutgoing()
    {
        Graph<String, DefaultEdge> g = createInstance1();

        VertexScoringAlgorithm<String, Double> pr = new HarmonicCentrality<>(g, false, true);

        assertEquals((1d / 1 + 1d / 1 + 1d / 2 + 1d / 3) / 4, pr.getVertexScore("1"), 1e-9);
        assertEquals((1d / 3 + 1d / 1 + 1d / 2 + 1d / 3) / 4, pr.getVertexScore("2"), 1e-9);
        assertEquals((1d / 2 + 1d / 3 + 1d / 1 + 1d / 2) / 4, pr.getVertexScore("3"), 1e-9);
        assertEquals((1d / 1 + 1d / 2 + 1d / 2 + 1d / 1) / 4, pr.getVertexScore("4"), 1e-9);
        assertEquals((1d / 3 + 1d / 4 + 1d / 1 + 1d / 2) / 4, pr.getVertexScore("5"), 1e-9);
    }

    @Test
    public void testIncoming()
    {
        Graph<String, DefaultEdge> g = createInstance1();

        VertexScoringAlgorithm<String, Double> pr = new HarmonicCentrality<>(g, true, true);

        assertEquals((1d / 3 + 1d / 2 + 1d / 1 + 1d / 3) / 4, pr.getVertexScore("1"), 1e-9);
        assertEquals((1d / 1 + 1d / 3 + 1d / 2 + 1d / 4) / 4, pr.getVertexScore("2"), 1e-9);
        assertEquals((1d / 1 + 1d / 1 + 1d / 2 + 1d / 1) / 4, pr.getVertexScore("3"), 1e-9);
        assertEquals((1d / 2 + 1d / 2 + 1d / 1 + 1d / 2) / 4, pr.getVertexScore("4"), 1e-9);
        assertEquals((1d / 3 + 1d / 3 + 1d / 2 + 1d / 1) / 4, pr.getVertexScore("5"), 1e-9);
    }

    @Test
    public void testIncomingNoNormalization()
    {
        Graph<String, DefaultEdge> g = createInstance1();

        VertexScoringAlgorithm<String, Double> pr = new HarmonicCentrality<>(g, true, false);

        assertEquals((1d / 3 + 1d / 2 + 1d / 1 + 1d / 3), pr.getVertexScore("1"), 1e-9);
        assertEquals((1d / 1 + 1d / 3 + 1d / 2 + 1d / 4), pr.getVertexScore("2"), 1e-9);
        assertEquals((1d / 1 + 1d / 1 + 1d / 2 + 1d / 1), pr.getVertexScore("3"), 1e-9);
        assertEquals((1d / 2 + 1d / 2 + 1d / 1 + 1d / 2), pr.getVertexScore("4"), 1e-9);
        assertEquals((1d / 3 + 1d / 3 + 1d / 2 + 1d / 1), pr.getVertexScore("5"), 1e-9);
    }

    @Test
    public void testUndirected()
    {
        Graph<String, DefaultEdge> g = new AsUndirectedGraph<>(createInstance1());

        VertexScoringAlgorithm<String, Double> pr1 = new HarmonicCentrality<>(g, true, true);
        VertexScoringAlgorithm<String, Double> pr2 = new HarmonicCentrality<>(g, false, true);

        assertEquals((1d / 1 + 1d / 1 + 1d / 1 + 1d / 2) / 4, pr1.getVertexScore("1"), 1e-9);
        assertEquals((1d / 1 + 1d / 1 + 1d / 1 + 1d / 2) / 4, pr2.getVertexScore("1"), 1e-9);
        assertEquals((1d / 1 + 1d / 1 + 1d / 2 + 1d / 2) / 4, pr1.getVertexScore("2"), 1e-9);
        assertEquals((1d / 1 + 1d / 1 + 1d / 2 + 1d / 2) / 4, pr2.getVertexScore("2"), 1e-9);
        assertEquals((1d / 1 + 1d / 1 + 1d / 1 + 1d / 1) / 4, pr1.getVertexScore("3"), 1e-9);
        assertEquals((1d / 1 + 1d / 1 + 1d / 1 + 1d / 1) / 4, pr2.getVertexScore("3"), 1e-9);
        assertEquals((1d / 1 + 1d / 2 + 1d / 1 + 1d / 1) / 4, pr1.getVertexScore("4"), 1e-9);
        assertEquals((1d / 1 + 1d / 2 + 1d / 1 + 1d / 1) / 4, pr2.getVertexScore("4"), 1e-9);
        assertEquals((1d / 2 + 1d / 2 + 1d / 1 + 1d / 1) / 4, pr1.getVertexScore("5"), 1e-9);
        assertEquals((1d / 2 + 1d / 2 + 1d / 1 + 1d / 1) / 4, pr2.getVertexScore("5"), 1e-9);
    }

    @Test
    public void testNegativeWeights()
    {
        DirectedWeightedPseudograph<String, DefaultWeightedEdge> g =
            new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);

        g.addVertex("1");
        g.addVertex("2");
        g.addVertex("3");
        g.addVertex("4");
        g.addVertex("5");
        g.addEdge("1", "2");
        DefaultWeightedEdge e13 = g.addEdge("1", "3");
        g.addEdge("2", "3");
        g.addEdge("3", "4");
        g.addEdge("4", "1");
        g.addEdge("4", "5");
        g.addEdge("5", "3");

        g.setEdgeWeight(e13, -1d);

        VertexScoringAlgorithm<String, Double> pr = new HarmonicCentrality<>(g, false, true);

        assertEquals(Double.POSITIVE_INFINITY, pr.getVertexScore("1"), 1e-9);
        assertEquals((1d / 3 + 1d / 1 + 1d / 2 + 1d / 3) / 4, pr.getVertexScore("2"), 1e-9);
        assertEquals((1d / 2 + 1d / 3 + 1d / 1 + 1d / 2) / 4, pr.getVertexScore("3"), 1e-9);
        assertEquals(Double.POSITIVE_INFINITY, pr.getVertexScore("4"), 1e-9);
        assertEquals((1d / 3 + 1d / 4 + 1d / 1 + 1d / 2) / 4, pr.getVertexScore("5"), 1e-9);
    }

    @Test
    public void testDisconnectedOutgoing()
    {
        Graph<String, DefaultEdge> g = createInstance1();
        g.addVertex("6");

        VertexScoringAlgorithm<String, Double> pr = new HarmonicCentrality<>(g, false, true);

        assertEquals((1d / 1 + 1d / 1 + 1d / 2 + 1d / 3) / 5, pr.getVertexScore("1"), 1e-9);
        assertEquals((1d / 3 + 1d / 1 + 1d / 2 + 1d / 3) / 5, pr.getVertexScore("2"), 1e-9);
        assertEquals((1d / 2 + 1d / 3 + 1d / 1 + 1d / 2) / 5, pr.getVertexScore("3"), 1e-9);
        assertEquals((1d / 1 + 1d / 2 + 1d / 2 + 1d / 1) / 5, pr.getVertexScore("4"), 1e-9);
        assertEquals((1d / 3 + 1d / 4 + 1d / 1 + 1d / 2) / 5, pr.getVertexScore("5"), 1e-9);
        assertEquals(0d, pr.getVertexScore("6"), 1e-9);
    }

    @Test
    public void testSingletonWithNormalize()
    {
        DirectedPseudograph<String, DefaultEdge> g = new DirectedPseudograph<>(DefaultEdge.class);
        g.addVertex("1");
        VertexScoringAlgorithm<String, Double> pr = new HarmonicCentrality<>(g, false, true);
        assertEquals(0d, pr.getVertexScore("1"), 1e-9);
    }

    @Test
    public void testSingletonWithoutNormalize()
    {
        DirectedPseudograph<String, DefaultEdge> g = new DirectedPseudograph<>(DefaultEdge.class);
        g.addVertex("1");
        VertexScoringAlgorithm<String, Double> pr = new HarmonicCentrality<>(g, false, false);
        assertEquals(0d, pr.getVertexScore("1"), 1e-9);
    }

    private Graph<String, DefaultEdge> createInstance1()
    {
        DirectedPseudograph<String, DefaultEdge> g = new DirectedPseudograph<>(DefaultEdge.class);
        g.addVertex("1");
        g.addVertex("2");
        g.addVertex("3");
        g.addVertex("4");
        g.addVertex("5");
        g.addEdge("1", "2");
        g.addEdge("1", "3");
        g.addEdge("2", "3");
        g.addEdge("3", "4");
        g.addEdge("4", "1");
        g.addEdge("4", "5");
        g.addEdge("5", "3");
        return g;
    }

}
