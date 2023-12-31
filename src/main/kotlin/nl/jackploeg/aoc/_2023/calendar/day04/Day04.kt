package nl.jackploeg.aoc._2023.calendar.day04

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject
import kotlin.math.pow

class Day04 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String): Int {
    val cards: MutableList<ScratchCard> = mutableListOf()
    readScratchCards(fileName, cards)
    return cards.sumOf { it.getCardPoints() }
  }

  fun partTwo(fileName: String): Int {
    val cards: MutableList<ScratchCard> = mutableListOf()
    readScratchCards(fileName, cards)

    val cardBuckets: Map<Int, ScratchCard> =
      cards.associateBy { it.cardNumber }

    cardBuckets.forEach {
      val matches = it.value.getCardMatches()
      if (matches==0) {
        return@forEach
      }
      for (i in 1..matches) {
        cardBuckets[it.key + i]?.incrementCopies(it.value.copies)
      }
    }
    return cardBuckets.map { it.value.copies }.sum()
  }

  fun readScratchCards(fileName: String, cards: MutableList<ScratchCard>) =
    readStringFile(fileName).forEach { cards.add(parseCardData(it)) }

  fun parseCardData(line: String): ScratchCard {
    val cardNo: Int = line.split(':')[0].split("\\D+".toRegex())[1].toInt()
    val numbers: String = line.split(':')[1]
    val winningNumbers = numbers.split('|')[0].trim().split("\\D+".toRegex()).map { it.toInt() }
    val drawNumbers = numbers.split('|')[1].trim().split("\\D+".toRegex()).map { it.toInt() }
    return ScratchCard(cardNo, winningNumbers.toSortedSet(), drawNumbers.toSortedSet())
  }

  data class ScratchCard(
    val cardNumber: Int,
    val winningNumbers: MutableSet<Int> = mutableSetOf(),
    val drawNumbers: MutableSet<Int> = mutableSetOf()
  ) {
    var copies = 1
    fun getCardPoints(): Int {
      val wins = drawNumbers.count { it in winningNumbers }
      if (wins == 0) {
        return 0
      }
      return 2.0.pow((wins - 1).toDouble()).toInt()
    }

    fun getCardMatches(): Int =
      drawNumbers.count { it in winningNumbers }

    fun incrementCopies(increment: Int) {
      copies += increment
    }
  }
}
