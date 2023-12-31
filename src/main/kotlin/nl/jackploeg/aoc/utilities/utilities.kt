package nl.jackploeg.aoc.utilities

import java.io.File
import java.math.BigInteger

fun readNumbersFile(fileName: String): List<Int> {
    return File(fileName)
        .readLines()
        .map { it.toInt() }
}

fun readStringFile(fileName: String): List<String> {
    return File(fileName)
        .readLines()
}

fun repeatString(input: String, repeat: Int, separator: Char) : String {
    if (repeat==1)
        return input
    val result= StringBuilder(input)
    repeat(( 2..repeat).count()) {
        result.append(separator)
        result.append(input)
    }
    return result.toString()
}

fun printList(list: List<*>) {
    list.forEach { println(it) }
}

fun leastCommonMultiple(first: BigInteger, second: BigInteger): BigInteger {
    val larger = if (first > second) first else second
    val maxLcm = first * second
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm.mod(first) == BigInteger.ZERO && lcm.mod(second) == BigInteger.ZERO ) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

enum class Direction {
    UP, RIGHT, DOWN, LEFT;

    fun reversed() =
        when (this) {
            UP->DOWN
            DOWN->UP
            LEFT->RIGHT
            RIGHT->LEFT
        }
}

