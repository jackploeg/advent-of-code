package nl.jackploeg.aoc._2025.calendar.day05

import javax.inject.Inject
import kotlin.Int
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import kotlin.math.max

class Day05 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int {
        val freshRanges: MutableList<ClosedRange<Long>> = mutableListOf()
        val ingredients: MutableList<Long> = mutableListOf()
        generatorFactory.forFile(filename).readAs(::day05) { input ->
            input.forEach { a ->
                if (a.first != null) {
                    freshRanges.add(a.first!!)
                }
                if (a.second != null) {
                    ingredients.add(a.second!!)
                }
            }
        }
        return ingredients.count { ingredient -> freshRanges.any { range -> range.contains(ingredient) } }
    }

    fun partTwo(filename: String): Long {
        val freshRanges: MutableList<ClosedRange<Long>> = mutableListOf()
        val ingredients: MutableList<Long> = mutableListOf()
        generatorFactory.forFile(filename).readAs(::day05) { input ->
            input.forEach { a ->
                if (a.first != null) {
                    freshRanges.add(a.first!!)
                }
                if (a.second != null) {
                    ingredients.add(a.second!!)
                }
            }
        }
        freshRanges.sortBy { it.start }
        var count = 0L
        var largest = 0L
        freshRanges.forEach { range ->
            if (range.start > largest) {
                count += range.endInclusive - range.start + 1
            }
            if (range.start <= largest && range.endInclusive > largest) {
                count += range.endInclusive - largest
            }
            largest = max(largest, range.endInclusive)
        }
        return count
    }

    private fun day05(line: String): Pair<ClosedRange<Long>?, Long?> {
        if (line.isEmpty()) return Pair(null, null)
        if (line.indexOf('-') > -1) {
            val (start, end) = line.split("-")
            val range = start.toLong()..end.toLong()
            return Pair(range, null)
        } else {
            return Pair(null, line.toLong())
        }
    }
}
