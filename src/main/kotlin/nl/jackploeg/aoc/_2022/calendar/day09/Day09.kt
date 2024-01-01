package nl.jackploeg.aoc._2022.calendar.day09

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day09 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String) =
        visitedCells(2, filename)

    fun partTwo(filename: String) =
        visitedCells(10,filename)

    fun visitedCells(numberOfKnots: Int, fileName: String): Int {
        val input = readStringFile(fileName)
        val startCell = Cell(0, 0)

        val knots = ArrayList<Knot>()
        repeat(numberOfKnots) {
            knots.add(Knot(startCell))
        }

        for (line in input) {
            move(line, knots)
        }
        return knots.last().visitedCells.count()
    }

    fun move(line: String, knots: ArrayList<Knot>) {
        val direction = line.substring(0, 1)
        val steps = Integer.parseInt(line.substring(2))
        for (step in 0 until steps) {
            var pos = knots.first().move(direction)
            for (i in 1 until knots.size) {
                pos = knots[i].follow(pos)
            }
        }
    }

    class Knot(private var position: Cell, val visitedCells: MutableSet<Cell> = mutableSetOf(position)) {
        fun move(direction: String): Cell {
            when (direction) {
                "U" -> position = Cell(position.x, position.y + 1)
                "D" -> position = Cell(position.x, position.y - 1)
                "L" -> position = Cell(position.x - 1, position.y)
                "R" -> position = Cell(position.x + 1, position.y)
            }
            visitedCells.add(position)
            return position
        }

        fun follow(pos: Cell): Cell {
            var x = position.x
            var y = position.y
            if (x == pos.x) {
                if (y < pos.y - 1) y++
                if (y > pos.y + 1) y--
            } else if (y == pos.y) {
                if (x < pos.x - 1) x++
                if (x > pos.x + 1) x--
            } else {
                // right top
                if (y < pos.y - 1 && x < pos.x) {
                    y++; x++
                }
                // left top
                else if (y < pos.y - 1 && x > pos.x) {
                    y++; x--
                }
                // right bottom
                else if (y > pos.y + 1 && x < pos.x) {
                    y--; x++
                }
                // right top
                else if (y > pos.y + 1 && x > pos.x) {
                    y--; x--
                }

                // right top
                else if (x < pos.x - 1 && y < pos.y) {
                    x++; y++
                }
                // left top
                else if (x < pos.x - 1 && y > pos.y) {
                    x++; y--
                }
                // right bottom
                else if (x > pos.x + 1 && y < pos.y) {
                    x--; y++
                }
                // right top
                else if (x > pos.x + 1 && y > pos.y) {
                    x--; y--
                }

            }
            position = Cell(x, y)
            visitedCells.add(position)
            return position
        }
    }


    data class Cell(val x: Int, val y: Int)
}
