package nl.jackploeg.aoc._2022.calendar.day16

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day16Test {
    @Inject
    lateinit var day16: Day16

    @BeforeEach
    fun setup() {
        DaggerTestDayComponent.create().inject(this)
    }

    @Test
    fun testDay16PartOneTestInput() {
        assertEquals(1651, day16.partOne(DAY_16_TEST))
    }

    @Test
    fun testDay16PartTwoTestInput() {
        assertEquals(1707, day16.partTwo(DAY_16_TEST))
    }

    @Test
    fun testDay16PartOne() {
        assertEquals(1991, day16.partOne(DAY_16))
    }

    @Test
    fun testDay16PartTwo() {
        assertEquals(2705, day16.partTwo(DAY_16))
    }

    companion object {
        private const val DAY_16: String = "advent-of-code-input/2022/day16.input"

        private const val DAY_16_TEST: String = "advent-of-code-input/2022/day16.test"
    }
}
