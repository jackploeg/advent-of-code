package nl.jackploeg.aoc._2023.calendar.day25

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import org.jgrapht.alg.StoerWagnerMinimumCut
import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleWeightedGraph
import java.io.File
import javax.inject.Inject


class Day25 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String): Int {

    val graph = SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)

    File(fileName).readLines().forEach { line ->
      val (name, others) = line.split(": ")
      graph.addVertex(name)
      others.split(" ").forEach { other ->
        graph.addVertex(other)
        graph.addEdge(name, other)
      }
    }

    val smallestGroup = StoerWagnerMinimumCut(graph)
    val numberOfCuts = smallestGroup.minCutWeight().toInt()
    if (numberOfCuts <= 3) {
      val smallestGroupSize = smallestGroup.minCut().size
      println("Group sizes: ${(graph.vertexSet().size - smallestGroupSize)}, $smallestGroupSize with $numberOfCuts cuts")
      return (graph.vertexSet().size - smallestGroupSize) * smallestGroupSize
    } else {
      println("Not possible with max 3 cuts")
    }
    return -1
  }
}
