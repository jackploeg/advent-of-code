package nl.jackploeg.aoc._2025.calendar.day50

import nl.jackploeg.aoc._2022.calendar.day16.Day16.Valve
import nl.jackploeg.aoc._2022.calendar.day16.Day16.ValveNodeWithNeigbours
import java.io.File
import javax.inject.Inject
import kotlin.Int
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.grid.*
import nl.jackploeg.aoc.pathing.GenericIntDijkstra

class Day50 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int {
        val grid = intGridFromFile(filename)
        val solver = MyDijkstra()
        val paths = grid.entries.associateWith {
            solver.solve(CellNodeWithNeigbours(it.key).usingValves(grid))
        }
        // paths bevat nu alle paden van elke cell naar elke andere, met de totale value van cellen op het pad
        // we willen alleen het pad van linksboven naar rechtsonder
        val height = grid.maxBy { it.key.row }.key.row
        val width = grid.maxBy { it.key.col }.key.col
        val pathsFromTopLeft = paths.filter { it.key.key.row == 0 && it.key.key.col == 0 }
        val ourPath = pathsFromTopLeft.values.firstOrNull()?.filter { it.key.cell.row == 4 && it.key.cell.col == 7 }?.values?.sum()

        return ourPath?:0
    }

    fun partTwo(filename: String): Int {
        val input = File(filename).readLines()
        return -1
    }

    data class CellNodeWithNeigbours(val cell: Cell) : GenericIntDijkstra.DijkstraNode<CellNodeWithNeigbours> {
        lateinit var grid: Map<Cell, Int>

        fun usingValves(grid: Map<Cell, Int>) = apply { this.grid = grid }

        override fun neighbours(): Map<CellNodeWithNeigbours, Int> {
            return cell.neighbours()
                .filter { grid.contains(it) }
                .map { CellNodeWithNeigbours(it).usingValves(grid) }
                .associateWith { grid.get(it.cell)!! }
        }
    }

    class MyDijkstra: GenericIntDijkstra<CellNodeWithNeigbours>()
}
