package nl.jackploeg.aoc._2024.calendar.day5

import java.io.File
import javax.inject.Inject

class Day5 @Inject constructor() {
    fun partOne(filename: String): Int {
        val file = File(filename)
        val input = file.readLines()
        val orderRules: MutableList<OrderRule> = mutableListOf()
        val pageUpdates: MutableList<List<Int>> = mutableListOf()
        var readingRules = true
        input.forEach { line ->
            if (line == "") {
                readingRules = false
            } else {
                if (readingRules) {
                    val (f, s) = line.split('|')
                    orderRules.add(OrderRule(f.toInt(), s.toInt()))
                } else {
                    pageUpdates.add(line.split(',').map { it.toInt() })
                }
            }
        }
        return pageUpdates.filter { it.isValid(orderRules) }.sumOf { it.getMiddleValue() }
    }

    fun partTwo(filename: String): Int {
        val file = File(filename)
        val input = file.readLines()
        val orderRules: MutableList<OrderRule> = mutableListOf()
        val pageUpdates: MutableList<List<Int>> = mutableListOf()
        var readingRules = true
        input.forEach { line ->
            if (line == "") {
                readingRules = false
            } else {
                if (readingRules) {
                    val (f, s) = line.split('|')
                    orderRules.add(OrderRule(f.toInt(), s.toInt()))
                } else {
                    pageUpdates.add(line.split(',').map { it.toInt() })
                }
            }
        }
        val correctedUpdates = pageUpdates.filter { !it.isValid(orderRules) }.map {
            it.orderCorrectly(orderRules)
        }
        return correctedUpdates.sumOf { it.getMiddleValue() }
    }

    data class OrderRule(val first: Int, val second: Int)

    fun List<Int>.getMiddleValue() = this.get(this.size / 2)

    fun List<Int>.isValid(rules: List<OrderRule>): Boolean {
        this.forEachIndexed { index, page ->
            if (rules.count { it.first == page && this.contains(it.second) && this.indexOf(it.second) < index } > 0) {
                return false
            }
            if (rules.count { it.second == page && this.contains(it.first) && this.indexOf(it.first) > index } > 0) {
                return false
            }
        }
        return true
    }

    fun List<Int>.orderCorrectly(rules: List<OrderRule>): List<Int> {
        var orderIsCorrect = false
        var tryList: MutableList<Int> = mutableListOf()
        val newTry: MutableList<Int> = mutableListOf()
        newTry.addAll(this)
        while (!orderIsCorrect) {
            tryList = newTry
            for (i in 0 .. tryList.size -1) {
                if (rules.count { it.first == tryList[i] && tryList.contains(it.second) && tryList.indexOf(it.second) < i } > 0) {
                    val earlier = rules.first { it.first == tryList[i] && tryList.indexOf(it.second) < i }
                    newTry.remove(earlier.second)
                    newTry.add(i, earlier.second)
                    orderIsCorrect = false
                    break
                }
                if (rules.count { it.second == tryList[i] && tryList.contains(it.first) && tryList.indexOf(it.first) > i } > 0) {
                    val earlier = rules.first { it.second == tryList[i] && tryList.indexOf(it.first) > i }
                    newTry.remove(earlier.first)
                    newTry.add(i, earlier.first)
                    orderIsCorrect = false
                    break
                }
                orderIsCorrect = true
            }
        }
        return tryList
    }
}
