package nl.jackploeg.aoc._2024.calendar.day19

import nl.jackploeg.aoc.utilities.splitOnFirstEmptyLine
import java.io.File
import javax.inject.Inject

class Day19 @Inject constructor() {

    fun partOne(filename: String): Int {
        val input = File(filename).readLines()
        val (patternsline, requestedDesigns) = input.splitOnFirstEmptyLine()
        val patterns = patternsline[0].split(",")
            .map { it.trim() }
            .filter { requestedDesigns.any { design -> design.contains(it) } }

        return requestedDesigns.filter { design ->
            canMakeDesign(design, patterns)}.count()
    }

    val cache = mutableMapOf<String, Long>()

    fun partTwo(filename: String): Long {
        val input = File(filename).readLines()
        val (patternsline, requestedDesigns) = input.splitOnFirstEmptyLine()
        val patterns = patternsline[0].split(",")
            .map { it.trim() }
            .filter { requestedDesigns.any { design -> design.contains(it) } }

        return requestedDesigns.map { countPossibleCombinations(it, patterns) }.sum()
    }

    private fun canMakeDesign(design: String, patterns: List<String>): Boolean =
        patterns.filter { pattern -> design.startsWith(pattern)}.any { pattern ->
            pattern == design || canMakeDesign(design.substring(pattern.length), patterns)
        }

    private fun countPossibleCombinations(design: String, patterns: List<String>): Long =
        cache.getOrPut(design) {
            patterns.filter { pattern -> design.startsWith(pattern)}.sumOf { pattern ->
                if (pattern == design) 1 else countPossibleCombinations(design.substring(pattern.length), patterns)
            }
        }
}
