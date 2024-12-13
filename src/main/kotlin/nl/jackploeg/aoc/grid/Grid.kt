package nl.jackploeg.aoc.grid

import java.io.File

//typealias CharacterGrid = MutableMap<Cell, Char>

class CharacterGrid() {
    val grid: MutableMap<Cell, Char> = mutableMapOf()

    fun fromFile(filename: String): CharacterGrid {
        val rows = File(filename).readLines()
        for ((rowIndex, row) in rows.withIndex()) {
            for ((colIndex, char) in row.withIndex()) {
                this.grid[Cell(rowIndex, colIndex)] = char
            }
        }
        return this
    }

    fun get(cell: Cell) = this.grid.get(cell)

    fun contains(cell: Cell) = this.grid.contains(cell)

    fun getEntries() = this.grid.entries

    fun print() {
        val rows = this.grid.keys.map { it.row }.distinct().sorted()
        val cols = this.grid.keys.map { it.col }.distinct().sorted()
        for (row in rows) {
            for (col in cols) {
                print(this.grid[Cell(row, col)])
            }
            println()
        }
    }
}

typealias IntGrid = Map<Cell, Int>

fun IntGrid.print() {
    val rows = this.keys.map { it.row }.distinct().sorted()
    val cols = this.keys.map { it.col }.distinct().sorted()
    for (row in rows) {
        for (col in cols) {
            print(this[Cell(row, col)])
        }
        println()
    }
}

fun intGridFromFile(filename: String): IntGrid {
    val grid: MutableMap<Cell, Int> = mutableMapOf()
    val rows = File(filename).readLines()
    for ((rowIndex, row) in rows.withIndex()) {
        for ((colIndex, char) in row.withIndex()) {
            grid[Cell(rowIndex, colIndex)] = char.code - 48
        }
    }
    return grid
}
