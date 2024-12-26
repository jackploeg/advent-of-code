package nl.jackploeg.aoc._2024.calendar.day24

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
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
    assertEquals(4, day24.partOne(DAY_24_TEST))
  }

  @Test
  fun testDay24PartOneTestAInput() {
    assertEquals(2024, day24.partOne(DAY_24_TESTA))
  }

  @Test
  fun testDay24PartOne() {
    assertEquals(51410244478064, day24.partOne(DAY_24))
  }

  @Test
  fun testDay24PartTwo() {
    assertEquals("gst,khg,nhn,tvb,vdc,z12,z21,z33", day24.partTwo(DAY_24))
  }

  companion object {
    private const val DAY_24: String = "advent-of-code-input/2024/day24.input"

    private const val DAY_24_TEST: String = "advent-of-code-input/2024/day24.test"
    private const val DAY_24_TESTA: String = "advent-of-code-input/2024/day24.testa"
  }
}
