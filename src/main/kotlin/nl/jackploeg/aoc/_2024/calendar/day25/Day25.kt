package nl.jackploeg.aoc._2024.calendar.day25

import nl.jackploeg.aoc.utilities.splitOnEmptyLines
import java.io.File
import javax.inject.Inject

class Day25 @Inject constructor() {
    fun partOne(filename: String): Int {
        val input = File(filename).readLines().splitOnEmptyLines()
        val locks = mutableListOf<Lock>()
        val keys = mutableListOf<Key>()
        input.forEach { listOfLines ->
            if (listOfLines.get(0).contains('#')) {
                locks.add(Lock.fromLines(listOfLines))
            } else {
                keys.add(Key.fromLines(listOfLines))
            }
        }

        val validCombinations: MutableList<Combination> = mutableListOf()

        keys.forEach { key ->
            locks.forEach { lock ->
                if ((0..4).all { index ->
                        (lock.heights.get(index)!! + key.heights.get(index)!!) < 6
                    }) {
                    validCombinations.add(Combination(key, lock))
                }
            }
        }

        return validCombinations.size
    }

    data class Lock(val heights: Map<Int, Int>) {
        companion object {
            fun fromLines(lines: List<String>): Lock {
                val heights = (0..4).map { index ->
                    index to lines.count { it.get(index) == '#' } - 1
                }.toMap()
                return Lock(heights)
            }
        }
    }

    data class Key(val heights: Map<Int, Int>) {
        companion object {
            fun fromLines(lines: List<String>): Key {
                val heights = (0..4).map { index ->
                    index to lines.count { it.get(index) == '#' } - 1
                }.toMap()
                return Key(heights)
            }
        }
    }

    data class Combination(val key: Key, val lock: Lock)
}
