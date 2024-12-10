package nl.jackploeg.aoc._2024.calendar.day09

import java.io.File
import javax.inject.Inject

class Day09 @Inject constructor() {
    fun partOne(filename: String): Long {
        val blocks: MutableList<Int> = readBlocks(filename)
        compactBlocks(blocks)
        return blocks.checksum()
    }



    fun partTwo(filename: String): Long {
        val blocks: MutableList<Int> = readBlocks(filename)
        compactFiles(blocks)
        return blocks.checksum()
    }

    private fun readBlocks(filename: String): MutableList<Int> {
        val input = File(filename).readLines()
        var fileId = 0
        val blocks: MutableList<Int> = mutableListOf()
        val blockValues = input[0].toCharArray().map { it.code - 48 }
        (0..input[0].length step 2).forEach { index ->
            blocks.addAll((1..blockValues[index]).map { fileId })
            if (index < blockValues.size - 1) {
                blocks.addAll((1..blockValues[index + 1]).map { -1 })
            }
            fileId++
        }
        return blocks
    }

    private fun compactBlocks(blocks: MutableList<Int>) {
        var isCompact = false
        while (!isCompact) {
            val lastFileBlock = blocks.indexOfLast { it != -1 }
            val firstEmptyBlock = blocks.indexOfFirst { it == -1 }
            if (lastFileBlock < firstEmptyBlock) {
                isCompact = true
            } else {
                blocks[firstEmptyBlock] = blocks[lastFileBlock]
                blocks[lastFileBlock] = -1
            }
        }
    }

    private fun compactFiles(blocks: MutableList<Int>) {
        val files = blocks.groupBy { it }.filter { it.key != -1 }.map{ f -> File(f.key,f.value.size, blocks.indexOfFirst { it == f.key })}
        var emptyBlockFound = false
        var emptyBlockSize = 0
        var emptyBlockIndex = 0
        val emptyBlocks : MutableList<EmptyBlock> = mutableListOf()
        blocks.forEachIndexed { index, block ->
            if (block == -1) {
                if (!emptyBlockFound) {
                    emptyBlockFound = true
                    emptyBlockSize = 0
                    emptyBlockIndex = index
                }
                emptyBlockSize++
            } else  {
                if (emptyBlockFound) {
                    emptyBlocks.add(EmptyBlock(emptyBlockSize, emptyBlockIndex ))
                }
                emptyBlockFound = false
            }
        }
        // add last empty block, if no file after it
        if (emptyBlockFound) {
            emptyBlocks.add(EmptyBlock(emptyBlockSize, emptyBlockIndex ))
        }
        (files.size - 1 downTo 0).forEach { index ->
            val file = files[index]
            val emptyBlock = emptyBlocks.firstOrNull { it.size >= file.size }
            if (emptyBlock != null && emptyBlock.start < file.start) {
                (emptyBlock.start until emptyBlock.start + file.size).forEach { blocks[it] = file.id }
                emptyBlock.size -= file.size
                emptyBlock.start += file.size
                (file.start until file.start + file.size).forEach { blocks[it] = -1 }
            }
        }
    }

    fun List<Int>.checksum(): Long =
        this.mapIndexed { index, value -> if (value == -1) 0 else index * value.toLong() }.sum()

    data class File(val id: Int, val size: Int, val start: Int)
    data class EmptyBlock(var size: Int, var start: Int)
}

