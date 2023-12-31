package nl.jackploeg.aoc._2023.calendar.day10

import nl.jackploeg.aoc.utilities.Direction
import nl.jackploeg.aoc.utilities.readStringFile
import javax.inject.Inject

class Day10 @Inject constructor() {
    // find firthest part from start
    fun partOne(fileName: String): Int {
        val lines = readStringFile(fileName)
        val maze: List<List<Char>> = lines.map { it.toList() }
        val loop: MutableList<Cell> = mutableListOf()
        val row = maze.first { chars -> chars.contains('S') }
        val startY = maze.indexOf(row)
        val startX = row.indexOf('S')
        val startCell: Cell = determineStartCell(maze, startX, startY)
        loop.add(startCell)
        findLoop(maze, loop)
        return loop.size / 2
    }

    // count enclosed cells
    fun partTwo(fileName: String): Int {
        val lines = readStringFile(fileName)
        val maze: List<List<Char>> = lines.map { it.toList() }
        val loop: MutableList<Cell> = mutableListOf()
        val row = maze.first { chars -> chars.contains('S') }
        val startY = maze.indexOf(row)
        val startX = row.indexOf('S')
        val startCell: Cell = determineStartCell(maze, startX, startY)
        loop.add(startCell)

        findLoop(maze, loop)
        val lastCell = loop.last()
        loop[0].comingFromX = lastCell.x
        loop[0].comingFromY = lastCell.y

        val loopCells: MutableSet<Pair<Int, Int>> = mutableSetOf()
        val enclosedCells: MutableSet<Pair<Int, Int>> = mutableSetOf()
        loop.forEach { loopCells.add(Pair(it.x, it.y)) }

        // find leftmost vertical cell
        val markingStartCell = loop.filter { it.cellType == CellType.VERT }.minBy { it.x }
        var direction = Direction.UP
        if (markingStartCell.y + 1 == markingStartCell.comingFromY) {
            direction = Direction.DOWN
        }
        // from here we will loop through the loop in reverse direction,
        // if we start going up enclosed cells are to our right,
        // if we start going down the enclosed cells are to our left
        val enclosedCellsDirection = if (direction == Direction.UP) Direction.RIGHT else Direction.LEFT

        // loop the loop, marking all cells to the right or left as enclosed, up to next cell that is part of the loop
        markAdjacentCellsAsEnclosed(markingStartCell, direction, enclosedCellsDirection, loopCells, maze, enclosedCells)
        var nextCellAndDirection = findNextCellInLoopAndNewDirection(markingStartCell, loop, direction)
        var currentCell = nextCellAndDirection.first
        direction = nextCellAndDirection.second
        while (currentCell != markingStartCell) {
            markAdjacentCellsAsEnclosed(
                currentCell,
                direction,
                enclosedCellsDirection,
                loopCells,
                maze,
                enclosedCells
            )
            nextCellAndDirection = findNextCellInLoopAndNewDirection(currentCell, loop, direction)
            currentCell = nextCellAndDirection.first
            direction = nextCellAndDirection.second
        }

        return enclosedCells.size

    }

    fun findNextCellInLoopAndNewDirection(
        currentCell: Cell,
        loop: MutableList<Cell>,
        direction: Direction
    ): Pair<Cell, Direction> {
        val nextCell = loop.first { it.x == currentCell.comingFromX && it.y == currentCell.comingFromY }
        val nextDirection: Direction = when (direction) {
            Direction.UP ->
                when (nextCell.cellType) {
                    CellType.VERT -> Direction.UP
                    CellType.TR -> Direction.LEFT
                    CellType.TL -> Direction.RIGHT
                    else -> throw RuntimeException("Incorrect direction")
                }

            Direction.DOWN ->
                when (nextCell.cellType) {
                    CellType.VERT -> Direction.DOWN
                    CellType.BL -> Direction.RIGHT
                    CellType.BR -> Direction.LEFT
                    else -> throw RuntimeException("Incorrect direction")
                }

            Direction.RIGHT ->
                when (nextCell.cellType) {
                    CellType.HOR -> Direction.RIGHT
                    CellType.TR -> Direction.DOWN
                    CellType.BR -> Direction.UP
                    else -> throw RuntimeException("Incorrect direction")
                }

            Direction.LEFT ->
                when (nextCell.cellType) {
                    CellType.HOR -> Direction.LEFT
                    CellType.BL -> Direction.UP
                    CellType.TL -> Direction.DOWN
                    else -> throw RuntimeException("Incorrect direction")
                }
        }
        return Pair(nextCell, nextDirection)
    }

