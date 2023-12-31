package nl.jackploeg.aoc._2023.calendar.day05

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.math.BigInteger
import javax.inject.Inject

class Day05 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  /* Seed -> soil -> fertilizer -> water -> light -> temperature -> humidity -> location */

  fun partOne(fileName: String): BigInteger {
    val (seeds, mappers) = loadMaps(fileName)
    return seeds.map { getLocationForSeed(it, mappers) }.minOfOrNull { it.first }!!
  }

  private val MAX_VALUE = "9999999999999999"

  fun partTwo(fileName: String): BigInteger {
    val (seeds, mappers) = loadMaps(fileName)
    val seedRanges: MutableList<Pair<BigInteger, BigInteger>> = mutableListOf()

    for (i in seeds.indices step 2) {
      seedRanges.add(Pair(seeds[i], seeds[i + 1]))
    }

    var minLocation = BigInteger(MAX_VALUE)
    for (seedRange in seedRanges) {
      var seed = seedRange.first
      while (seed < seedRange.first + seedRange.second) {
        val (location, range) = getLocationForSeed(seed, mappers)
        if (location < minLocation) {
          minLocation = location
        }
        seed += range
      }
    }
    return minLocation
  }

  fun getMappedValueAndNextRangeSize(source: Pair<BigInteger, BigInteger>, mappers: List<Mapper>?): Pair<BigInteger, BigInteger> {
    val mapper = mappers?.firstOrNull { it.inRange(source.first) }
    return mapper?.let {
      Pair(
        source.first - mapper.source + mapper.destination,
        source.second.min(mapper.range - (source.first - mapper.source))
      )
    } ?: source
  }


  fun getLocationForSeed(
    seed: BigInteger,
    mappers: Map<Pair<ResourceType, ResourceType>, List<Mapper>>
  ): Pair<BigInteger, BigInteger> {
    val soil =
      getMappedValueAndNextRangeSize(Pair(seed, BigInteger("9999999999")),
        mappers[Pair(ResourceType.SEED, ResourceType.SOIL)]
      )
    val fertilizer = getMappedValueAndNextRangeSize(soil, mappers[Pair(ResourceType.SOIL, ResourceType.FERTILIZER)])
    val water = getMappedValueAndNextRangeSize(fertilizer, mappers[Pair(ResourceType.FERTILIZER, ResourceType.WATER)])
    val light = getMappedValueAndNextRangeSize(water, mappers[Pair(ResourceType.WATER, ResourceType.LIGHT)])
    val temperature = getMappedValueAndNextRangeSize(light, mappers[Pair(ResourceType.LIGHT, ResourceType.TEMPERATURE)])
    val humidity = getMappedValueAndNextRangeSize(temperature,
      mappers[Pair(ResourceType.TEMPERATURE, ResourceType.HUMIDITY)]
    )
    val location = getMappedValueAndNextRangeSize(humidity, mappers[Pair(ResourceType.HUMIDITY, ResourceType.LOCATION)])
    return location
  }

  fun loadMaps(fileName: String): Pair<List<BigInteger>, Map<Pair<ResourceType, ResourceType>, List<Mapper>>> {

    var seeds: List<BigInteger> = listOf()
    var mapKey: Pair<ResourceType, ResourceType> = Pair(ResourceType.SEED, ResourceType.SOIL)
    var numberMappers = mutableListOf<Mapper>()
    val maps: MutableMap<Pair<ResourceType, ResourceType>, List<Mapper>> = mutableMapOf()

    fun saveMapAndReInit() {
      numberMappers.sortBy { it.source }
      // add unmapped ranges
      var i = BigInteger.ZERO
      val defaultMappers: MutableList<Mapper> = mutableListOf()
      for (mapper in numberMappers) {
        if (mapper.source > i) {
          defaultMappers.add(Mapper(i, i, mapper.source - i))
        }
        i = mapper.source + mapper.range
      }

      defaultMappers.add(Mapper(i, i, BigInteger(MAX_VALUE)))
      numberMappers.addAll(defaultMappers)
      numberMappers.sortBy { it.source }
      maps[mapKey] = numberMappers
      numberMappers = mutableListOf()
    }

    readStringFile(fileName).forEach { line ->
      if (line.startsWith("seeds")) {
        seeds = line.split(":")[1].trim().split(" ").map { BigInteger(it) }
      } else if (line.startsWith("seed-to-soil map")) {
        mapKey = Pair(ResourceType.SEED, ResourceType.SOIL)
        numberMappers = mutableListOf()
      } else if (line.startsWith("soil-to-fertilizer map")) {
        saveMapAndReInit()
        mapKey = Pair(ResourceType.SOIL, ResourceType.FERTILIZER)
        numberMappers = mutableListOf()
      } else if (line.startsWith("fertilizer-to-water map")) {
        saveMapAndReInit()
        mapKey = Pair(ResourceType.FERTILIZER, ResourceType.WATER)
        numberMappers = mutableListOf()
      } else if (line.startsWith("water-to-light map")) {
        saveMapAndReInit()
        mapKey = Pair(ResourceType.WATER, ResourceType.LIGHT)
        numberMappers = mutableListOf()
      } else if (line.startsWith("light-to-temperature map")) {
        saveMapAndReInit()
        mapKey = Pair(ResourceType.LIGHT, ResourceType.TEMPERATURE)
        numberMappers = mutableListOf()
      } else if (line.startsWith("temperature-to-humidity map")) {
        saveMapAndReInit()
        mapKey = Pair(ResourceType.TEMPERATURE, ResourceType.HUMIDITY)
        numberMappers = mutableListOf()
      } else if (line.startsWith("humidity-to-location map")) {
        saveMapAndReInit()
        mapKey = Pair(ResourceType.HUMIDITY, ResourceType.LOCATION)
        numberMappers = mutableListOf()
      } else if (line.trim() != "") {
        val (destination, source, range) = line.split(" ").map { BigInteger(it) }
        numberMappers.add(Mapper(source, destination, range))
      }
    }
    saveMapAndReInit()

    return Pair(seeds, maps)
  }

  data class Mapper(val source: BigInteger, val destination: BigInteger, val range: BigInteger) {
    fun inRange(number: BigInteger) =
      number >= source && number < source + range
  }

  enum class ResourceType {
    SEED, SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION
  }
}
