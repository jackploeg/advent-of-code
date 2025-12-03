package nl.jackploeg.aoc._2015.calendar.day01

import javax.inject.Inject

import nl.jackploeg.aoc._2015.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day01Test {
  @Inject
  lateinit var day01: Day01

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay01PartOneTestInput() {
    assertEquals(3, day01.partOne(DAY_01_TEST))
  }

  @Test
  fun testDay01PartTwoTestInput() {
    assertEquals(9, day01.partTwo(DAY_01_TEST))
  }

  @Test
  fun testDay01PartOne() {
    assertEquals(74, day01.partOne(DAY_01))
  }

  @Test
  fun testDay01PartTwo() {
    assertEquals(1795, day01.partTwo(DAY_01))
  }

  companion object {
    private const val DAY_01: String = "advent-of-code-input/2015/day01.input"

    private const val DAY_01_TEST: String = "advent-of-code-input/2015/day01.test"
  }
}
