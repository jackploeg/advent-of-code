package nl.jackploeg.aoc._2024.calendar.day16

import javax.inject.Inject

import nl.jackploeg.aoc._2024.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Day16Test {
  @Inject
  lateinit var day16: Day16

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay16PartOneTestInput() {
    assertEquals(7036, day16.partOne(DAY_16_TEST))
  }

  @Test
  fun testDay16PartTwoTestInput() {
    assertEquals(45, day16.partTwo(DAY_16_TEST))
  }

  @Test
  fun testDay16PartTwoTestAInput() {
    assertEquals(64, day16.partTwo(DAY_16_TESTA))
  }

  @Test
  fun testDay16PartOne() {
    assertEquals(109516, day16.partOne(DAY_16))
  }

  @Test
  fun testDay16PartTwo() {
    assertEquals(568, day16.partTwo(DAY_16))
  }

  companion object {
    private const val DAY_16: String = "advent-of-code-input/2024/day16.input"

    private const val DAY_16_TEST: String = "advent-of-code-input/2024/day16.test"
    private const val DAY_16_TESTA: String = "advent-of-code-input/2024/day16.testa"
  }
}
