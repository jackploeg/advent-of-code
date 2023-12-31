package nl.jackploeg.aoc._2023.calendar.day18

import nl.jackploeg.aoc.utilities.Direction
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject

class Day18 @Inject constructor() {

    var x = 0L
    var y = 0L
    var maxX = 0L
    var minX = 0L
    var maxY = 0L
    var minY = 0L

    fun partOne(fileName: String): Int
    {
        val edges: List<Edge> = readStringFile(fileName).map { toEdges(it) }.flatten()
        val enclosedCells: MutableList<Edge> = mutableListOf()

        //println(edges)
        maxX = edges.maxBy { it.x }.x
        minX = edges.minBy { it.x }.x
        maxY = edges.maxBy { it.y }.y
        minY = edges.minBy { it.y }.y

//    println("$minX, $minY - $maxX, $maxY")
        var inEnclosing = false
        var onEdge = false
        var onHorizontalEdge = false

//    println(edges)

        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (edges.contains(Edge(x, y)))
                {
                    if (onEdge)
                    {
                        onHorizontalEdge = true
                        inEnclosing = false
                    }
//                if (inEnclosing) {
//                    inEnclosing = false
//                }
                    onEdge = true
                } else
                {
                    // end of horizontal edge
                    if (onHorizontalEdge)
                    {
                        onEdge = false
                        onHorizontalEdge = false
                        if (edges.contains(Edge(x - 1, y + 1)))
                        {
                            // edge down
                            inEnclosing = false
                        }
                        if (enclosedCells.contains(Edge(x, y - 1)))
                        {
                            inEnclosing = true
                            enclosedCells.add(Edge(x, y))
                        }
                    } else if (onEdge)
                    {
                        onEdge = false
                        inEnclosing = !inEnclosing
                        if (inEnclosing)
                        {
                            enclosedCells.add(Edge(x, y))
                        }
                    } else if (inEnclosing)
                    {
                        enclosedCells.add(Edge(x, y))
                    }
                }
                //println("$x, $y -> $onEdge, $onHorizontalEdge, $inEnclosing")
            }
            onEdge = false
            onHorizontalEdge = false
            inEnclosing = false
        }

        //printList(enclosedCells)

        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (enclosedCells.contains(Edge(x, y)))
                    print(" ")
                else if (edges.contains(Edge(x, y)))
                    print("#")
                else print(".")
            }
            println()
        }
        return edges.size + enclosedCells.size
    }

    fun partTwo(fileName: String): Long
    {
        /* The "surveyor's formula." It gives the area of any planar polygon. If the vertices are (x1,y1), (x2,y2), ..., (xn,yy), then
        A = (1/2)[Det(x1,x2,y1,y2)+Det(x2,x3,y2,y3)+ ... +Det(xn,x1,yn,y1)],
        where Det(a,b,c,d) = a*d-b*c.
         */

        var edgeLength = 0L
        val edges: MutableList<Edge> = mutableListOf(Edge(0L, 0L))
        readStringFile(fileName).forEach { line ->
            val (length, direction) = colorToEdgeInfo(line)
            val lastEdge = edges.last()
            edgeLength += length
            when (direction)
            {
                Direction.RIGHT -> edges.add(Edge(lastEdge.x + length, lastEdge.y))
                Direction.LEFT -> edges.add(Edge(lastEdge.x - length, lastEdge.y))
                Direction.UP -> edges.add(Edge(lastEdge.x, lastEdge.y - length))
                Direction.DOWN -> edges.add(Edge(lastEdge.x, lastEdge.y + length))
            }
        }
        println(edges)
        println(edgeLength/2)

        var totalValue = 0L
        for (i in 1..edges.size -1 ) {
            //println(edges[i])
            totalValue += det(edges[i-1], edges[i])
        }
        //totalValue += det(edges.last(), edges.first())
        return ( (edgeLength+2) + totalValue) / 2
//    return  (totalValue / 2)
    }

    fun det(x: Edge, y: Edge): Long =
        //BigInteger(x.x.toString()) * BigInteger(y.y.toString()) - BigInteger(x.y.toString()) * BigInteger(y.x.toString())
        (x.x * y.y) - (x.y * y.x)

    fun toEdges(line: String): List<Edge>
    {
        val edges = mutableListOf<Edge>()
//    println(line)
        val (direction, distance, color) = line.split(' ')
        (0 until distance.toInt()).forEach {
            when (direction)
            {
                "R" -> edges.add(Edge(x++, y, color))
                "L" -> edges.add(Edge(x--, y, color))
                "D" -> edges.add(Edge(x, y++, color))
                "U" -> edges.add(Edge(x, y--, color))
            }
        }
        return edges
    }

    fun colorToEdgeInfo(line: String): Pair<Long, Direction>
    {
        val (_, _, color) = line.split(' ')
//    println(color)
//    println(color.substring(2, 7))
        val length = color.substring(2, 7).toLong(radix = 16)
        val directionNumber = color.substring(7, 8).toInt()
        val direction: Direction = when (directionNumber)
        {
            0 -> Direction.RIGHT
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.UP
            else -> throw RuntimeException("Invalid value for direction")
        }

        return Pair(length, direction)
    }

    data class Edge(val x: Long, val y: Long, val color: String = "")
    {
        override fun equals(other: Any?): Boolean
        {
            if (this === other) return true
            if (other !is Edge) return false

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int
        {
            var result = x
            result = 31 * result + y
            return result.toInt()
        }
    }
}