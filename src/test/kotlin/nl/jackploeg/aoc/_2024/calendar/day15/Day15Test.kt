package nl.jackploeg.aoc._2024.calendar.day15

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day15Test {
  @Inject
  lateinit var day15: Day15

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay15PartOneTestInput() {
    assertEquals(10092, day15.partOne(DAY_15_TEST))
  }

  @Test
  fun testDay15PartOneTestAInput() {
    assertEquals(2028, day15.partOne(DAY_15_TESTA))
  }

  @Test
  fun testDay15PartTwoTestInput() {
    assertEquals(9021, day15.partTwo(DAY_15_TEST))
  }

  @Test
  fun testDay15PartTwoTestBInput() {
    assertEquals(618, day15.partTwo(DAY_15_TESTB))
  }

  @Test
  fun testDay15PartOne() {
    assertEquals(1412971, day15.partOne(DAY_15))
  }

  @Test
  fun testDay15PartTwo() {
    assertEquals(1429299, day15.partTwo(DAY_15))
  }

  companion object {
    private const val DAY_15: String = "advent-of-code-input/2024/day15.input"

    private const val DAY_15_TEST: String = "advent-of-code-input/2024/day15.test"
    private const val DAY_15_TESTA: String = "advent-of-code-input/2024/day15.testa"
    private const val DAY_15_TESTB: String = "advent-of-code-input/2024/day15.testb"
  }
}
