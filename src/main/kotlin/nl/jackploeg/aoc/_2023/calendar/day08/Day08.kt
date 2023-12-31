package nl.jackploeg.aoc._2023.calendar.day08

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.leastCommonMultiple
import nl.jackploeg.aoc.utilities.readStringFile
import java.math.BigInteger
import javax.inject.Inject

class Day08 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  var directions = CharArray(0)
  var nodes: Map<String, Node> = mapOf()

  fun partOne(fileName: String): Int {
    val lines = readStringFile(fileName)
    directions = lines[0].toCharArray()
    nodes = lines.subList(2, lines.size).map { parseNode(it) }.associateBy { it.name }
    var node = nodes["AAA"]
    var step = 0
    while (node != null && node.name != "ZZZ") {
      val instruction = directions[step++ % directions.size]
      node = if (instruction == 'L')
        nodes[node.left]
      else
        nodes[node.right]
    }
    return step
  }

  fun partTwo(fileName: String): BigInteger {
    val lines = readStringFile(fileName)
    directions = lines[0].toCharArray()
    nodes = lines.subList(2, lines.size).map { parseNode(it) }.associateBy { it.name }
    val currentNodes = nodes.filter { it.key.endsWith('A') }.values
    val steps: MutableList<BigInteger> = mutableListOf()
    currentNodes.forEach { steps.add(getStepsToZ(it)) }
    return steps.reduce { acc, it -> leastCommonMultiple(acc, it) }
  }

  fun getStepsToZ(node: Node): BigInteger {
    var step = BigInteger.ZERO
    var dirPointer = 0
    var currentNode = node
    while (!currentNode.name.endsWith('Z')) {
      val instruction = directions[dirPointer]
      dirPointer++
      if (dirPointer >= directions.size)
        dirPointer = 0
      step++
      currentNode = if (instruction == 'L')
        nodes[currentNode.left]!!
      else
        nodes[currentNode.right]!!
    }
    return step
  }


  fun parseNode(it: String): Node {
    val subs = it.split(" ")
    val name = subs[0]
    val l = subs[2].substring(1, subs[2].length - 1)
    val r = subs[3].substring(0, subs[3].length - 1)
    return Node(name, l, r)
  }

  data class Node(val name: String, val left: String, val right: String)

}
