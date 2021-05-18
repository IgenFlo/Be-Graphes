package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

/* TESTS :
 * chemin inexistant
 * chemon de longueur nulle
 * */

public class DijkstraTest {
	
	/* Pour chaque scénario :
	 * Carte (tester routières et non routières)
	 * Nature du coût (temps ou distance)
	 * Une origine, une destination (changer)
	 */
	
	//chemin d'une map
	static private String mapName;
	
	//reader
	static private GraphReader graphReader;
	
	//Graphe(s) utilisé pour les tests
	static private Graph graph;
	
	//Dijkstra algorithm
	private DijkstraAlgorithm algoDijkstra;
	
	//Bellman-Ford Algorithm
	private BellmanFordAlgorithm algoBellmanFord;

	@BeforeClass
	public static void initAll() throws IOException {
		DijkstraTest.mapName = "/Users/floeh/Desktop/INSA/3A/S2/maps_be_graphes/insa.mapgr";
		DijkstraTest.graphReader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(DijkstraTest.mapName)))); //throws FileNotFoundException
		DijkstraTest.graph = graphReader.read(); //throws IOException
	}
	
	@Test
	public void run() {
		//INIT ALGOS
		int num_origin = 650;
		int num_dest = 241;
		Node node_origin = DijkstraTest.graph.getNodes().get(num_origin);
		Node node_dest = DijkstraTest.graph.getNodes().get(num_dest);
		ArcInspector filter = ArcInspectorFactory.getAllFilters().get(1);
		
		this.algoDijkstra = new DijkstraAlgorithm(
				new ShortestPathData(DijkstraTest.graph, node_origin, node_dest, filter));
		
		this.algoBellmanFord = new BellmanFordAlgorithm(
				new ShortestPathData(DijkstraTest.graph, node_origin, node_dest, filter));
		
		ShortestPathSolution solutionDijkstra = this.algoDijkstra.doRun();
		ShortestPathSolution solutionBellmanFord = this.algoBellmanFord.doRun();
		
		//ASSERTIONS
		assertEquals(solutionDijkstra.getSolvingTime(), solutionBellmanFord.getSolvingTime());
	}
}

