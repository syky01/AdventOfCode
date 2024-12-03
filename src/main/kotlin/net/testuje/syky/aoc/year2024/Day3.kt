package net.testuje.syky.aoc.year2024

import net.testuje.syky.aoc.Utils.Input

class Day3 {
    private val input = Input()

    fun run() {

        part1()
        part2()

        return
    }

    private fun part1() {
        val result = input.get(FILE).sumOf { line ->
            REGEX_FIRST.findAll(line).sumOf {
                it.groupValues[1].toInt() * it.groupValues[2].toInt()
            }
        }
        println("Part1: $result")
    }

    private fun part2() {
        var count = true
        val result = input.get(FILE).sumOf { line ->
            REGEX_SECOND.findAll(line).sumOf {
                when (it.groupValues[0]) {
                    "do()" -> {
                        count = true
                        0
                    }

                    "don't()" -> {
                        count = false
                        0
                    }

                    else -> {
                        when (count) {
                            true -> it.groupValues[1].toInt() * it.groupValues[2].toInt()
                            else -> 0
                        }
                    }
                }
            }
        }

        println("Part2: $result")
    }

    companion object {
        val REGEX_FIRST = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        val REGEX_SECOND = Regex("""mul\((\d{1,3}),(\d{1,3})\)|don't\(\)|do\(\)""")
        const val FILE = "/2024/input3_test"
    }
}