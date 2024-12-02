package nl.jackploeg.aoc._2024.calendar.day02

import javax.inject.Inject
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import java.lang.Math.abs

class Day02 @Inject constructor(
    private val generatorFactory: InputGeneratorFactory,
) {
    fun partOne(filename: String): Int =
        generatorFactory.forFile(filename).readAs(::day02) { input ->
            return@readAs input.count { it.isSafe() }
        }


    fun partTwo(filename: String) : Int =
        generatorFactory.forFile(filename).readAs(::day02) { input ->
            return@readAs input.count { it.isSafeWithOneError() }
        }

    private fun day02(line: String): List<Int> {
        return line.split(" ").map {
            it.toInt()
        }
    }

    fun List<Int>.isSafe(): Boolean {
        val increasing = this.count{ it >  this.get(0)} > 2
        (0..this.size - 2).forEach {
            if (increasing && this.get(it + 1) <= this.get(it)) {
                return false
            }
            if (!increasing && this.get(it + 1) >= this.get(it)) {
                return false
            }
            if (abs(this.get(it + 1) - this.get(it)) > 3) {
                return false
            }
        }
        return true
    }

    fun List<Int>.isSafeWithOneError(): Boolean {
        if (this.isSafe() || this.subList(1,this.size).isSafe() || this.subList(0, this.size-1).isSafe()) {
            return true
        }
        (1..this.size -2).forEach() { i ->
            if (this.subList(0, i).plus(this.subList(i+1, this.size)).isSafe()) {
                return true
            }
        }
        return false
    }
}
