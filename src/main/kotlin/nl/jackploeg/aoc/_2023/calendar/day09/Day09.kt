package nl.jackploeg.aoc._2023.calendar.day09

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject

class Day09 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String): Long {
    val lines = readStringFile(fileName)
    return lines.map { it.split(' ') }.map { strings -> strings.map { it.toLong() } }.sumOf { predictNextNumber(it) }
  }

  fun partTwo(fileName: String): Long {
    val lines = readStringFile(fileName)
    return lines.map { it.split(' ') }.map { number -> number.map { it.toLong() } }.sumOf { predictNextNumber(it.reversed()) }
  }

  fun predictNextNumber(sequence: List<Long>): Long {
    val sequences: MutableList<List<Long>> = mutableListOf()
    sequences.add(sequence)
    while (!sequences.last().all { it == 0L }) {
      sequences.add(sequences.last().zipWithNext().map { p -> p.second - p.first })
    }
    sequences.reverse()
    var newValue = 0L
    sequences.forEach { newValue += it.last() }
    return newValue
  }

}
