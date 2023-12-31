package nl.jackploeg.aoc._2023.calendar.day06

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject
import kotlin.math.sqrt

class Day06 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun countRecordOptions(raceTime: Long, record: Long): Long {
    /* distance = (raceTime - push) * push  =>  -push^2 + push*raceTime */
    val (min, max) = recordBeatingTimes(raceTime, record)
    return max - min + 1
  }

  fun recordBeatingTimes(raceTime: Long, record: Long) : Pair<Long, Long> {
    val(min, max) = recordIntersectionTimes(raceTime, record)
    return Pair((min + 1).toLong(), (max-0.0001).toLong())
  }

  fun recordIntersectionTimes(raceTime: Long, record: Long): Pair<Double, Double> {
    // b^2 - 4ac, a==-1
    val discriminator: Double = sqrt((raceTime * raceTime + 4 * -record).toDouble())
    // (-b +/- discriminator)/2a
    val i1: Double = (-raceTime + discriminator) / -2
    val i2: Double = (-raceTime - discriminator) / -2
    return Pair(i1, i2)
  }

  fun partOne(fileName: String): Long {
    val whiteSpaceSplitter = "\\D+".toRegex()
    val recordInfo = readStringFile(fileName)
    val raceTimes = recordInfo[0].split(":")[1].trim().split(whiteSpaceSplitter).map { it.toLong() }
    val records = recordInfo[1].split(":")[1].trim().split(whiteSpaceSplitter).map { it.toLong() }
    return raceTimes
      .mapIndexed { index, i -> countRecordOptions(i, records[index]) }
      .reduce { acc, recordOptions -> acc * recordOptions }
  }

  fun partTwo(fileName: String): Long {
    val whiteSpaceSplitter = "\\D+".toRegex()
    val recordInfo = readStringFile(fileName)
    val raceTime = recordInfo[0].split(":")[1].trim().replace(whiteSpaceSplitter, "").toLong()
    val record = recordInfo[1].split(":")[1].trim().replace(whiteSpaceSplitter, "").toLong()
    return  countRecordOptions(raceTime, record)
  }


}
