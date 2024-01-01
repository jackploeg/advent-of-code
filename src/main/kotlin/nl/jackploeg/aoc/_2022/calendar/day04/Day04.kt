package nl.jackploeg.aoc._2022.calendar.day04

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile

class Day04 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
    // get number of containments
    fun partOne(fileName: String): Int {
        val assignmentPairs: List<Pair<IntRange, IntRange>> = readStringFile(fileName)
            .map { toRangePair(it) }
        return assignmentPairs.count { rangesContained(it) }
    }

    // get number of overlaps
    fun partTwo(fileName: String): Int {
        val assignmentPairs: List<Pair<IntRange, IntRange>> = readStringFile(fileName)
            .map { toRangePair(it) }
        return assignmentPairs.count { rangesOverlap(it) }
    }

    fun toRangePair(input: String): Pair<IntRange, IntRange> {
        val ranges = input.split(",")
        val (start1, end1) = ranges[0].split("-")
        val range1 = start1.toInt().rangeTo(end1.toInt())
        val (start2, end2) = ranges[1].split("-")
        val range2 = start2.toInt().rangeTo(end2.toInt())
        return Pair(range1, range2)
    }

    fun rangesContained(ranges: Pair<IntRange, IntRange>): Boolean {
        return (ranges.first.contains(ranges.second.first) && ranges.first.contains(ranges.second.last))
                || (ranges.second.contains(ranges.first.first) && ranges.second.contains(ranges.first.last))
    }

    fun rangesOverlap(ranges: Pair<IntRange, IntRange>): Boolean {
        return ranges.first.contains(ranges.second.first)
                || ranges.first.contains(ranges.second.last)
                || ranges.second.contains(ranges.first.first)
                || ranges.second.contains(ranges.first.last)
    }}
