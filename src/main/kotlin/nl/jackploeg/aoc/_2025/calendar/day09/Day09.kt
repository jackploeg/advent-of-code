package nl.jackploeg.aoc._2025.calendar.day09

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.grid.Cell
import nl.jackploeg.aoc.grid.col
import nl.jackploeg.aoc.grid.row
import nl.jackploeg.aoc.utilities.extractInts
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject

class Day09 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Long {
        var cells = mutableListOf<Cell>()
        generatorFactory.forFile(filename).readAs(::day09) { input ->
            input.forEach { cell ->
                cells.add(cell)
            }
        }
        var maxSurface = 0L
        cells.forEach { cell1 ->
            cells.filter { it != cell1 && it.row >= cell1.row && it.col >= cell1.col }
                .forEach { cell2 ->
                    val surface = (cell2.col - cell1.col + 1).toLong() * (cell2.row - cell1.row + 1).toLong()
                    if (surface > maxSurface) {
                        maxSurface = surface
                    }
                }
        }
        return maxSurface
    }

    fun partTwo(filename: String): Long {
        val input = readStringFile(filename)

        val cornerCells = input.map { it.split(",").let { Cell(it[0].toInt(), it[1].toInt()) } }
        val lines = (cornerCells + cornerCells.first()).windowed(2).map { it[0] to it[1] }

        var max = 0L
        for (corner in cornerCells) {
            for (oppositeCorner in cornerCells.filter { it != corner }) {
                // Rows and columns inside the rectangle
                val minRow = minOf(corner.row, oppositeCorner.row) + 1
                val maxRow = maxOf(corner.row, oppositeCorner.row) - 1
                val minCol = minOf(corner.col, oppositeCorner.col) + 1
                val maxCol = maxOf(corner.col, oppositeCorner.col) - 1

                // Area of the rectangle
                val width = maxCol - minCol + 1 + 2L
                val height = maxRow - minRow + 1 + 2L
                val area = width * height
                if (area <= max) continue

                val isCrossing = listOf(
                    Cell(minRow, minCol) to Cell(maxRow, minCol),
                    Cell(maxRow, minCol) to Cell(maxRow, maxCol),
                    Cell(maxRow, maxCol) to Cell(minRow, maxCol),
                    Cell(minRow, maxCol) to Cell(minRow, minCol),
                ).any { border ->
                    val verticalBorder = border.first.col == border.second.col
                    lines.any { line: Pair<Cell, Cell> ->
                        val verticalLine = line.first.col == line.second.col
                        if (verticalBorder && !verticalLine) {
                            // Check for vertical border crossing horizontal line
                            val borderMinRow = minOf(border.first.row, border.second.row)
                            val borderMaxRow = maxOf(border.first.row, border.second.row)
                            val lineMinCol = minOf(line.first.col, line.second.col)
                            val lineMaxCol = maxOf(line.first.col, line.second.col)
                            val lineRow = line.first.row
                            val borderCol = border.first.col
                            lineRow in borderMinRow..borderMaxRow && borderCol in lineMinCol..lineMaxCol
                        } else if (!verticalBorder && verticalLine) {
                            // Check for horizontal border crossing vertical line
                            val borderMinCol = minOf(border.first.col, border.second.col)
                            val borderMaxCol = maxOf(border.first.col, border.second.col)
                            val lineMinRow = minOf(line.first.row, line.second.row)
                            val lineMaxRow = maxOf(line.first.row, line.second.row)
                            val lineCol = line.first.col
                            val borderRow = border.first.row
                            lineCol in borderMinCol..borderMaxCol && borderRow in lineMinRow..lineMaxRow
                        } else false
                    }
                }
                if (isCrossing) continue
                max = area
            }
        }
        return max
    }

    private fun day09(line: String): Cell {
        val coords = line.extractInts()
        return Cell(coords.get(0), coords.get(1))
    }
}
