package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input

class Day3 {
    private val input = Input()
    fun run() {
        part1()
        part2()

        return
    }

    private fun part1() {
        var points = 0
        input.get(FILE).forEach { line ->
            val length = line.length / 2
            val part1 = line.drop(length).toCharArray().toSet()
            val part2 = line.dropLast(length).toCharArray().toSet()
            val char = part1.filter(part2::contains)[0]
            points += charScore(char)
        }
        println("Part1: $points")

    }

    private fun part2() {
        var points = 0
        var i = 0
        val inputData = mutableListOf<String>()
        input.get(FILE).forEach { line ->
            inputData.add(line)
            if (++i == 3) {
                points += calculatePoints(inputData)
                inputData.clear()
                i = 0
            }

        }
        println("Part2: $points")

    }

    private fun charScore(char: Char): Int {
        return when {
            char.isLowerCase() -> char.code - 96
            else -> char.code - 38
        }
    }

    private fun calculatePoints(input: List<String>): Int {
        val char = input[0].filter(input[1]::contains).filter(input[2]::contains)[0]
        return charScore(char)
    }


    companion object {
        const val FILE = "/2022/input3"
        const val FILE_TEST = "/2022/input3-test"
    }
}