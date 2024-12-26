package nl.jackploeg.aoc._2024.calendar.day21

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day21Test {
  @Inject
  lateinit var day21: Day21

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay21PartOneTestInput() {
    assertEquals(126384, day21.partOne(DAY_21_TEST))
  }

  @Test
  fun testDay21PartOne() {
    assertEquals(157230, day21.partOne(DAY_21))
  }

  @Test
  fun testDay21PartTwo() {
    assertEquals(195969155897936, day21.partTwo(DAY_21))
  }

  companion object {
    private const val DAY_21: String = "advent-of-code-input/2024/day21.input"

    private const val DAY_21_TEST: String = "advent-of-code-input/2024/day21.test"
  }
}
