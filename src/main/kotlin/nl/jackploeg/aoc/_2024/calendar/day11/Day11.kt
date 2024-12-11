package nl.jackploeg.aoc._2024.calendar.day11

import java.io.File
import javax.inject.Inject

class Day11 @Inject constructor()  {

    fun partOne(filename: String): Int {
        val stones = File(filename).readLines()[0].split(" ").map { it.toLong() }
        return stones.sumOf { countStones(it, 25) }.toInt()
    }

    fun partTwo(filename: String): Long {
        val stones = File(filename).readLines()[0].split(" ").map { it.toLong() }
        return stones.sumOf { countStones(it, 75) }
    }

    fun String.splitInTwo(): List<Long> =
        listOf(take(length / 2).toLong(), drop(length / 2).toLong())

    private fun blink(stone: Long): List<Long> =
        when {
        stone == 0L -> listOf(1L)
        stone.toString().length % 2 == 0 -> stone.toString().splitInTwo()
        else -> listOf(stone * 2024)
    }

    // cache for already evaluated stone/blink combinations
    val cache = mutableMapOf<Pair<Long, Int>, Long>()

    fun countStones(stone: Long, blinksToGo: Int): Long =
        cache.getOrPut(stone to blinksToGo) {
            if (blinksToGo == 0) 1 else blink(stone).sumOf { countStones(it, blinksToGo - 1) }
    }


}
