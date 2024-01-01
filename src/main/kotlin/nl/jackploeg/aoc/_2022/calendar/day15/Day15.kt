package nl.jackploeg.aoc._2022.calendar.day15

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day15 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
    // occupied spaces
   fun partOne(row: Int, fileName: String): Int {
        val input = readStringFile(fileName)

        val sensors: MutableList<Sensor> = mutableListOf()
        val beacons: MutableList<Beacon> = mutableListOf()

        for (line in input) {
            processLine(line, sensors, beacons)
        }

        val occupiedCoordinates: MutableSet<Int> = mutableSetOf()

        for (sensor in sensors) {
            occupiedCoordinates.addAll(sensor.intersections(row))
        }

        // remove beacons
        beacons.filter { it.y == row }.map { occupiedCoordinates.remove(it.x) }
        return occupiedCoordinates.size
    }

    // unoccupied space frequency
    fun partTwo(maxIndex: Int, fileName: String): Long {
        val input = readStringFile(fileName)

        val sensors: MutableList<Sensor> = mutableListOf()
        val beacons: MutableList<Beacon> = mutableListOf()

        for (line in input) {
            processLine(line, sensors, beacons)
        }
        var notCoveredCell: Cell? = null

        for (sensor in sensors) {
            var covered = false
            val otherSensors = sensors.subtract(listOf(sensor).toSet())
            for (edge in sensor.outerEdges(maxIndex)) {
                covered = false
                for (otherSensor in otherSensors) {
                    if (otherSensor.covers(edge)) {
                        covered = true
                        break
                    }
                }
                if (!covered) {
                    println("Not covered: $edge")
                    notCoveredCell = edge
                    break
                }
            }
            if (!covered)
                break
        }

        var notcoveredX = 0L
        var notcoveredY = 0L
        if (notCoveredCell != null) {
            notcoveredX = notCoveredCell.x.toLong()
            notcoveredY = notCoveredCell.y.toLong()
        }

        return (notcoveredX * 4000000L) + notcoveredY
    }

    fun processLine(line: String, sensors: MutableList<Sensor>, beacons: MutableList<Beacon>) {
        val match = Regex(".*x=(-?\\d+).*y=(-?\\d+).*x=(-?\\d+).*y=(-?\\d+)").find(line)!!
        val (sx, sy, beaconx, beacony) = match.destructured
        val x = Integer.parseInt(sx)
        val y = Integer.parseInt(sy)
        val bx = Integer.parseInt(beaconx)
        val by = Integer.parseInt(beacony)
        val radius = kotlin.math.abs(bx - x) + kotlin.math.abs(by - y)
        sensors.add(Sensor(x, y, radius))
        beacons.add(Beacon(bx, by))
    }

    data class Sensor(val x: Int, val y: Int, val radius: Int) {
        fun intersections(row: Int): List<Int> {
            val xPoints: MutableList<Int> = mutableListOf()
            if (row in (y - radius)..(y + radius)) {
                val width = kotlin.math.abs(radius - kotlin.math.abs((y - row))) * 2
                for (pos in (x - width / 2)..(x + width / 2)) {
                    xPoints.add(pos)
                }
            }
            return xPoints
        }

        fun outerEdges(maxIndex: Int): ArrayList<Cell> {
            val edges = arrayListOf<Cell>()
            // 2,10 3,11 4,12 .. 10,18
            for (i in 0..radius + 1) {
                if (x + i in 0..maxIndex && this.y - this.radius - 1 + i > 0 && this.y - this.radius - 1 + i <= maxIndex)
                    edges.add(Cell(this.x + i, this.y - this.radius - 1 + i))
            }

            // 9,19, 8,20 .. 2,26
            for (i in 1..radius + 1) {
                if (this.x + radius + 1 - i in 0..maxIndex && this.y + i >= 0 && this.y + i <= maxIndex)
                    edges.add(Cell(this.x + radius + 1 - i, this.y + i))
            }

            // 1,25 0,24 -1,23 .. -6,18
            for (i in 1..radius + 1) {
                if (this.x - i in 0..maxIndex && this.y + radius + 1 - i >= 0 && this.y + radius + 1 - i <= maxIndex)
                    edges.add(Cell(this.x - i, this.y + radius + 1 - i))
            }

            // -5,17 -4,16 .. 1,9
            for (i in radius downTo 1) {
                if (this.x - i in 0..maxIndex && this.y - radius - 1 + i >= 0 && this.y - radius - 1 + i <= maxIndex)
                    edges.add(Cell(this.x - i, this.y - radius - 1 + i))
            }
            return edges
        }

        fun covers(cell: Cell): Boolean =
            kotlin.math.abs(cell.x - x) + kotlin.math.abs(cell.y - y) <= radius

    }

    data class Beacon(val x: Int, val y: Int)

    data class Cell(val x: Int, val y: Int)
}
