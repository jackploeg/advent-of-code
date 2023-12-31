package nl.jackploeg.aoc._2023.calendar.day12

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import nl.jackploeg.aoc.utilities.repeatString
import javax.inject.Inject

class Day12 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun countValidPermutationsInLineFromIndex(
    line: String,
    requiredGroups: List<Int>,
    index: Int = 0,
    numberOfGroupsFound: Int = 0,
    currentGroupSize: Int = 0,
    cache: MutableMap<kotlin.Triple<Int, Int, Int>, Long> = mutableMapOf()
  ): Long {
    val cacheKey = Triple(index, numberOfGroupsFound, currentGroupSize)
    cache[cacheKey]?.let { return it }

    if (index == line.length) {
      // all required groups found and no extra group after it in the pattern
      if (numberOfGroupsFound == requiredGroups.size && currentGroupSize == 0) {
        return 1
      }
      // all required groups found, last group ends at end of pattern
      if (numberOfGroupsFound == requiredGroups.size - 1 && requiredGroups[numberOfGroupsFound] == currentGroupSize) {
        return 1
      }
      // too few or too many groups found
      return 0
    }

    var possibleSolutionCount: Long = 0
    val char = line[index]

    // reached a group or still in a group
    if (char == '#' || char == '?') {
      possibleSolutionCount += countValidPermutationsInLineFromIndex(
        line, requiredGroups, index + 1, numberOfGroupsFound, currentGroupSize + 1, cache
      )
    }

    // not in a group or ending a group
    if ((char == '.' || char == '?')) {
      val notInAGroup = currentGroupSize == 0

      if (notInAGroup) {
        possibleSolutionCount += countValidPermutationsInLineFromIndex(
          line, requiredGroups, index + 1, numberOfGroupsFound, 0, cache
        )
      } else if (
        numberOfGroupsFound < requiredGroups.size &&
        requiredGroups[numberOfGroupsFound] == currentGroupSize
      ) {
        // found a group
        possibleSolutionCount += countValidPermutationsInLineFromIndex(
          line, requiredGroups, index + 1, numberOfGroupsFound + 1, 0, cache
        )
      }
    }

    // add to cache for next time we get here
    cache[cacheKey] = possibleSolutionCount

    return possibleSolutionCount
  }

  fun countValidPermutationsInFile(fileName: String, repeat: Int): Long {
    val lines = readStringFile(fileName)
    val rows: MutableList<String> = mutableListOf()
    val scores: MutableList<List<Int>> = mutableListOf()
    lines.forEach { it ->
      val (group, score) = it.split(" ")
      rows.add(repeatString(group, repeat, '?'))
      scores.add(repeatString(score, repeat, ',').split(',').map { it.toInt() })
    }

    return rows.mapIndexed { index, row -> countValidPermutationsInLineFromIndex(row, scores[index]) }.sum()
  }

}
