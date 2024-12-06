package nl.jackploeg.aoc._2024.calendar.day05

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day05Test {
  @Inject
  lateinit var day05: Day05

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay05PartOneTestInput() {
    assertEquals(143, day05.partOne(DAY_5_TEST))
  }

  @Test
  fun testDay05PartTwoTestInput() {
    assertEquals(123, day05.partTwo(DAY_5_TEST))
  }

  @Test
  fun testDay05PartOne() {
    assertEquals(4959, day05.partOne(DAY_5))
  }

  @Test
  fun testDay05PartTwo() {
    assertEquals(4655, day05.partTwo(DAY_5))
  }

  companion object {
    private const val DAY_5: String = "advent-of-code-input/2024/day05.input"

    private const val DAY_5_TEST: String = "advent-of-code-input/2024/day05.test"
  }
}
