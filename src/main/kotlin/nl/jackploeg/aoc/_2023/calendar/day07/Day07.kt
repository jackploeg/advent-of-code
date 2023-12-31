package nl.jackploeg.aoc._2023.calendar.day07

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject

class Day07 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String): Long =
    readStringFile(fileName).map { parseHand(it) }.sorted().mapIndexed { index, hand -> (index + 1) * hand.bet }.sum()

  fun partTwo(fileName: String): Long =
    readStringFile(fileName).map { parseHand(it, true) }.sorted().mapIndexed { index, hand -> (index + 1) * hand.bet }.sum()

  fun parseHand(line: String, useJokers: Boolean = false): Hand
  {
    val (symbols, bet) = line.split(" ")
    val cards = if (!useJokers)
      symbols.asSequence().map { RegularCard.from(it) }.toList()
    else
      symbols.asSequence().map { JokerCard.from(it) }.toList()

    return Hand(cards as List<Card>, bet.toLong(), useJokers)
  }

  data class Hand(val cards: List<Card>, val bet: Long, val useJokers: Boolean) : Comparable<Hand>
  {
    private val handType: HandType
      get()
      {
        val groups = cards.groupBy { it }
        if (!useJokers)
        {
          if (groups.any { it.value.size == 5 })
            return HandType.FIVE_OF_A_KIND
          if (groups.any { it.value.size == 4 })
            return HandType.FOUR_OF_A_KIND
          if (groups.any { it.value.size == 3 } && groups.any { it.value.size == 2 })
            return HandType.FULL_HOUSE
          if (groups.any { it.value.size == 3 })
            return HandType.THREE_OF_A_KIND
          if (groups.any { it.value.size == 2 } && groups.size == 3)
            return HandType.TWO_PAIR
          if (groups.any { it.value.size == 2 })
            return HandType.ONE_PAIR
          return HandType.HIGH_CARD
        } else
        {
          val jokerCount = (groups as Map<JokerCard, List<JokerCard>>)[JokerCard.JOKER]?.size ?: 0
          val groupsWithoutJokers = groups.filter { it.key != JokerCard.JOKER }

          if (jokerCount == 0)
          {
            if (groupsWithoutJokers.any { it.value.size == 5 })
              return HandType.FIVE_OF_A_KIND
            if (groupsWithoutJokers.any { it.value.size == 4 })
              return HandType.FOUR_OF_A_KIND
            if (groupsWithoutJokers.any { it.value.size == 3 } && groups.any { it.value.size == 2 })
              return HandType.FULL_HOUSE
            if (groupsWithoutJokers.any { it.value.size == 3 })
              return HandType.THREE_OF_A_KIND
            if (groupsWithoutJokers.any { it.value.size == 2 } && groups.size == 3)
              return HandType.TWO_PAIR
            if (groupsWithoutJokers.any { it.value.size == 2 })
              return HandType.ONE_PAIR
            return HandType.HIGH_CARD
          }
          if (jokerCount == 1)
          {
            if (groupsWithoutJokers.any { it.value.size == 4 })
              return HandType.FIVE_OF_A_KIND
            if (groupsWithoutJokers.any { it.value.size == 3 })
              return HandType.FOUR_OF_A_KIND
            if (groupsWithoutJokers.all { it.value.size == 2 })
              return HandType.FULL_HOUSE
            if (groupsWithoutJokers.any { it.value.size == 2 })
              return HandType.THREE_OF_A_KIND
            // one card with 1 joker
            return HandType.ONE_PAIR
          }
          if (jokerCount == 2)
          {
            if (groupsWithoutJokers.any { it.value.size == 3 })
              return HandType.FIVE_OF_A_KIND
            if (groupsWithoutJokers.any { it.value.size == 2 })
              return HandType.FOUR_OF_A_KIND
            // 1 card with 2 jokers
            return HandType.THREE_OF_A_KIND
          }
          if (jokerCount == 3)
          {
            if (groupsWithoutJokers.any { it.value.size == 2 })
              return HandType.FIVE_OF_A_KIND
            return HandType.FOUR_OF_A_KIND
          }
          if (jokerCount == 4)
          {
            // 1 card with 4 jokers
            return HandType.FIVE_OF_A_KIND
          }
          // jokerCount==5
          return HandType.FIVE_OF_A_KIND
        }
      }


    override fun compareTo(other: Hand): Int
    {
      return if (this.handType > other.handType)
        1
      else if (this.handType < other.handType)
        -1
      else
        if (!useJokers)
          compareRegularCards(this.cards as List<RegularCard>, other.cards as List<RegularCard>)
        else
          compareJokerCards(this.cards as List<JokerCard>, other.cards as List<JokerCard>)
    }

    private fun compareRegularCards(myCards: List<RegularCard>, theirCards: List<RegularCard>): Int
    {
      val firstDifferentCard = myCards.zip(theirCards).firstOrNull { it.first > it.second || it.first < it.second }
      firstDifferentCard?.let { return it.first.compareTo(it.second) }
      return 0
    }

    private fun compareJokerCards(myCards: List<JokerCard>, theirCards: List<JokerCard>): Int
    {
      val firstDifferentCard = myCards.zip(theirCards).firstOrNull { it.first > it.second || it.first < it.second }
      firstDifferentCard?.let { return it.first.compareTo(it.second) }
      return 0
    }
  }

  interface Card

  enum class RegularCard(val symbol: Char) : Card
  {
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    companion object
    {
      private val map = entries.associateBy { it.symbol }
      infix fun from(symbol: Char) = map[symbol]!!
    }
  }

  enum class JokerCard(val symbol: Char) : Card
  {
    JOKER('J'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    companion object
    {
      private val map = entries.associateBy { it.symbol }
      infix fun from(symbol: Char) = map[symbol]!!
    }
  }

  enum class HandType
  {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
  }
}
