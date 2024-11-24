package nl.jackploeg.aoc._2021

import dagger.Component
import javax.inject.Singleton
import nl.jackploeg.aoc.DayComponent
import nl.jackploeg.aoc.InputModule
import nl.jackploeg.aoc._2021.calendar.day01.Day01Test

@Singleton
@Component(modules = [InputModule::class])
internal interface TestDayComponent : DayComponent {
  fun inject(day01Test: Day01Test)
}
