package nl.jackploeg.aoc._2023.calendar.day20

import javax.inject.Inject


import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day20Test {
  @Inject
  lateinit var day20: Day20

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay20PartOneTestInput() {
    assertEquals(32000000, day20.partOne(DAY_20_TEST))
  }

  @Test
  fun testDay20PartTwoTestInput2() {
    assertEquals(11687500, day20.partOne(DAY_20_TEST2))
  }

  @Test
  fun testDay20PartOne() {
    assertEquals(898557000, day20.partOne(DAY_20))
  }

  @Test
  fun testDay20PartTwo() {
    assertEquals(238420328103151, day20.partTwo(DAY_20))
  }

  companion object {
    private const val DAY_20: String = "advent-of-code-input/2023/day20.input"
    private const val DAY_20_TEST: String = "advent-of-code-input/2023/day20.test"
    private const val DAY_20_TEST2: String = "advent-of-code-input/2023/day20.test.2"
  }
}
