fun main() = Day06.main()

object Day06 {
    fun main() {
        val testInput = readInput("Day06_test").toField()
        val input = readInput("Day06").toField()

        part1(testInput).also { println("Part 1, test input: $it"); check(it == 41) }
        part1(input).also { println("Part 1, real input: $it"); check(it == 5329) }

        part2(testInput).also { println("Part 2, test input: $it"); check(it == 6) }
        part2(input).also { println("Part 2, real input: $it"); check(it == 130) }
    }

    private class Input(
        val field: List<List<Char>>,
        val guardPosition: Pair<Int, Int>,
        val guardDirection: Char
    )

    private fun List<String>.toField(): Input {
        val field = this.map { it.toList() }

        val lastRow = field.lastIndex
        val lastColumn = field[0].lastIndex

        val guardPosition = (0..lastRow).firstNotNullOf { row ->
            (0..lastColumn).firstNotNullOfOrNull { column ->
                when (field[row][column]) {
                    '^', '>', 'v', '<' -> row to column
                    else -> null
                }
            }
        }
        val guardDirection = field[guardPosition.first][guardPosition.second]
        val newField = field.map { it.map { if (it in setOf('^', '>', 'v', '<')) '.' else it } }
        return Input(newField, guardPosition, guardDirection)
    }

    private fun part1(
        input: Input
    ): Int {
        var (guardRow, guardColumn) = input.guardPosition
        var guardDirection = input.guardDirection

        val lastRow = input.field.lastIndex
        val lastColumn = input.field[0].lastIndex

        val positions: MutableSet<Pair<Int, Int>> = mutableSetOf()
        while (guardRow in 0..lastRow && guardColumn in 0..lastColumn) {
            positions += guardRow to guardColumn
            when (guardDirection) {
                '^' -> {
                    if (guardRow > 0 && input.field[guardRow - 1][guardColumn] != '.') {
                        guardDirection = '>'
                    } else {
                        guardRow -= 1
                    }
                }

                '>' -> {
                    if (guardColumn < lastColumn && input.field[guardRow][guardColumn + 1] != '.') {
                        guardDirection = 'v'
                    } else {
                        guardColumn += 1
                    }
                }

                'v' -> {
                    if (guardRow < lastRow && input.field[guardRow + 1][guardColumn] != '.') {
                        guardDirection = '<'
                    } else {
                        guardRow += 1
                    }
                }

                '<' -> {
                    if (guardColumn > 0 && input.field[guardRow][guardColumn - 1] != '.') {
                        guardDirection = '^'
                    } else {
                        guardColumn -= 1
                    }
                }
            }
        }
        return positions.size
    }

    private fun part2(input: Input): Int {
        val lastRow = input.field.lastIndex
        val lastColumn = input.field[0].lastIndex
        return (0..lastRow).sumOf { row ->
            val sum: Int = (0..lastColumn).sumOf { column: Int ->

                if (
                    input.field[row][column] == '.'
                    && row to column != input.guardPosition
                    && isLoop(input, row to column)
                ) {
                    1.toInt()
                } else {
                    0
                }
            }
            sum
        }
    }

    private fun isLoop(input: Input, obstaclePosition: Pair<Int, Int>): Boolean {
        val field = input.field.mapIndexed { row, chars ->
            chars.mapIndexed { column, c ->
                if (row to column == obstaclePosition) {
                    '#'
                } else {
                    c
                }
            }
        }
        var (guardRow, guardColumn) = input.guardPosition
        var guardDirection = input.guardDirection

        val lastRow = input.field.lastIndex
        val lastColumn = input.field[0].lastIndex

        val positions: MutableSet<Triple<Int, Int, Char>> = mutableSetOf()
        while (guardRow in 0..lastRow && guardColumn in 0..lastColumn) {
            val newPosition = Triple(guardRow, guardColumn, guardDirection)
            if (newPosition in positions) {
                return true
            }
            positions += newPosition
            when (guardDirection) {
                '^' -> {
                    if (guardRow > 0 && field[guardRow - 1][guardColumn] != '.') {
                        guardDirection = '>'
                    } else {
                        guardRow -= 1
                    }
                }

                '>' -> {
                    if (guardColumn < lastColumn && field[guardRow][guardColumn + 1] != '.') {
                        guardDirection = 'v'
                    } else {
                        guardColumn += 1
                    }
                }

                'v' -> {
                    if (guardRow < lastRow && field[guardRow + 1][guardColumn] != '.') {
                        guardDirection = '<'
                    } else {
                        guardRow += 1
                    }
                }

                '<' -> {
                    if (guardColumn > 0 && field[guardRow][guardColumn - 1] != '.') {
                        guardDirection = '^'
                    } else {
                        guardColumn -= 1
                    }
                }
            }
        }
        return false
    }
}
