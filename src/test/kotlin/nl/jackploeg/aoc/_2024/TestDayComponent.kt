package nl.jackploeg.aoc._2024

import dagger.Component
import nl.jackploeg.aoc._2024.calendar.day03.Day03Test
import nl.jackploeg.aoc._2024.calendar.day02.Day02Test
import nl.jackploeg.aoc._2024.calendar.day04.Day04Test
import nl.jackploeg.aoc._2024.calendar.day5.Day5Test
import javax.inject.Singleton
import nl.jackploeg.aoc.DayComponent
import nl.jackploeg.aoc.InputModule
import nl.jackploeg.aoc._2024.calendar.day01.Day01Test

@Singleton
@Component(modules = [InputModule::class])
internal interface TestDayComponent : DayComponent {
  fun inject(day01Test: Day01Test)
  fun inject(day03Test: Day03Test)
  fun inject(day02Test: Day02Test)
  fun inject(day04Test: Day04Test)
  fun inject(day5Test: Day5Test)
}
