package nl.jackploeg.aoc._2023.calendar.day22

import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.utilities.readStringFile
import java.util.*
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class Day22 @Inject constructor(
  private val generatorFactory: InputGeneratorFactory,
) {
  fun partOne(fileName: String): Int
  {
    val snapshot: List<Block> = readStringFile(fileName).mapIndexed { index, line -> parseBlock(index, line) }

    val rightBorder = snapshot.maxOf { it.right() }
    val backBorder = snapshot.maxOf { it.back() }
    val topBorder = snapshot.maxOf { it.top() }

    val stack = Stack(rightBorder + 1, backBorder + 1, topBorder + 1)
    stack.blocks.addAll(snapshot)
    snapshot.forEach { claimPointsForBlock(it, stack) }

    stack.dropBlocks()

    return countRemovableBlocks(stack).size
  }

  fun partTwo(fileName: String): Int
  {
    val snapshot: List<Block> = readStringFile(fileName).mapIndexed { index, line -> parseBlock(index, line) }

    val rightBorder = snapshot.maxOf { it.right() }
    val backBorder = snapshot.maxOf { it.back() }
    val topBorder = snapshot.maxOf { it.top() }

//    println(rightBorder)
//    println(backBorder)
//    println(topBorder)

    val stack = Stack(rightBorder + 1, backBorder + 1, topBorder + 1)
    stack.blocks.addAll(snapshot)
    snapshot.forEach { claimPointsForBlock(it, stack) }
    stack.dropBlocks()

    return sumOfBlocksDropping(stack)
  }

  fun countRemovableBlocks(stack: Stack): MutableList<Block>
  {
    var removableBlocks: MutableList<Block> = mutableListOf()
    stack.blocks.forEach { block ->
      if (stack.blockSupport[block] == null)
      {
        removableBlocks.add(block)
      } else if (stack.blockSupport[block]!!.supports.size == 0)
      {
        removableBlocks.add(block)
      } else if (stack.blockSupport[block]!!.supports.all { supported ->
          stack.blockSupport[supported]!!.restsOn.any { it != block }
        })
      {
        removableBlocks.add(block)
      }
    }
    //   println(removableBlocks)
    return removableBlocks
  }

  fun sumOfBlocksDropping(stack: Stack): Int
  {
    var dropCount = 0
    stack.blocks.forEach {
      stack.blocks.forEach { block ->
        block.willFall = false
      }
      dropCount += markBlocksFalling(it, stack)
    }
    return dropCount
  }

  fun markBlocksFalling(block:Block, stack: Stack, ): Int {
//    println("Remove block ${block.id}")
    val todo: Deque<Block> = ArrayDeque()
    todo.add(block)
    while (!todo.isEmpty()) {
      val currentblock = todo.removeFirst()
      currentblock.willFall = true
      val support = stack.blockSupport[currentblock]
      if (support != null) {
        support.supports.forEach {
          if (stack.blockSupport[it]!!.restsOn.all { it.willFall }) {
            //println("Block ${it.id} will fall" )
            if (!todo.contains(it))
              todo.add(it)
          }
        }
      } else {
        // println(currentblock)
      }
    }
    return stack.blocks.count { it.willFall } - 1 // don't count first removed block
  }

  fun claimPointsForBlock(block: Block, stack: Stack)
  {
    (block.left()..block.right()).forEach { x ->
      (block.front()..block.back()).forEach { y ->
        (block.bottom()..block.top()).forEach { z ->
          stack.space[x][y][z].block = block
          block.points.add(stack.space[x][y][z])
        }
      }
    }
  }

  fun parseBlock(id: Int, input: String): Block
  {
    val (start, end) = input.split('~')
    return Block(id, Point(start), Point(end))
  }

  data class Point(val x: Int, val y: Int, val z: Int)
  {

    constructor(data: List<Int>) : this(data[0], data[1], data[2])
    constructor(data: String) : this(data.split(',').map { it.toInt() })
  }

  data class Block(val id: Int, val start1: Point= Point(0,0,0), val start2: Point= Point(0,0,0))
  {

    var points: MutableList<ClaimedPoint> = mutableListOf()
    var willFall = false

    fun left(): Int =
      min(start1.x, start2.x)

    fun right(): Int =
      max(start1.x, start2.x)

    fun front(): Int =
      min(start1.y, start2.y)

    fun back(): Int =
      max(start1.y, start2.y)

    fun bottom(): Int =
      min(start1.z, start2.z)

    fun top(): Int =
      max(start1.z, start2.z)

    override fun equals(other: Any?): Boolean
    {
      if (this === other) return true
      if (other !is Block) return false

      if (id != other.id) return false

      return true
    }

    override fun hashCode(): Int
    {
      return id
    }

    override fun toString(): String {
      return "Block(id=$id, willFall=$willFall)"
    }


  }

  data class ClaimedPoint(val point: Point, var block: Block? = null)
  {
    override fun equals(other: Any?): Boolean
    {
      if (this === other) return true
      if (other !is ClaimedPoint) return false

      if (point != other.point) return false

      return true
    }

    override fun hashCode(): Int
    {
      return point.hashCode()
    }
  }

  data class BlockSupport(val supports: MutableList<Block> = mutableListOf(), val restsOn: MutableList<Block> = mutableListOf())
  data class Stack(val width: Int, val depth: Int, val height: Int)
  {
    val space: Array<Array<Array<ClaimedPoint>>> = Array(width) { x -> Array(depth) { y -> Array(height) { z -> ClaimedPoint(Point(x, y, z)) } } }

    val blocks: MutableList<Block> = mutableListOf()
    val blockSupport: MutableMap<Block, BlockSupport> = mutableMapOf()

    fun dropBlocks()
    {
      blocks.sortedBy { it.bottom() }.forEach { drop(it, space) }
    }

    fun drop(block: Block, space: Array<Array<Array<ClaimedPoint>>>)
    {
      var blockRests = false
      while (!blockRests)
      {
        block.points.forEach {
          if (it.point.z == 1 || space[it.point.x][it.point.y][it.point.z - 1].block ?: block != block)
          {
            blockRests = true
            if (it.point.z > 1 && space[it.point.x][it.point.y][it.point.z - 1].block != null)
            {
              var restingBlock = blockSupport[block]
              if (restingBlock == null) blockSupport.put(block, BlockSupport())
              restingBlock = blockSupport[block]
              restingBlock?.restsOn?.add(space[it.point.x][it.point.y][it.point.z - 1].block!!)

              var supportingBlock = blockSupport[space[it.point.x][it.point.y][it.point.z - 1].block]
              if (supportingBlock == null) blockSupport.put(space[it.point.x][it.point.y][it.point.z - 1].block!!, BlockSupport())
              supportingBlock = blockSupport[space[it.point.x][it.point.y][it.point.z - 1].block]
              supportingBlock?.supports?.add(block)
            }
          }
        }
        if (!blockRests)
        {
          val newPoints: MutableList<ClaimedPoint> = mutableListOf()
          block.points.forEach {
            space[it.point.x][it.point.y][it.point.z].block = null
            space[it.point.x][it.point.y][it.point.z - 1].block = block
            newPoints.add(space[it.point.x][it.point.y][it.point.z - 1])
          }
          block.points = newPoints
        }
      }

    }

    override fun toString(): String
    {
      val sb = StringBuilder("Stack(width=$width, depth=$depth, height=$height\n  space:\n")
      (0 until height).forEach { z ->
        (0 until width).forEach { y ->
          (0 until width).forEach { x ->
            sb.append(space[x][y][z].block?.id ?: " ")
          }
          sb.append("\n")
        }
        sb.append("\n\n")
      }
      return sb.toString()
    }

  }

  fun printBlockSupport(blockSupport: Map<Block, BlockSupport>)
  {
    blockSupport.forEach {
      print("${it.key.id} (points ")
      it.key.points.forEach {
        print("(${it.point.x},${it.point.y}, ${it.point.z}), ")
      }
      print(") supports ")
      it.value.supports.forEach { print("${it.id},") }
      println()
    }

    blockSupport.forEach {
      print("${it.key.id} (points ")
      it.key.points.forEach {
        print("(${it.point.x},${it.point.y}, ${it.point.z}), ")
      }
      print(") is supported by ")
      it.value.restsOn.forEach { print("${it.id},") }
      //   println()
    }

  }
}
