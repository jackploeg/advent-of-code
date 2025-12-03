package nl.jackploeg.aoc._2015.calendar.day02

import javax.inject.Inject

import nl.jackploeg.aoc._2015.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day02Test {
  @Inject
  lateinit var day02: Day02

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay02PartOneTestInput() {
    assertEquals(101, day02.partOne(DAY_02_TEST))
  }

  @Test
  fun testDay02PartTwoTestInput() {
    assertEquals(48, day02.partTwo(DAY_02_TEST))
  }

  @Test
  fun testDay02PartOne() {
    assertEquals(1606483, day02.partOne(DAY_02))
  }

  @Test
  fun testDay02PartTwo() {
    assertEquals(3842356, day02.partTwo(DAY_02))
  }

  companion object {
    private const val DAY_02: String = "advent-of-code-input/2015/day02.input"

    private const val DAY_02_TEST: String = "advent-of-code-input/2015/day02.test"
  }
}
