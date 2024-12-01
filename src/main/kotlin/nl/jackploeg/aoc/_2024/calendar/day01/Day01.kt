package nl.jackploeg.aoc._2024.calendar.day01

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import kotlin.math.max
import kotlin.math.min

class Day01 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String) : Int {
      val left = mutableListOf<Int>()
      val right = mutableListOf<Int>()
      var total = 0
      generatorFactory.forFile(filename).readAs(::day01) { input ->
        input.forEach {
            left.add(it.first)
            right.add(it.second)
        }
        left.sort()
        right.sort()
        left.forEachIndexed { i, v ->
            total += max(v, right[i])
            total -= min(v, right[i])
        }
      }
      return total
  }

  fun partTwo(filename: String)  : Int {
      val left = mutableListOf<Int>()
      val right = mutableListOf<Int>()
      var total = 0
      generatorFactory.forFile(filename).readAs(::day01) { input ->
          input.forEach {
              left.add(it.first)
              right.add(it.second)
          }
          left.forEach {v ->
              total += v * right.count{ it == v}
          }
      }
      return total
  }

  private fun day01(line: String) : Pair<Int, Int> {
      val (a, b) = line.split("   ")
      return Pair(a.toInt(),b.toInt())
  }
}