    fun markAdjacentCellsAsEnclosed(
        currentCell: Cell,
        direction: Direction,
        enclosedCellsDirection: Direction,
        loopCells: MutableSet<Pair<Int, Int>>,
        maze: List<List<Char>>,
        enclosedCells: MutableSet<Pair<Int, Int>>
    ) {
        val mazeWidth = maze.first().size
        if (enclosedCellsDirection == Direction.RIGHT
            && ((currentCell.cellType == CellType.VERT && direction == Direction.UP)
                    || (currentCell.cellType == CellType.TR && direction == Direction.LEFT)
                    || (currentCell.cellType == CellType.BR && direction == Direction.UP))
        )
            if (currentCell.x < mazeWidth) {
                var cellToCheck = Pair(currentCell.x + 1, currentCell.y)
                while (!loopCells.contains(cellToCheck) && cellToCheck.first < mazeWidth - 1) {
                    enclosedCells.add(cellToCheck)
                    cellToCheck = Pair(cellToCheck.first + 1, currentCell.y)
                }
            }

        if (enclosedCellsDirection == Direction.LEFT
            && ((currentCell.cellType == CellType.VERT && direction == Direction.UP)
                    || (currentCell.cellType == CellType.TR && direction == Direction.RIGHT)
                    || (currentCell.cellType == CellType.BR && direction == Direction.UP))
        )
            if (currentCell.x > 0) {
                var cellToCheck = Pair(currentCell.x - 1, currentCell.y)
                while (!loopCells.contains(cellToCheck) && cellToCheck.first >= 0) {
                    enclosedCells.add(cellToCheck)
                    cellToCheck = Pair(cellToCheck.first - 1, currentCell.y)
                }
            }


        if (enclosedCellsDirection == Direction.LEFT
            && ((currentCell.cellType == CellType.VERT && direction == Direction.UP)
                    || (currentCell.cellType == CellType.BL && direction == Direction.UP)
                    || (currentCell.cellType == CellType.TL && direction == Direction.RIGHT))
        )
            if (currentCell.x > 0) {
                var cellToCheck = Pair(currentCell.x - 1, currentCell.y)
                while (!loopCells.contains(cellToCheck) && cellToCheck.first >= 0) {
                    enclosedCells.add(cellToCheck)
                    cellToCheck = Pair(cellToCheck.first - 1, currentCell.y)
                }
            }
        if (enclosedCellsDirection == Direction.RIGHT
            && ((currentCell.cellType == CellType.VERT && direction == Direction.DOWN)
                    || (currentCell.cellType == CellType.TR && direction == Direction.DOWN)
                    || (currentCell.cellType == CellType.BR && direction == Direction.LEFT))
        )
            if (currentCell.x > 0) {
                var cellToCheck = Pair(currentCell.x - 1, currentCell.y)
                while (!loopCells.contains(cellToCheck) && cellToCheck.first >= 0) {
                    enclosedCells.add(cellToCheck)
                    cellToCheck = Pair(cellToCheck.first - 1, currentCell.y)
                }
            }
    }

    fun findLoop(maze: List<List<Char>>, loop: MutableList<Cell>) {
        val startCell = loop[0]
        var nextCell = determineNextCell(startCell, maze)
        while (nextCell.cellType != CellType.START) {
            loop.add(nextCell)
            nextCell = determineNextCell(nextCell, maze)
        }
    }

