package nl.jackploeg.aoc._2023.calendar.day14

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject

class Day14 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  //calc Load After Tilt North
  fun partOne(fileName: String): Long {
    val lines = readStringFile(fileName)
    val rockLayout: Array<Array<Char>> = lines.map { it.toCharArray().toTypedArray() }.toTypedArray()
    val lineSize = lines[0].length
    // move O's north
    tiltNorth(lines, lineSize, rockLayout)
    val load = calcScore(lines, lineSize, rockLayout)
    return load
  }

  // calc Load After Many Spins With Cache
  fun partTwo(fileName: String, spins: Int): Long {
    val lines = readStringFile(fileName)
    val rockLayout: Array<Array<Char>> = lines.map { it.toCharArray().toTypedArray() }.toTypedArray()
    val lineSize = lines[0].length
    val scores: MutableList<Long> = mutableListOf()
    var spin = 1
    val cache = mutableListOf<String>()
    var skipped = false
    while (spin <= spins) {
      tiltNorth(lines, lineSize, rockLayout)
      tiltWest(lines, lineSize, rockLayout)
      tiltSouth(lines, lineSize, rockLayout)
      tiltEast(lines, lineSize, rockLayout)
      scores.add(calcScore(lines, lineSize, rockLayout))
      val zip = zip(rockLayout)
      if (!skipped && cache.contains(zip)) {
        val cachePos = cache.indexOf(zip) + 1
        val cycleLength = spin - cachePos
        spin += ((((spins - spin) / cycleLength) * cycleLength))
        skipped = true
      }
      cache.add(zip(rockLayout))
      spin++
    }
    val load = calcScore(lines, lineSize, rockLayout)
    return load
  }

  fun zip(rockLayout: Array<Array<Char>>): String =
    rockLayout.joinToString("") { it.joinToString("") }


  private fun calcScore(lines: List<String>, lineSize: Int, rockLayout: Array<Array<Char>>): Long {
    var load = 0L
    val numLines = lines.size
    (0 until numLines).forEach { row ->
      (0 until lineSize).forEach {
        if (rockLayout[row][it] == 'O') {
          load += numLines - row
        }
      }
    }
    return load
  }

  private fun tiltNorth(lines: List<String>, lineSize: Int, rockLayout: Array<Array<Char>>) {
    (1 until lines.size).forEach { row ->
      (0 until lineSize).forEach {
        var curRow = row
        while (curRow > 0 && rockLayout[curRow][it] == 'O' && rockLayout[curRow - 1][it] == '.') {
          rockLayout[curRow - 1][it] = 'O'
          rockLayout[curRow][it] = '.'
          curRow--
        }
      }
    }
  }

  private fun tiltSouth(lines: List<String>, lineSize: Int, rockLayout: Array<Array<Char>>) {
    (lines.size - 2 downTo 0).forEach { row ->
      (0 until lineSize).forEach {
        var curRow = row
        while (curRow < lines.size - 1 && rockLayout[curRow][it] == 'O' && rockLayout[curRow + 1][it] == '.') {
          rockLayout[curRow + 1][it] = 'O'
          rockLayout[curRow][it] = '.'
          curRow++
        }
      }
    }
  }

  private fun tiltEast(lines: List<String>, lineSize: Int, rockLayout: Array<Array<Char>>) {
    lines.indices.forEach { row ->
      (lineSize - 1 downTo 0).forEach { pos ->
        var curPos = pos
        while (curPos < lineSize - 1 && rockLayout[row][curPos] == 'O' && rockLayout[row][curPos + 1] == '.') {
          rockLayout[row][curPos + 1] = 'O'
          rockLayout[row][curPos] = '.'
          curPos++
        }
      }
    }
  }

  private fun tiltWest(lines: List<String>, lineSize: Int, rockLayout: Array<Array<Char>>) {
    lines.indices.forEach { row ->
      (0 until lineSize).forEach { pos ->
        var curPos = pos
        while (curPos > 0 && rockLayout[row][curPos] == 'O' && rockLayout[row][curPos - 1] == '.') {
          rockLayout[row][curPos - 1] = 'O'
          rockLayout[row][curPos] = '.'
          curPos--
        }
      }
    }
  }


  fun printArray(array: Array<Array<Char>>) {
    array.forEach { it ->
      it.forEach { print(it) }
      println()
    }

  }
}
