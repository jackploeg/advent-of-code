package nl.jackploeg.aoc._2015.calendar.day01

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import javax.inject.Inject

class Day01 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String): Int =
      generatorFactory.forFile(filename).readOne { input ->
          var floor = 0
          input.forEach {
              when (it) {
                  '(' -> floor++
                  ')' -> floor--
              }
          }
          floor
  }

  fun partTwo(filename: String): Int =
      generatorFactory.forFile(filename).readOne { input ->
          var floor = 0
          var steps = 0
          input.forEach {
              steps++
              when (it) {
                  '(' -> floor++
                  ')' -> floor--
              }
              println(floor)
              if(floor==-1) {
                  return@readOne steps
              }
          }
          steps
  }

  private fun day01(line: String): CharArray = line.toCharArray()
}
