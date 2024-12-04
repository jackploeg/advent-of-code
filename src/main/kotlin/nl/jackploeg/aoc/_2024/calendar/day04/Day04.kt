package nl.jackploeg.aoc._2024.calendar.day04

import java.io.File
import javax.inject.Inject

class Day04 @Inject constructor() {
    fun partOne(filename: String): Int {
        val file = File(filename)
        val input = file.readLines()
        var count = 0
        input.forEachIndexed { vertIndex, s ->
            s.forEachIndexed { horIndex, c ->
                count += checkHorizontal(input, vertIndex, horIndex)
                count += checkVertical(input, vertIndex, horIndex)
                count += checkDiagonalLeft(input, vertIndex, horIndex)
                count += checkDiagonalRight(input, vertIndex, horIndex)
            }
        }
        return count
    }

    private fun checkHorizontal(input: List<String>, vertIndex: Int, horIndex: Int): Int {
        val width = input[0].length
        var count = 0
        if (input[vertIndex][horIndex] != 'X') {
            return 0
        }
        if (horIndex < width - 3 &&
            input[vertIndex][horIndex + 1] == 'M' &&
            input[vertIndex][horIndex + 2] == 'A' &&
            input[vertIndex][horIndex + 3] == 'S'
        ) {
            count++
        }
        if (horIndex >= 3 &&
            input[vertIndex][horIndex - 1] == 'M' &&
            input[vertIndex][horIndex - 2] == 'A' &&
            input[vertIndex][horIndex - 3] == 'S'
        ) {
            count++
        }
        return count
    }

    private fun checkVertical(input: List<String>, vertIndex: Int, horIndex: Int): Int {
        var count = 0
        if (input[vertIndex][horIndex] != 'X') {
            return 0
        }
        if (vertIndex >= 3 &&
            input[vertIndex - 1][horIndex] == 'M' &&
            input[vertIndex - 2][horIndex] == 'A' &&
            input[vertIndex - 3][horIndex] == 'S'
        ) {
            count++
        }
        if (vertIndex < input.size - 3 &&
            input[vertIndex + 1][horIndex] == 'M' &&
            input[vertIndex + 2][horIndex] == 'A' &&
            input[vertIndex + 3][horIndex] == 'S'
        ) {
            count++
        }
        return count
    }

    private fun checkDiagonalRight(input: List<String>, vertIndex: Int, horIndex: Int): Int {
        val width = input[0].length
        var count = 0
        if (input[vertIndex][horIndex] != 'X') {
            return 0
        }
        // up
        if (horIndex < width - 3 &&
            vertIndex >= 3 &&
            input[vertIndex - 1][horIndex + 1] == 'M' &&
            input[vertIndex - 2][horIndex + 2] == 'A' &&
            input[vertIndex - 3][horIndex + 3] == 'S'
        ) {
            count++
        }
        // down
        if (horIndex < width - 3 &&
            vertIndex < input.size - 3 &&
            input[vertIndex + 1][horIndex + 1] == 'M' &&
            input[vertIndex + 2][horIndex + 2] == 'A' &&
            input[vertIndex + 3][horIndex + 3] == 'S'
        ) {
            count++
        }
        return count
    }

    private fun checkDiagonalLeft(input: List<String>, vertIndex: Int, horIndex: Int): Int {
        var count = 0
        if (input[vertIndex][horIndex] != 'X') {
            return 0
        }
        // up
        if (horIndex >= 3 &&
            vertIndex >= 3 &&
            input[vertIndex - 1][horIndex - 1] == 'M' &&
            input[vertIndex - 2][horIndex - 2] == 'A' &&
            input[vertIndex - 3][horIndex - 3] == 'S'
        ) {
            count++
        }
        // down
        if (horIndex >= 3 &&
            vertIndex < input.size - 3 &&
            input[vertIndex + 1][horIndex - 1] == 'M' &&
            input[vertIndex + 2][horIndex - 2] == 'A' &&
            input[vertIndex + 3][horIndex - 3] == 'S'
        ) {
            count++
        }
        return count
    }

    private fun checkXMas(input: List<String>, vertIndex: Int, horIndex: Int): Int {
        var count = 0
        val width = input[0].length
        if (input[vertIndex][horIndex] != 'A' ||
            vertIndex < 1 ||
            vertIndex > input.size - 2 ||
            horIndex < 1 ||
            horIndex > width - 2
        ) {
            return 0
        }
        if (
            (
                    (input[vertIndex - 1][horIndex - 1] == 'M' && input[vertIndex + 1][horIndex + 1] == 'S') ||
                            (input[vertIndex - 1][horIndex - 1] == 'S' && input[vertIndex + 1][horIndex + 1] == 'M')
                    ) &&
            (
                    (input[vertIndex + 1][horIndex - 1] == 'M' && input[vertIndex - 1][horIndex + 1] == 'S') ||
                            (input[vertIndex + 1][horIndex - 1] == 'S' && input[vertIndex - 1][horIndex + 1] == 'M')
                    )
        ) {
            count++
        }
        return count
    }


    fun partTwo(filename: String): Int {
        val file = File(filename)
        val input = file.readLines()
        var count = 0
        input.forEachIndexed { vertIndex, s ->
            s.forEachIndexed { horIndex, c ->
                count += checkXMas(input, vertIndex, horIndex)
            }
        }
        return count
    }

}
