package nl.jackploeg.aoc._2024.calendar.day5

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day5Test {
  @Inject
  lateinit var day5: Day5

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay5PartOneTestInput() {
    assertEquals(143, day5.partOne(DAY_5_TEST))
  }

  @Test
  fun testDay5PartTwoTestInput() {
    assertEquals(123, day5.partTwo(DAY_5_TEST))
  }

  @Test
  fun testDay5PartOne() {
    assertEquals(4959, day5.partOne(DAY_5))
  }

  @Test
  fun testDay5PartTwo() {
    assertEquals(4655, day5.partTwo(DAY_5))
  }

  companion object {
    private const val DAY_5: String = "advent-of-code-input/2024/day5.input"

    private const val DAY_5_TEST: String = "advent-of-code-input/2024/day5.test"
  }
}
