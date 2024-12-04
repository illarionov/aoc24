fun main() = Day06.main()

object Day06 {
    fun main() {
        val testInput = readInput("Day06_test")
        val input = readInput("Day06")

        part1(testInput).also { println("Part 1, test input: $it"); check(it == 1) }
        part1(input).also { println("Part 1, real input: $it"); /* check(it == 2) */ }

        part2(testInput).also { println("Part 2, test input: $it"); /* check(it == 2) */ }
        part2(input).also { println("Part 2, real input: $it"); /* check(it == 2) */ }
    }

    private fun part1(input: List<String>): Int {
        return input.size
    }

    private fun part2(input: List<String>): Int {
        return input.size
    }
}
