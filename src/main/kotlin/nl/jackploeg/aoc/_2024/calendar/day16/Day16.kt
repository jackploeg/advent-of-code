package nl.jackploeg.aoc._2024.calendar.day16

import nl.jackploeg.aoc.grid.*
import java.util.*
import javax.inject.Inject

class Day16 @Inject constructor() {
    fun partOne(filename: String): Int {
        val grid = CharacterGrid.fromFile(filename)
        val start = CellWithDirection(grid.filter { it.value == 'S' }.map { it.key }.first(), Direction.EAST)
        val end = CellWithDirection(grid.filter { it.value == 'E' }.map { it.key }.first(), Direction.EAST)

        grid.setValue(start.cell, '.')
        grid.setValue(end.cell, '.')

        val paths = pathsFromStartToEnd(grid, start, end)
            .filter { it.key.cell == end.cell }

        return paths.map { it.value }.minOrNull() ?: -1
    }

    fun partTwo(filename: String): Int {
        val grid = CharacterGrid.fromFile(filename)
        val start = grid.entries.first { it.value == 'S' }.key
        val end = grid.entries.first { it.value == 'E' }.key

        data class Path(
            val cellWithDirection: CellWithDirection,
            val cost: Int,
            val previous: List<CellWithDirection>
        )

        val visited = mutableSetOf<CellWithDirection>()
        val unvisited = PriorityQueue<Path>(compareBy(Path::cost))
        unvisited.add(Path(CellWithDirection(start, Direction.EAST), 0, emptyList()))
        val bestCells = mutableSetOf(end)
        var bestCost = Int.MAX_VALUE
        while (unvisited.isNotEmpty()) {
            val (current, cost, previous) = unvisited.remove().also { visited.add(it.cellWithDirection) }
            if (current.cell == end) {
                if (cost <= bestCost) {
                    bestCost = cost
                    bestCells += previous.map { it.cell }
                } else break
            }
            unvisited += listOf(
                Path(
                    CellWithDirection(current.cell.left(current.direction), current.direction.turnLeft()),
                    cost + 1001,
                    previous + current
                ),
                Path(
                    CellWithDirection(current.cell.right(current.direction), current.direction.turnRight()),
                    cost + 1001,
                    previous + current
                ),
                Path(
                    CellWithDirection(current.cell.straightAhead(current.direction), current.direction),
                    cost + 1,
                    previous + current
                ),
            ).filter { it.cellWithDirection !in visited && grid[it.cellWithDirection.cell] != '#' }
        }
        return bestCells.size
    }

    private fun pathsFromStartToEnd(
        grid: CharacterGrid,
        start: CellWithDirection,
        end: CellWithDirection
    ): Map<CellWithDirection, Int> {
        val solver = ReindeerMazeDijkstra(grid) { map, cellWithDirection ->
            val directionsToTravel = cellWithDirection.cell.neighboursWithDirection().filter { grid[it.cell] != '#' }
            directionsToTravel.map { neigbour ->
                neigbour to 1 + if (neigbour.direction == cellWithDirection.direction) 0 else 1000
            }
        }

        val paths = solver.solve(start, end)
        return paths
    }

}
