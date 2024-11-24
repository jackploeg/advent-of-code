package nl.jackploeg.aoc._2021.calendar.day01

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory

class Day01 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String) =
      generatorFactory.forFile(filename).readAs(::day01) { input ->
         countDepthIncrements(input)
      }

    fun partTwo(filename: String) =
      generatorFactory.forFile(filename).readAs(::day01) { input ->
          countSlidingWindowIncrements(input)
  }

    private fun countDepthIncrements(input: Sequence<Measurement>): Int =
        input.runningReduce { acc, measurement ->  Measurement(measurement.depth, acc.depth) }
            .filter { it.previous != null }
            .filter { it.depth > it.previous!! }
            .count()

    private fun countSlidingWindowIncrements(input: Sequence<Measurement>): Int {
        val recentMeasurements: MutableList<Measurement> = mutableListOf()
        val slidingWindows: MutableList<SlidingWindow> = mutableListOf()
        input.forEach { measurement ->
            recentMeasurements.add(measurement)
            if (recentMeasurements.size > 2) {
                slidingWindows.add(SlidingWindow(recentMeasurements.drop(recentMeasurements.size - 3)))
            }
        }
        slidingWindows.forEach{
            println(it)
        }
        val w2 = slidingWindows.runningReduce { acc, window ->  SlidingWindow(window.measurements, window.total, acc.total) }
        w2.forEach{
            println(it)
        }

        return slidingWindows.runningReduce { acc, window ->  SlidingWindow(window.measurements, window.total, acc.total) }
            .filter { it.depth != null && it.previous != null }
            .filter { it.depth!! > it.previous!! }
            .count()
    }

    private fun day01(line: String): Measurement {
      return Measurement(line.toInt())
  }

  data class Measurement( val depth: Int, val previous: Int? = null)

  data class SlidingWindow(val measurements: List<Measurement>?, val depth: Int? = null, val previous: Int? = null) {
      val total: Int?
          get() = measurements?.sumOf { it.depth }
  }
}
