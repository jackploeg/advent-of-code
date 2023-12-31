package nl.jackploeg.aoc._2023.calendar.day13

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject
import kotlin.math.min

typealias Pattern = MutableList<MutableList<Char>>

class Day13 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  // find reflections
  fun partOne(fileName: String): Int {
    val patterns: MutableList<MutableList<String>> = mutableListOf()
    var pattern: MutableList<String> = mutableListOf()
    readStringFile(fileName).forEach { line ->
      if (line == "") {
        patterns.add(pattern)
        pattern = mutableListOf()
      } else {
        pattern.add(line)
      }
    }
    patterns.add(pattern)

    return patterns.sumOf { findReflection(it) }
  }

  // find reflections after clearing smudges
  fun partTwo(fileName: String): Int {
    val patterns: MutableList<MutableList<String>> = mutableListOf()
    var pattern: MutableList<String> = mutableListOf()
    readStringFile(fileName).forEach { line ->
      if (line == "") {
        patterns.add(pattern)
        pattern = mutableListOf()
      } else {
        pattern.add(line)
      }
    }
    patterns.add(pattern)

    return patterns.sumOf { findSmudgeClearedSolution(it) }
  }

  fun findSmudgeClearedSolution(pattern: MutableList<String>): Int {
    val oldScore = findReflection(pattern)
    for (i in 0 until pattern.size) {
      val lineToClear = pattern[i]
      for (j in 0 until pattern[i].length) {
        val newline =
          lineToClear.substring(0, j) + (if (lineToClear[j] == '.') '#' else '.') + lineToClear.substring(j + 1)
        val newPattern = pattern.subList(0, i).plus(newline).plus(pattern.subList(i + 1, pattern.size))
        val score = findReflection(newPattern, oldScore)
        if (score != 0 && score != oldScore) return score
      }
    }
    return 0
  }

  fun findReflection(pattern: List<String>, scoreToSkip: Int = 0): Int {
    // try to find reflection row
    for (row in 1 until pattern.size) {
      val rowsToCompare = min(row, pattern.size - row)
      if (pattern.subList(row - rowsToCompare, row).reversed() == pattern.subList(row, row + rowsToCompare)) {
        // found mirror row
        if (row * 100 != scoreToSkip) return row * 100
      }
    }

    // no reflection row, try to find reflection column
    val patternWidth = pattern[0].length
    for (column in 1 until patternWidth) {
      val columnsToCompare = min(column, patternWidth - column)
      val columnsUptoX =
        (column - columnsToCompare until column).map { col -> pattern.map { it[col] }.joinToString("") }
      val columnsFromtoX =
        (column until column + columnsToCompare).map { col -> pattern.map { it[col] }.joinToString("") }
      if (columnsUptoX.reversed() == columnsFromtoX) {
        // found mirror column
        if (column != scoreToSkip) return column
      }
    }
    return 0
  }
}
