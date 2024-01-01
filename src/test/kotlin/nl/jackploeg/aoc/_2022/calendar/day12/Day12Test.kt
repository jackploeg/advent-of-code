package nl.jackploeg.aoc._2022.calendar.day12

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day12Test {
    @Inject
    lateinit var day12: Day12

    @BeforeEach
    fun setup() {
        DaggerTestDayComponent.create().inject(this)
    }

    @Test
    fun testDay12PartOneTestInput() {
        assertEquals(31, day12.partOne(DAY_12_TEST))
    }

    @Test
    fun testDay12PartTwoTestInput() {
        assertEquals(29, day12.partTwo(DAY_12_TEST))
    }

    @Test
    fun testDay12PartOne() {
        assertEquals(468, day12.partOne(DAY_12))
    }

    @Test
    fun testDay12PartTwo() {
        assertEquals(459, day12.partTwo(DAY_12))
    }

    companion object {
        private const val DAY_12: String = "advent-of-code-input/2022/day12.input"

        private const val DAY_12_TEST: String = "advent-of-code-input/2022/day12.test"
    }
}
