fun main() = Day03.main()

object Day03 {
    fun main() {
        val testInput = readInput("Day03_test").joinToString(" ")
        val input = readInput("Day03").joinToString(" ")

        part1(testInput).also { println("Part 1, test input: $it"); check(it == 161L) }
        part1(input).also { println("Part 1, real input: $it"); check(it == 173785482L) }

        part2(input).also { println("Part 2, real input: $it"); /* check(it == 2) */ }
    }

    private val mulRegex = """mul\(([0-9]{1,3})\,([0-9]{1,3})\)""".toRegex()
    private val mulRegexDoDont = """(mul\(([0-9]{1,3})\,([0-9]{1,3})\))|(do\(\))|(don't\(\))""".toRegex()

    private fun part1(input: String): Long {
        return mulRegex.findAll(input).toList().sumOf {
            it.groupValues.drop(1).map { mr -> mr.toLong() }.fold(1L) { acc, c -> acc * c }.toLong()
        }
    }

    private fun part2(input: String): Long {
        var apply = true
        return mulRegexDoDont.findAll(input).toList().sumOf {
            when (it.value) {
                "do()" -> {
                    apply = true
                    0L
                }

                "don't()" -> {
                    apply = false
                    0L
                }

                else -> if (apply) {
                    it.groupValues[2].toLong() * it.groupValues[3].toLong()
                } else {
                    0L
                }
            }
        }
    }
}
