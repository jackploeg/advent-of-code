package nl.jackploeg.aoc._2022.calendar.day06

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day06 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(filename: String) =
      findmarker(4, filename)

  fun partTwo(filename: String) =
     findmarker(14, filename)

    fun findmarker(markersize: Int, fileName: String): Int {
        val input = readStringFile(fileName)
        var markerEnd = 0
        for(line in input) {
            val marker: ArrayDeque<Char> = ArrayDeque(markersize)
            val lineChars = line.toCharArray()
            for (i in 1..markersize) {
                marker.add(lineChars[i-1])
            }
            markerEnd = markersize
            while (!allCharsDiffer(marker)) {
                val c = lineChars[markerEnd]
                marker.removeFirst()
                marker.add(c)
                markerEnd++
            }
            //println(marker)
        }
        return markerEnd
    }

    fun allCharsDiffer(chars: ArrayDeque<Char>): Boolean =
        chars.distinct().size == chars.size
}
