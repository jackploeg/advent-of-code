package nl.jackploeg.aoc

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [InputModule::class])
internal interface DayComponent
