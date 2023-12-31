package nl.jackploeg.aoc._2023.calendar.day24

import javax.inject.Inject

import nl.jackploeg.aoc._2023.DaggerTestDayComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class Day24Test {
  @Inject
  lateinit var day24: Day24

  @BeforeEach
  fun setup() {
    DaggerTestDayComponent.create().inject(this)
  }

  @Test
  fun testDay24PartOneTestInput() {
    assertEquals(2, day24.partOne(DAY_24_TEST,7,27))
  }

  @Test
  fun testDay24PartOne() {
    assertEquals(11995, day24.partOne(DAY_24, 200000000000000L, 400000000000000L))
  }

  @Test
  fun testDay24PartTwo() {
    assertEquals(BigDecimal(983620716335751), day24.partTwo(DAY_24))
  }

  companion object {
    private const val DAY_24: String = "advent-of-code-input/2023/day24.input"
    private const val DAY_24_TEST: String = "advent-of-code-input/2023/day24.test"
  }
}
