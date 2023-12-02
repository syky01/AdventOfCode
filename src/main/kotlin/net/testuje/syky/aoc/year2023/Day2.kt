package net.testuje.syky.aoc.year2023

import net.testuje.syky.aoc.Utils.Input


class Day2 {
    private val input = Input()

    fun run() {

        part1()
        part2()

        return
    }

    private fun part1() {

        val result = input.get(FILE).sumOf { line ->
            val game = parseGame(line)
            game.cubesMax.forEach { (color, max) ->
                if (MAX_ALLOWED_CUBES[color]!! < max) {
                    return@sumOf 0
                }
            }
            game.id.toInt()
        }

        println("Part1: $result")

    }

    private fun part2() {
        val result = input.get(FILE).sumOf { line ->
            var power = 1
            val game = parseGame(line)
            game.cubesMax.values.map { power *= it }
            power
        }

        println("Part2: $result")

    }

    private fun parseGame(input: String): Game {
        val data = input.split(": ")
        val gameId = data[0].split(" ")[1]
        val cubesMax = mutableMapOf(Color.RED to 0, Color.GREEN to 0, Color.BLUE to 0)
        val game = Game(gameId, cubesMax.toMutableMap())

        data[1].split("; ").forEach { draw ->
            draw.split(", ").forEach { color ->
                val details = color.split(" ")
                val c = Color.valueOf(details[1].uppercase())
                if (game.cubesMax[c]!! < details[0].toInt()) {
                    game.cubesMax[c] = details[0].toInt()
                }
            }
        }
        return game
    }

    companion object {
        const val FILE = "/2023/input2_test"
        val MAX_ALLOWED_CUBES = mutableMapOf(Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14)

        class Game(val id: String, val cubesMax: MutableMap<Color, Int>)

        enum class Color {
            RED,
            GREEN,
            BLUE
        }
    }
}