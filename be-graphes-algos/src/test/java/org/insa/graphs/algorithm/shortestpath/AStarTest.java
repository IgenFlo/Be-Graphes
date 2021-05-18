package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

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

public class AStarTest {
	
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
		AStarTest.mapName = "/Users/floeh/Desktop/INSA/3A/S2/maps_be_graphes/insa.mapgr";
		AStarTest.graphReader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(AStarTest.mapName)))); //throws FileNotFoundException
		AStarTest.graph = graphReader.read(); //throws IOException
	}
	
	@Test
	public void run() {
		int num_origin = 0;
		int num_dest = 0;
		int nb_nodes = AStarTest.graph.getNodes().size();
		for (int i = 0 ; i < 20 ; i++) {
			System.out.println("--------------------Test "+(i+1)+"--------------------");
			//origine et destination aleaotires
			num_origin = new Random().nextInt(nb_nodes);
			num_dest = new Random().nextInt(nb_nodes);
			System.out.println("origine : "+num_origin+" destination : "+num_dest);
			//Recuperation des noeuds et choix du filtre
			Node node_origin = AStarTest.graph.getNodes().get(num_origin);
			Node node_dest = AStarTest.graph.getNodes().get(num_dest);
			for (int j = 0 ; j < 5 ; j++) {
				ArcInspector filter = ArcInspectorFactory.getAllFilters().get(j);
				//Parcours et renvoi de la solution
				this.algoDijkstra = new DijkstraAlgorithm(
						new ShortestPathData(AStarTest.graph, node_origin, node_dest, filter));
				this.algoBellmanFord = new BellmanFordAlgorithm(
						new ShortestPathData(AStarTest.graph, node_origin, node_dest, filter));
				ShortestPathSolution solutionDijkstra = this.algoDijkstra.doRun();
				ShortestPathSolution solutionBellmanFord = this.algoBellmanFord.doRun();
				//Tests
				if (solutionDijkstra.isFeasible()) {
					//En temps
					System.out.print("Test en temps, filtre "+j+" "+filter+ " : ");
					System.out.println(solutionDijkstra.getPath().getMinimumTravelTime()-solutionBellmanFord.getPath().getMinimumTravelTime());
					assertTrue(solutionDijkstra.getPath().getMinimumTravelTime()-solutionBellmanFord.getPath().getMinimumTravelTime() <= 0.001f);
					//En distance
					System.out.print("Test en distance, filtre "+j+" "+filter+" : ");
					System.out.println(solutionDijkstra.getPath().getLength()-solutionBellmanFord.getPath().getLength());
					assertTrue(solutionDijkstra.getPath().getLength()-solutionBellmanFord.getPath().getLength() <= 0.001f);
				} else {
					System.out.print("Test chemin pas faisables : "+filter+" : ");
					System.out.println(!solutionBellmanFord.isFeasible());
					assertEquals(solutionDijkstra.isFeasible(), solutionBellmanFord.isFeasible());
				}
				
			}
			
			
		}
		
	}
}

