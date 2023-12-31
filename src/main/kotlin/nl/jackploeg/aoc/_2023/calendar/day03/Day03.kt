package nl.jackploeg.aoc._2023.calendar.day03

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject

class Day03 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String): Int {
    val partNumbers: MutableList<PartNumber> = mutableListOf()
    readSchematic(fileName, partNumbers)
    return partNumbers.filter { it.hasSymbolNeighbour() }.sumOf { it.value }
  }

  fun partTwo(fileName: String): Int {
    val symbols: MutableList<Symbol> = mutableListOf()
    readSchematic(fileName, symbols = symbols)
    return symbols
      .filter { it.char == '*' && it.neighbours.size==2 }
      .sumOf { it.neighbours[0].value * it.neighbours[1].value}
  }

  fun readSchematic(
    fileName: String,
    partNumbers: MutableList<PartNumber> = mutableListOf(),
    symbols: MutableList<Symbol> = mutableListOf()
  ) {
    val engineSchema = readStringFile(fileName)

    for (lineNo in engineSchema.indices) {
      val line = engineSchema[lineNo]
      var lastDigitProcessed = -1
      for (pos in line.indices) {
        if (line[pos].isDigit() && pos > lastDigitProcessed) {
          lastDigitProcessed = pos
          while (lastDigitProcessed < line.length && line[lastDigitProcessed].isDigit())
            lastDigitProcessed++
          val value = line.substring(pos, lastDigitProcessed).toInt()
          partNumbers.add(PartNumber(lineNo, pos, value))
        }
      }
    }

    for (lineNo in engineSchema.indices) {
      val line = engineSchema[lineNo]
      for (pos in line.indices) {
        if (!line[pos].isDigit() && line[pos] != '.') {
          val symbol = Symbol(lineNo, pos, line[pos])
          if (lineNo > 0) {
            partNumbers.filter { it.top == lineNo - 1 && it.left <= pos + 1 && it.right >= pos - 1 }
              .forEach {
                it.bottomChars.add(line[pos])
                symbol.neighbours.add(it)
              }
          }
          if (lineNo < engineSchema.size - 1) {
            partNumbers.filter { it.top == lineNo + 1 && it.left <= pos + 1 && it.right >= pos - 1 }
              .forEach {
                it.topChars.add(line[pos])
                symbol.neighbours.add(it)
              }
          }
          partNumbers.filter { it.top == lineNo && it.left == pos + 1 }
            .forEach {
              it.leftChars.add(line[pos])
              symbol.neighbours.add(it)
            }
          partNumbers.filter { it.top == lineNo && it.right == pos - 1 }
            .forEach {
              it.rightChars.add(line[pos])
              symbol.neighbours.add(it)
            }
          symbols.add(symbol)
        }
      }
    }
  }

  data class PartNumber(
    val top: Int,
    val left: Int,
    val value: Int,
    val topChars: MutableList<Char> = mutableListOf(),
    val bottomChars: MutableList<Char> = mutableListOf(),
    val leftChars: MutableList<Char> = mutableListOf(),
    val rightChars: MutableList<Char> = mutableListOf()
  ) {
    val right: Int
      get() = left + value.toString().length - 1

    fun hasSymbolNeighbour(): Boolean =
      topChars.size > 0 || bottomChars.size > 0 || leftChars.size > 0 || rightChars.size > 0

  }

  data class Symbol(
    val top: Int,
    val left: Int,
    val char: Char,
    val neighbours: MutableList<PartNumber> = mutableListOf()
  )
}
