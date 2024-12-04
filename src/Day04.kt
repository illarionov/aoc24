fun main() = Day04.main()

object Day04 {
    fun main() {
        val testInput = readInput("Day04_test").toPositions().also { it.println() }
        val input = readInput("Day04").toPositions()

        part1(testInput).also { println("Part 1, test input: $it"); check(it == 18) }
        part1(input).also { println("Part 1, real input: $it"); check(it == 2554) }

        part2(testInput).also { println("Part 2, test input: $it"); check(it == 9) }
        part2(input).also { println("Part 2, real input: $it"); /* check(it == 1916) */ }
    }

    private fun List<String>.toPositions(): Map<RowCol, Char> = flatMapIndexed { row: Int, s: String ->
        s.mapIndexed { column, char -> RowCol(row, column) to char }
    }.toMap()

    private fun part1(input: Map<RowCol, Char>): Int {
        val maxRow = input.maxOf { it.key.row }
        val maxColumn = input.maxOf { it.key.column }
        return (0..maxRow).sumOf { row ->
            (0..maxColumn).sumOf { column ->
                input.xmasCount(RowCol(row, column))
            }
        }
    }

    private fun Map<RowCol, Char>.xmasCount(pos: RowCol): Int = listOf(
        xmasChars(pos, 0, 1),
        xmasChars(pos, 0, -1),
        xmasChars(pos, -1, -1),
        xmasChars(pos, -1, 0),
        xmasChars(pos, -1, 1),
        xmasChars(pos, 1, -1),
        xmasChars(pos, 1, 0),
        xmasChars(pos, 1, 1)
    ).count { it == "XMAS" }

    private fun part2(input: Map<RowCol, Char>): Int {
        val maxRow = input.maxOf { it.key.row }
        val maxColumn = input.maxOf { it.key.column }
        return (0..maxRow).sumOf { row ->
            (0..maxColumn).count { column ->
                input.isxmas2(RowCol(row, column))
            }
        }
    }

    private fun Map<RowCol, Char>.isxmas2(pos: RowCol): Boolean {
        if (this[pos] != 'A') {
            return false
        }
        val str = listOf(
            pos.adj( - 1,  - 1),
            pos.adj( - 1,  + 1),
            pos.adj( + 1,  - 1),
            pos.adj( + 1,  + 1),
        ).joinToString("") { this[it]?.toString() ?: " " }
        return (str == "MSMS" || str == "SMSM" || str == "SSMM" || str == "MMSS")
    }

    private data class RowCol(val row: Int, val column: Int) {}
    private fun RowCol.adj(dRow: Int = 0, dColumn: Int = 0): RowCol = RowCol(row = this.row + dRow, column = this.column + dColumn)

    private fun Map<RowCol, Char>.xmasChars(pos: RowCol, dRow: Int, dColumn: Int): String = (0..3).joinToString("") {
        val newPos = pos.adj(dRow * it, dColumn * it)
        this[newPos]?.toString() ?: " "
    }
}
