package nl.jackploeg.aoc._2025.calendar.day08

import javax.inject.Inject
import kotlin.Int
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.extractInts
import kotlin.math.max
import kotlin.math.min

class Day08 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String, numConnections: Int): Long {
        val junctionBoxes = mutableListOf<Box>()
        generatorFactory.forFile(filename).read() { input ->
            input.forEachIndexed { index, line ->
                val xyz = line.extractInts()
                junctionBoxes.add(Box(xyz[0], xyz[1], xyz[2], index))
            }
            val distanceMap = mutableMapOf<Box, List<BoxWithDistance>>()
            junctionBoxes.forEach { box ->
                    distanceMap[box] = junctionBoxes
                        .filter { it != box }
                        .map { BoxWithDistance(box, it, box.distance(it)) }
                        .sortedBy { it.distance }
            }

            val sortedMap = distanceMap.flatMap { it.value }.sortedBy { it.distance }
            var i = 0
            var connected = 0
            var previousPair: Pair<Box, Box> = Pair(Box(-1,-1,-1,-1), Box(-1,-1,-1, -1))
            while (connected < numConnections) {

                val closestPair = sortedMap[i]

                if (closestPair.box != previousPair.second || closestPair.toBox != previousPair.first) {
                    previousPair = Pair( closestPair.box, closestPair.toBox)

                    val firstCircuit = min(closestPair.box.circuit, closestPair.toBox.circuit)
                    val secondCircuit = max(closestPair.box.circuit, closestPair.toBox.circuit)

                    if (firstCircuit!=secondCircuit) {
                        junctionBoxes.filter { it.circuit == secondCircuit }.forEach {
                            it.circuit = firstCircuit
                        }
                    }
                    connected++
                }
                i++
            }
        }
        return junctionBoxes.groupBy { it.circuit }
            .map { it.key to it.value.size }
            .sortedByDescending { it.second }
            .take(3)
            .map { it.second.toLong() }
            .reduce(Long::times)
    }

    fun partTwo(filename: String): Long {
        val junctionBoxes = mutableListOf<Box>()
        var previousPair: Pair<Box, Box> = Pair(Box(-1,-1,-1,-1), Box(-1,-1,-1, -1))
        generatorFactory.forFile(filename).read() { input ->
            input.forEachIndexed { index, line ->
                val xyz = line.extractInts()
                junctionBoxes.add(Box(xyz[0], xyz[1], xyz[2], index))
            }
            val distanceMap = mutableMapOf<Box, List<BoxWithDistance>>()
            junctionBoxes.forEach { box ->
                distanceMap[box] = junctionBoxes
                    .filter { it != box }
                    .map { BoxWithDistance(box, it, box.distance(it)) }
                    .sortedBy { it.distance }
            }

            val sortedMap = distanceMap.flatMap { it.value }.sortedBy { it.distance }
            var i = 0
            var connected = 0
            while (junctionBoxes.any { it.circuit > 0 }) {

                val closestPair = sortedMap[i]

                if (closestPair.box != previousPair.second || closestPair.toBox != previousPair.first) {
                    previousPair = Pair( closestPair.box, closestPair.toBox)

                    val firstCircuit = min(closestPair.box.circuit, closestPair.toBox.circuit)
                    val secondCircuit = max(closestPair.box.circuit, closestPair.toBox.circuit)

                    if (firstCircuit!=secondCircuit) {
                        junctionBoxes.filter { it.circuit == secondCircuit }.forEach {
                            it.circuit = firstCircuit
                        }
                    }
                    connected++
                }
                i++
            }
        }
        return previousPair.first.x.toLong() * previousPair.second.x.toLong()
    }

    data class Box(val x: Int, val y: Int, val z: Int, var circuit: Int) {

        fun distance(other: Box): Double {
            val dx = (x - other.x).toDouble()
            val dy = (y - other.y).toDouble()
            val dz = (z - other.z).toDouble()
            return kotlin.math.sqrt(dx * dx + dy * dy + dz * dz)
        }
    }

    data class BoxWithDistance(val box: Box, val toBox: Box, val distance: Double)
}
