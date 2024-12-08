package nl.jackploeg.aoc._2024.calendar.day08

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day08Test {
  @Inject
  lateinit var day08: Day08

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay08PartOneTestInput() {
    assertEquals(14, day08.partOne(DAY_08_TEST))
  }

  @Test
  fun testDay08PartTwoTestInput() {
    assertEquals(34, day08.partTwo(DAY_08_TEST))
  }

  @Test
  fun testDay08PartOne() {
    assertEquals(273, day08.partOne(DAY_08))
  }

  @Test
  fun testDay08PartTwo() {
    assertEquals(1017, day08.partTwo(DAY_08))
  }

  companion object {
    private const val DAY_08: String = "advent-of-code-input/2024/day08.input"

    private const val DAY_08_TEST: String = "advent-of-code-input/2024/day08.test"
  }
}
