package nl.jackploeg.aoc._2022.calendar.day01

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day01 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
    // get max load
    fun partOne(fileName: String): Int {
        return readElfLoads(fileName).first()
    }

    // get top 3 max load
    fun partTwo(fileName: String): Int {
        return readElfLoads(fileName).take(3).reduce { sum, load -> sum + load }
    }

    fun readElfLoads(fileName: String): List<Int> {
        val loadSums = mutableListOf<Int>()
        val loads = readStringFile(fileName)

        var currentLoad = 0
        for (load in loads) {
            if (load == "") {
                loadSums.add(loadSums.size, currentLoad)
                currentLoad = 0
            } else {
                currentLoad += Integer.valueOf(load)
            }
        }
        loadSums.add(loadSums.size, currentLoad)
        return loadSums.sortedDescending()
    }
}
