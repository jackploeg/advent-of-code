package nl.jackploeg.aoc._2024.calendar.day23

import java.io.File
import javax.inject.Inject


class Day23 @Inject constructor() {
    fun partOne(filename: String): Int {
        val connections: MutableMap<String, MutableSet<String>> = readConnections(filename)

        val triplets: MutableSet<Triplet> = mutableSetOf()
        connections.forEach { connection1 ->
            connections.filter { it.key in connection1.value }.forEach { connection2 ->
                connection2.value.forEach {
                    if (connection1.value.contains(it)) {
                        triplets.add(Triplet(connection1.key, connection2.key, it))
                    }
                }
            }
        }

        return triplets.count { it.first.startsWith('t') || it.second.startsWith('t') || it.third.startsWith('t') }
    }

    fun partTwo(filename: String): String {
        val connections: MutableMap<String, MutableSet<String>> = readConnections(filename)

        val clusters = mutableSetOf<Set<String>>()

        for ((computer, directConnections) in connections) {
            var cluster = directConnections + computer
            for (directConnection in directConnections) {
                val indirectConnections = connections.getValue(directConnection)
                val commonConnections = cluster.intersect(indirectConnections + directConnection)
                if (commonConnections.size > 2) cluster = commonConnections
            }
            clusters.add(cluster)
        }

        val result = clusters.maxBy { it.size }.sorted()

        return result.joinToString(",")
    }

    private fun readConnections(filename: String): MutableMap<String, MutableSet<String>> {
        val connections: MutableMap<String, MutableSet<String>> = mutableMapOf()
        File(filename).readLines().map { line ->
            line.split('-').toTypedArray()
        }.forEach { connection ->
            val current0 = connections.getOrPut(connection[0]) { mutableSetOf() }
            current0.add(connection[1])
            val current1 = connections.getOrPut(connection[1]) { mutableSetOf() }
            current1.add(connection[0])
        }
        return connections
    }
}

data class Triplet(val first: String, val second: String, val third: String) {
    override fun equals(other: Any?): Boolean {
        if (other is Triplet) {
            return (first == other.first && second == other.second && third == other.third ||
                    first == other.second && second == other.first && third == other.third ||
                    first == other.third && second == other.second && third == other.first ||
                    first == other.second && second == other.third && third == other.first ||
                    first == other.first && second == other.third && third == other.second ||
                    first == other.third && second == other.first && third == other.second
                    )
        }
        return false
    }

    override fun hashCode() = listOf(first, second, third).sorted().joinToString { "" }.hashCode()

}

