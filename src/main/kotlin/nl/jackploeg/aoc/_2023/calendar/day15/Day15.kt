package nl.jackploeg.aoc._2023.calendar.day15

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject
import kotlin.math.max

class Day15 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  // sum of hashes
  fun partOne(filename: String) = generatorFactory.forFile(filename).readOne { line ->
    line.split(",").map(::hash).sum()
  }

  // sum of focal lengths
  fun partTwo(fileName: String): Int
  {
    val instructions = readStringFile(fileName).first().split(',')
    val boxes: List<MutableList<Lens>> = initBoxes()
    instructions.forEach { instruction ->
      val indexOfEq = instruction.indexOf('=')
      val indexOfMinus = instruction.indexOf('-')
      val separatorPos = max(indexOfMinus, indexOfEq)
      val lensName = instruction.substring(0, separatorPos)
      val box = hash(lensName)
      if (instruction[separatorPos] == '-')
      {
        val lens = boxes[box].firstOrNull { it.name == lensName }
        lens?.let { boxes[box].remove(it) }
      }
      if (instruction[separatorPos] == '=')
      {
        val focalLength = instruction.substring(separatorPos + 1, instruction.length).toInt()
        val lens = boxes[box].firstOrNull { it.name == lensName }
        if (lens != null)
        {
          lens.focalLength = focalLength
        } else
        {
          boxes[box].add(Lens(lensName, focalLength))
        }
      }
    }
    return boxes.mapIndexed { boxIndex, lenses ->
      lenses.mapIndexed { lensindex, lens ->
        ((boxIndex + 1) * (lensindex + 1) * lens.focalLength)
      }.sum()
    }.sum()
  }

  fun initBoxes(): List<MutableList<Lens>> =
    List(size = 256) { mutableListOf() }

  private fun hash(text: String) = text
    .map { it.code }
    .fold(0) { acc, next -> ((acc + next) * 17) % 256 }

  data class Lens(val name: String, var focalLength: Int)

}
