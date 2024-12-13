package nl.jackploeg.aoc._2024.calendar.day12

import nl.jackploeg.aoc.grid.*
import javax.inject.Inject

class Day12 @Inject constructor() {

    val plottedPlants = mutableListOf<Cell>()
    val plots = mutableListOf<Plot>()

    fun partOne(filename: String): Int {
        val grid = CharacterGrid().fromFile(filename)
        grid.grid.forEach { plant ->
            if (!plottedPlants.contains(plant.key)) {
                val newPlot = Plot(plant.value, mutableSetOf<Cell>())
                val plants = surroundingSamePlants(grid, plant.key, plant.value, mutableSetOf())
                newPlot.plants.addAll(plants)
                newPlot.plants.add(plant.key)
                plots.add(newPlot)
            }
            plottedPlants.add(plant.key)
        }
        return plots.sumOf { it.cost() }
    }

    fun partTwo(filename: String): Int {
        val grid = CharacterGrid().fromFile(filename)
        grid.grid.forEach { plant ->
            if (!plottedPlants.contains(plant.key)) {
                val newPlot = Plot(plant.value, mutableSetOf<Cell>())
                val plants = surroundingSamePlants(grid, plant.key, plant.value, mutableSetOf())
                newPlot.plants.addAll(plants)
                newPlot.plants.add(plant.key)
                plots.add(newPlot)
            }
            plottedPlants.add(plant.key)
        }
        return plots.sumOf { it.costByPerimeterCount() }
    }

    fun surroundingSamePlants(grid: CharacterGrid, cell: Cell, plant: Char, plot: MutableSet<Cell>): Set<Cell> {
        val surroundingSamePlants = cell.neighbours().filter { !plot.contains(it) && grid.grid.contains(it) && grid.grid[it] == plant }
        if (surroundingSamePlants.size == 0) return plot
        surroundingSamePlants.forEach {
            plot.add(it)
            plottedPlants.add(it)
            plot.addAll( surroundingSamePlants(grid, it, plant, plot) )
        }
        return plot
    }

    data class Plot(val char: Char, val plants: MutableSet<Cell>) {

        fun perimeter(): Int =
            plants.sumOf { it.neighbours().count { !plants.contains(it) } }

        fun perimeterCount(): Int {
            val borderPlants = plants.filter { it.neighbours().any { !plants.contains(it) } }
            val borders = borderPlants.map { it.neighboursWithDirection().filter { !plants.contains(it.cell) } }.flatten()
            val uniqueBorders = borders.filter { border ->
                border.direction == Direction.NORTH && !borders.contains(NeighbourWithDirection(Cell(border.cell.row, border.cell.col + 1), Direction.NORTH)) ||
                border.direction == Direction.SOUTH && !borders.contains(NeighbourWithDirection(Cell(border.cell.row, border.cell.col + 1), Direction.SOUTH)) ||
                border.direction == Direction.EAST && !borders.contains(NeighbourWithDirection(Cell(border.cell.row +1, border.cell.col), Direction.EAST)) ||
                border.direction == Direction.WEST && !borders.contains(NeighbourWithDirection(Cell(border.cell.row +1, border.cell.col), Direction.WEST))
            }
            return uniqueBorders.size
        }


        fun cost(): Int =
            plants.size * perimeter()

        fun costByPerimeterCount(): Int =
            plants.size * perimeterCount()
    }

    data class HorizontalPerimeter(val row: Int, val startCol: Int, val direction: Direction) {
        var length: Int = 1
            get() = field

        fun incrementLength() {
            length++
        }
    }
    data class VerticalPerimeter(val col: Int, val startRow: Int, val direction: Direction) {
        var length: Int = 1
            get() = field

        fun incrementLength() {
            length++
        }
    }
}
