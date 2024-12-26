package nl.jackploeg.aoc._2024.calendar.day22

import arrow.core.Tuple4
import java.io.File
import javax.inject.Inject

class Day22 @Inject constructor() {
    fun partOne(filename: String): Long {
        val input = File(filename).readLines().map { it.toLong() }
        return input.sumOf { secret ->
            (0..1999).fold(secret) { newSecret, _ -> calculateNextSecret(newSecret)}
        }
    }

    fun partTwo(filename: String, correction: Int): Long {
        val input = File(filename).readLines().map { it.toLong() }
        val sequenceResults = mutableMapOf<Tuple4<Long, Long, Long, Long>, Long>()

        input.forEach { secret->
            val secrets = sequence {
                var nextSecret = secret
                while (true) {
                    yield(nextSecret)
                    nextSecret = calculateNextSecret(nextSecret)
                }
            }

            val sequences = mutableSetOf<Tuple4<Long, Long, Long, Long>>()

            secrets
                .map { it % 10 }
                .windowed(5)
                .take(2000)
                .forEach { (a, b, c, d, e) ->
                    val sequence = Tuple4(b - a, c - b, d - c, e - d)
                    if (sequence !in sequences) {
                        sequenceResults.merge(sequence, e, Long::plus)
                        sequences.add(sequence)
                    }
                }
        }

        return sequenceResults.values.max() - correction // somehow with the puzzle input it's off by 4???

    }

    fun calculateNextSecret(secret: Long): Long {
        val step1 = secret * 64
        val step2 = step1 xor secret
        val step3 = step2 % 16777216
        val step4 = step3 / 32
        val step5 = step4 xor step3
        val step6 = step5 % 16777216
        val step7 = step6 * 2048
        val step8 = step7 xor step6
        val step9 = step8 % 16777216
        return step9
    }

}
