package nl.jackploeg.aoc._2022.calendar.day08

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day08 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
    // count visible trees
    fun partOne(fileName: String): Int {
        val input = readStringFile(fileName)
        val grid = initTreeGrid(input)
        return countVisibleTrees(grid.first)
    }

    // get max scenic score
    fun partTwo(fileName: String): Int {
        val input = readStringFile(fileName)
        val grid = initTreeGrid(input)
        return grid.first.flatMap { it.trees }.maxOfOrNull { it.scenicScore() }!!
    }

    fun countVisibleTrees(rows: ArrayList<Row>): Int {
        return rows.flatMap { it.trees }.filter { it.isVisible() }.count()
    }

    fun initTreeGrid(lines: List<String>): Pair<ArrayList<Row>, ArrayList<Column>> {
        var i = 0
        val rows: ArrayList<Row> = ArrayList()
        val columns: ArrayList<Column> = ArrayList()

        do {
            val trees = lines[i].toCharArray()
            if (i <= rows.size) {
                rows.add(Row(i))
            }
            val row = rows[i]

            var j = 0
            do {
                if (j <= columns.size) {
                    columns.add(Column(j))
                }
                val column = columns[j]

                val tree = Tree(row, i, column, j, lines[i][j].digitToInt())
                row.trees.add(tree)
                column.trees.add(tree)
                j++
            } while (j < trees.size)
            i++
        } while (i < lines.size)

        return Pair(rows, columns)
    }

    class Row(val number: Int, val trees: ArrayList<Tree> = ArrayList())
    class Column(val number: Int, val trees: ArrayList<Tree> = ArrayList())

    class Tree(private val row: Row, private val rowIndex: Int, private val column: Column, private val columnIndex: Int, private val height: Int) {
        fun isVisible(): Boolean {
            return rowIndex == 0
                    || rowIndex == row.trees.size - 1
                    || columnIndex == 0
                    || columnIndex == column.trees.size - 1
                    || allSmaller(row.trees, columnIndex)
                    || allSmaller(column.trees, rowIndex)
        }

        fun scenicScore(): Int {
            return scenicScore(row.trees, columnIndex) * scenicScore(column.trees, rowIndex)
        }

        private fun allSmaller(collection: ArrayList<Tree>, index: Int): Boolean {
            val before = collection.subList(0, index)
            val after = collection.subList(index + 1, collection.size)
            return before.map { it.height }.filter { it >= this.height }.isEmpty()
                    || after.map { it.height }.filter { it >= this.height }.isEmpty()
        }

        private fun scenicScore(collection: ArrayList<Tree>, index: Int): Int {
            val before = collection.subList(0, index)

            var score1 = 0
            if (index > 0) {
                for (i in index - 1 downTo 0) {
                    score1++
                    if (before[i].height >= height)
                        break
                }
            }

            var score2 = 0
            if (index < collection.size) {
                for (i in index + 1 until collection.size) {
                    score2++
                    if (collection[i].height >= height)
                        break
                }
            }
            return score1 * score2
        }

    }
}
