package nl.jackploeg.aoc

import dagger.Module
import dagger.Provides
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.generators.PermutationGenerator
import javax.inject.Singleton

@Module
internal class InputModule {
  @Provides
  @Singleton
  fun inputGeneratorFactory() = InputGeneratorFactory()

  @Provides
  @Singleton
  fun permutationGenerator() = PermutationGenerator()
}
