package nl.jackploeg.aoc._2023.calendar.day09

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day09Test {
  @Inject
  lateinit var day09: Day09

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay09PartOneTestInput() {
    assertEquals(114, day09.partOne(DAY_09_TEST))
  }

  @Test
  fun testDay09PartTwoTestInput() {
    assertEquals(2, day09.partTwo(DAY_09_TEST))
  }

  @Test
  fun testDay09PartOne() {
    assertEquals(2105961943, day09.partOne(DAY_09))
  }

  @Test
  fun testDay09PartTwo() {
    assertEquals(1019, day09.partTwo(DAY_09))
  }

  companion object {
    private const val DAY_09: String = "advent-of-code-input/2023/day09.input"
    private const val DAY_09_TEST: String = "advent-of-code-input/2023/day09.test"
  }
}
