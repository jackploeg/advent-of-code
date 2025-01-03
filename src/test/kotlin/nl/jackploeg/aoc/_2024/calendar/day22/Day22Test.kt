package nl.jackploeg.aoc._2024.calendar.day22

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day22Test {
  @Inject
  lateinit var day22: Day22

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay22PartOneTestInput() {
    assertEquals(37327623, day22.partOne(DAY_22_TEST))
  }

  @Test
  fun testDay22PartTwoTestInput() {
    assertEquals(23, day22.partTwo(DAY_22_TESTA, 0))
  }

  @Test
  fun testDay22PartOne() {
    assertEquals(18694566361, day22.partOne(DAY_22))
  }

  @Test
  fun testDay22PartTwo() {
    assertEquals(2100, day22.partTwo(DAY_22, 4))
  }

  companion object {
    private const val DAY_22: String = "advent-of-code-input/2024/day22.input"

    private const val DAY_22_TEST: String = "advent-of-code-input/2024/day22.test"
    private const val DAY_22_TESTA: String = "advent-of-code-input/2024/day22.testa"
  }
}
