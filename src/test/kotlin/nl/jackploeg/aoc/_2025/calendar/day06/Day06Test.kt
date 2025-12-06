package nl.jackploeg.aoc._2025.calendar.day06

import javax.inject.Inject

import nl.jackploeg.aoc._2025.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day06Test {
    @Inject
    lateinit var day06: Day06

    @BeforeEach
    fun setup() {
        DaggerTestDayComponent.create().inject(this)
    }

    @Test
    fun testDay06PartOneTestInput() {
        assertEquals(4277556, day06.partOne(DAY_06_TEST))
    }

    @Test
    fun testDay06PartTwoTestInput() {
        assertEquals(3263827, day06.partTwo(DAY_06_TEST))
    }

    @Test
    fun testDay06PartOne() {
        assertEquals(7644505810277, day06.partOne(DAY_06))
    }

    @Test
    fun testDay06PartTwo() {
        assertEquals(12841228084455, day06.partTwo(DAY_06))
    }

    companion object {
        private const val DAY_06: String = "advent-of-code-input/2025/day06.input"

        private const val DAY_06_TEST: String = "advent-of-code-input/2025/day06.test"
    }
}
