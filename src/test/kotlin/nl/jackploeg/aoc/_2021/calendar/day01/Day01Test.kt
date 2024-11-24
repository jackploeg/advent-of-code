package nl.jackploeg.aoc._2021.calendar.day01

import javax.inject.Inject

import nl.jackploeg.aoc._2021.DaggerTestDayComponent
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
    assertEquals(7, day01.partOne(DAY_01_TEST))
  }

  @Test
  fun testDay01PartTwoTestInput() {
    assertEquals(5, day01.partTwo(DAY_01_TEST))
  }

  @Test
  fun testDay01PartOne() {
    assertEquals(1766, day01.partOne(DAY_01))
  }

  @Test
  fun testDay01PartTwo() {
    assertEquals(1797, day01.partTwo(DAY_01))
  }

  companion object {
    private const val DAY_01: String = "advent-of-code-input/2021/day01.input"

    private const val DAY_01_TEST: String = "advent-of-code-input/2021/day01.test"
  }
}
