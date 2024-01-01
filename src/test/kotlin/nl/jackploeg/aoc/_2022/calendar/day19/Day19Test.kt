package nl.jackploeg.aoc._2022.calendar.day19

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day19Test {
  @Inject
  lateinit var day19: Day19

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay19PartOneTestInput() {
    assertEquals(33, day19.partOne(DAY_19_TEST))
  }

  @Test
  fun testDay19PartTwoTestInput() {
    assertEquals(3472, day19.partTwoAlt(DAY_19_TEST))
  }

  @Test
  fun testDay19PartOne() {
    assertEquals(1264, day19.partOne(DAY_19))
  }

  @Test
  fun testDay19PartTwo() {
    assertEquals(13475, day19.partTwoAlt(DAY_19))
  }

  companion object {
    private const val DAY_19: String = "advent-of-code-input/2022/day19.input"

    private const val DAY_19_TEST: String = "advent-of-code-input/2022/day19.test"
  }
}
