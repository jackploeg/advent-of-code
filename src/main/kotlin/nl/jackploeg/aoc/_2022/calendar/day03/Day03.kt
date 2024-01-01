package nl.jackploeg.aoc._2022.calendar.day03

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day03 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
    // get sum of priorities
    fun partOne(fileName: String): Int {
        val rucksacks = readStringFile(fileName)
        return rucksacks.map { determineRucksackPriority(it) }
            .reduce { sum, priority -> sum + priority}
    }

    // get sum of badge priorities
    fun partTwo(fileName: String): Int {
        val rucksacks = readStringFile(fileName)
        val elveGroups = rucksacks.chunked(3)
        return elveGroups.map { determineElveGroupPriority(it) }
            .reduce { sum, priority -> sum + priority}
    }
    fun determineRucksackPriority(rucksack: String): Int {
        val contentLength = rucksack.length / 2
        val compartment1Content = rucksack.substring(0, contentLength)
        val compartment2Content = rucksack.substring(contentLength, rucksack.length)
        val commonItems = compartment1Content.toCharArray()
            .filter { compartment2Content.toCharArray().contains(it) }
            .distinct()

        return getContentPriority(commonItems[0])
    }

    fun determineElveGroupPriority(group: List<String>): Int {
        val commonItems = group[0].toCharArray()
            .filter { group[1].toCharArray().contains(it) }
            .filter { group[2].toCharArray().contains(it) }
            .distinct()

        return getContentPriority(commonItems[0])
    }

    fun getContentPriority(content: Char): Int {
        return if (content in 'a'..'z') {
            content.code - 96
        } else {
            content.code - 64 + 26
        }
    }}
