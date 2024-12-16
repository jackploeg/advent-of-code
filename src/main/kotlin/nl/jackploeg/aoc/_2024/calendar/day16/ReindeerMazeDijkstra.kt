package nl.jackploeg.aoc._2024.calendar.day16

import nl.jackploeg.aoc.grid.CellWithDirection
import nl.jackploeg.aoc.grid.CharacterGrid
import nl.jackploeg.aoc.pathing.Dijkstra
import nl.jackploeg.aoc.pathing.DijkstraNodeWithCost

class ReindeerMazeDijkstra(
    val grid: CharacterGrid,
    val neighborsGenerator: (CharacterGrid, CellWithDirection) -> List<Pair<CellWithDirection, Int>>
) : Dijkstra<CellWithDirection, Int, ReindeerMazeDijkstra.CellWithCost> {
    override fun Int.plus(cost: Int) = this + cost

    override fun CellWithDirection.withCost(cost: Int) = CellWithCost(this, cost)

    override fun minCost(): Int = 0

    override fun maxCost(): Int = Int.MAX_VALUE

    inner class CellWithCost(val node: CellWithDirection, private val cost: Int) :
        DijkstraNodeWithCost<CellWithDirection, Int> {
        override fun neighbors() = neighborsGenerator(grid, node).map { CellWithCost(it.first, it.second) }

        override fun node(): CellWithDirection = node

        override fun cost(): Int = cost

        override fun compareTo(other: DijkstraNodeWithCost<CellWithDirection, Int>): Int = cost.compareTo(other.cost())
    }
}
