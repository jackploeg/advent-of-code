package nl.jackploeg.aoc._2024.calendar.day25

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day25Test {
  @Inject
  lateinit var day25: Day25

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay25PartOneTestInput() {
    assertEquals(3, day25.partOne(DAY_25_TEST))
  }

  @Test
  fun testDay25PartOne() {
    assertEquals(3690, day25.partOne(DAY_25))
  }

  companion object {
    private const val DAY_25: String = "advent-of-code-input/2024/day25.input"

    private const val DAY_25_TEST: String = "advent-of-code-input/2024/day25.test"
  }
}
