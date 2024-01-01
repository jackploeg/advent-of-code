package nl.jackploeg.aoc._2022.calendar.day07

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day07Test {
  @Inject
  lateinit var day07: Day07

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay07PartOneTestInput() {
    assertEquals(95437, day07.partOne(DAY_07_TEST))
  }

  @Test
  fun testDay07PartTwoTestInput() {
    assertEquals(24933642, day07.partTwo(DAY_07_TEST))
  }

  @Test
  fun testDay07PartOne() {
    assertEquals(1297159, day07.partOne(DAY_07))
  }

  @Test
  fun testDay07PartTwo() {
    assertEquals(3866390, day07.partTwo(DAY_07))
  }

  companion object {
    private const val DAY_07: String = "advent-of-code-input/2022/day07.input"

    private const val DAY_07_TEST: String = "advent-of-code-input/2022/day07.test"
  }
}
