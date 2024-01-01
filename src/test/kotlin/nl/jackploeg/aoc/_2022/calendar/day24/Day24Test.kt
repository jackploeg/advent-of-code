package nl.jackploeg.aoc._2022.calendar.day24

import javax.inject.Inject

import nl.jackploeg.aoc._2022.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day24Test {
  @Inject
  lateinit var day24: Day24

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay24PartOneTestInput() {
    assertEquals(18, day24.partOne(DAY_24_TEST,100))
  }

  @Test
  fun testDay24PartTwoTestInput() {
    assertEquals(54, day24.partTwo(DAY_24_TEST,100))
  }

  @Test
  fun testDay24PartOne() {
    assertEquals(332, day24.partOne(DAY_24,1000))
  }

  @Test
  fun testDay24PartTwo() {
    assertEquals(942, day24.partTwo(DAY_24,1000))
  }

  companion object {
    private const val DAY_24: String = "advent-of-code-input/2022/day24.input"

    private const val DAY_24_TEST: String = "advent-of-code-input/2022/day24.test"
  }
}
