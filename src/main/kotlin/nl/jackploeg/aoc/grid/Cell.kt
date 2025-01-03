package nl.jackploeg.aoc.grid

typealias Cell = Pair<Int, Int>

val Cell.row
    get() = first
val Cell.col
    get() = second

val NORTH = Cell(-1, 0)
val SOUTH = Cell(1, 0)
val EAST = Cell(0, 1)
val WEST = Cell(0, -1)
val NORTH_EAST = Cell(-1, 1)
val NORTH_WEST = Cell(-1, -1)
val SOUTH_EAST = Cell(1, 1)
val SOUTH_WEST = Cell(1, -1)

operator fun Cell.plus(other: Cell) = Cell(row + other.row, col + other.col)
operator fun Cell.minus(other: Cell) = Cell(row - other.row, col - other.col)
operator fun Cell.times(scalar: Int) = Cell(row * scalar, col * scalar)

val NEIGHBOURS = arrayOf(NORTH, SOUTH, EAST, WEST)
val SURROUNDINGS = arrayOf(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST)

fun Cell.neighbours() = NEIGHBOURS.map { this + it }
fun Cell.surroundings() = SURROUNDINGS.map { this + it }
fun Cell.neighboursWithDirection() = listOf(
    CellWithDirection(this.north(), Direction.NORTH ),
    CellWithDirection(this.south(), Direction.SOUTH ),
    CellWithDirection(this.east(), Direction.EAST ),
    CellWithDirection(this.west(), Direction.WEST )
)

fun Cell.north() = this + NORTH
fun Cell.south() = this + SOUTH
fun Cell.east() = this + EAST
fun Cell.west() = this + WEST
fun Cell.northEast() = this + NORTH_EAST
fun Cell.northWest() = this + NORTH_WEST
fun Cell.southEast() = this + SOUTH_EAST
fun Cell.southWest() = this + SOUTH_WEST

fun Cell.left(direction: Direction): Cell = when (direction) {
    Direction.NORTH -> west()
    Direction.SOUTH -> east()
    Direction.EAST -> north()
    Direction.WEST -> south()
}
fun Cell.right(direction: Direction): Cell = when (direction) {
    Direction.NORTH -> east()
    Direction.SOUTH -> west()
    Direction.EAST -> south()
    Direction.WEST -> north()
}
fun Cell.reverse(direction: Direction): Cell = when (direction) {
    Direction.NORTH -> south()
    Direction.SOUTH -> north()
    Direction.EAST -> west()
    Direction.WEST -> east()
}
fun Cell.straightAhead(direction: Direction): Cell = when (direction) {
    Direction.NORTH -> north()
    Direction.SOUTH -> south()
    Direction.EAST -> east()
    Direction.WEST -> west()
}
fun Cell.manhattanDistance(other: Cell) = Math.abs(row - other.row) + Math.abs(col - other.col)

fun Cell.getAllOnLine(rowDistance: Int, colDistance: Int, count: Int) =
    (0..count).map { Cell(this.row + it*rowDistance, this.col + it * colDistance) }

data class CellWithDirection(val cell: Cell, val direction: Direction)
