import kotlin.math.abs

fun main() = Day02.main()

object Day02 {
    fun main() {
        val testInput = readInput("Day02_test")
        val input = readInput("Day02")

        part1(testInput).also { println("Part 1, test input: $it"); check(it == 2) }
        part1(input).also { println("Part 2, real input: $it"); /* check(it == 2) */ }

        part2(testInput).also { println("Part 2, test input: $it"); /* check(it == 2) */ }
        part2(input).also { println("Part 2, real input: $it"); /* check(it == 2) */ }
    }

    private fun part1(input: List<String>): Int {
        return input.map {
            it.split(Regex("\\s+")).map { it.toInt() }
        }.count { it.isSafe() }
    }

    private fun List<Int>.isSafe(): Boolean {
        val isIncreasing = this[0] < this[1]
        return this.zipWithNext { a, b ->
            (a < b == isIncreasing && abs(a - b) in (1..3))
        }.all { it }
    }

    private fun part2(input: List<String>): Int {
        return input.map {
            it.split(Regex("\\s+")).map { it.toInt() }
        }.count { it.isSafeWithSingleBadLevel() }
    }

    private fun List<Int>.isSafeWithSingleBadLevel(): Boolean {
        if (isSafe()) return true
        return this.indices.any { removedIndex ->
            val sublist = this.filterIndexed { index, _ -> removedIndex != index }
            sublist.isSafe()
        }
    }
}