    fun determineNextCell(currentCell: Cell, maze: List<List<Char>>): Cell {
        var newX: Int = -1
        var newY: Int = -1
        when (currentCell.cellType) {
            CellType.HOR -> if (currentCell.comingFromX != currentCell.x - 1) {
                newX = currentCell.x - 1
                newY = currentCell.y
            } else {
                newX = currentCell.x + 1
                newY = currentCell.y
            }

            CellType.VERT -> if (currentCell.comingFromY != currentCell.y - 1) {
                newX = currentCell.x
                newY = currentCell.y - 1
            } else {
                newX = currentCell.x
                newY = currentCell.y + 1
            }

            CellType.TL -> if (currentCell.comingFromX != currentCell.x + 1) {
                newX = currentCell.x + 1
                newY = currentCell.y
            } else {
                newX = currentCell.x
                newY = currentCell.y + 1
            }

            CellType.TR -> if (currentCell.comingFromX != currentCell.x - 1) {
                newX = currentCell.x - 1
                newY = currentCell.y
            } else {
                newX = currentCell.x
                newY = currentCell.y + 1
            }

            CellType.BR -> if (currentCell.comingFromY != currentCell.y - 1) {
                newX = currentCell.x
                newY = currentCell.y - 1
            } else {
                newX = currentCell.x - 1
                newY = currentCell.y
            }

            CellType.BL -> if (currentCell.comingFromX != currentCell.x + 1) {
                newX = currentCell.x + 1
                newY = currentCell.y
            } else {
                newX = currentCell.x
                newY = currentCell.y - 1
            }

            CellType.START -> {
                newX = 0
                newY = 0
            }

        }
        return Cell(newX, newY, maze[newY][newX].toCellType(), currentCell.x, currentCell.y)
    }

    fun determineStartCell(maze: List<List<Char>>, startX: Int, startY: Int): Cell {
        var connectsLeft = false
        var connectsRight = false
        var connectsTop = false
        var connectsBottom = false
        if (startX > 0 && maze[startY][startX - 1].connectsRight())
            connectsLeft = true
        if (startX < maze[startY].size - 1 && maze[startY][startX + 1].connectsLeft())
            connectsRight = true
        if (startY > 0 && maze[startY - 1][startX].connectsBottom())
            connectsTop = true
        if (startY < maze.size - 1 && maze[startY + 1][startX].connectsTop())
            connectsBottom = true

        return when {
            connectsLeft && connectsRight -> Cell(startX, startY, CellType.HOR, 0, 0)
            connectsLeft && connectsBottom -> Cell(startX, startY, CellType.TR, 0, 0)
            connectsLeft && connectsTop -> Cell(startX, startY, CellType.BR, 0, 0)
            connectsRight && connectsBottom -> Cell(startX, startY, CellType.TL, 0, 0)
            connectsRight && connectsTop -> Cell(startX, startY, CellType.BL, 0, 0)
            connectsTop && connectsBottom -> Cell(startX, startY, CellType.VERT, 0, 0)
            else -> throw RuntimeException("Not able to determine start node type")
        }
    }

    fun Char.connectsRight(): Boolean =
        this == CellType.HOR.symbol || this == CellType.TL.symbol || this == CellType.BL.symbol

    fun Char.connectsTop(): Boolean =
        this == CellType.VERT.symbol || this == CellType.BL.symbol || this == CellType.BR.symbol

    fun Char.connectsLeft(): Boolean =
        this == CellType.HOR.symbol || this == CellType.TR.symbol || this == CellType.BR.symbol

    fun Char.connectsBottom(): Boolean =
        this == CellType.VERT.symbol || this == CellType.TL.symbol || this == CellType.TR.symbol

    fun Char.toCellType(): CellType =
        when (this) {
            '-' -> CellType.HOR
            '|' -> CellType.VERT
            'F' -> CellType.TL
            '7' -> CellType.TR
            'L' -> CellType.BL
            'J' -> CellType.BR
            'S' -> CellType.START
            else -> throw RuntimeException("Not a Celltype")
        }

    data class Cell(val x: Int, val y: Int, var cellType: CellType, var comingFromX: Int, var comingFromY: Int) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Cell

            if (x != other.x) return false
            if (y != other.y) return false
            if (cellType != other.cellType) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            result = 31 * result + cellType.hashCode()
            return result
        }
    }

    enum class CellType(val symbol: Char) {
        HOR('-'),
        VERT('|'),
        TL('F'),
        TR('7'),
        BR('J'),
        BL('L'),
        START('S')
    }

}