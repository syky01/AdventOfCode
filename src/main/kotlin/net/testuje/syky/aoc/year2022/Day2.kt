package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input
import net.testuje.syky.aoc.year2022.Day2.Companion.Score.*

class Day2 {
    private val input = Input()
    fun run() {
        part1()
        part2()

        return
    }

    private fun part1() {
        var points = 0
        input.get(FILE).forEach { line ->
            val data = line.split(" ")
            when (data[0]) {
                "A" -> { //ROCK
                    points += when (data[1]) {
                        "X" -> DRAW.points + ROCK.points
                        "Y" -> WIN.points + PAPER.points
                        else -> SCISSORS.points
                    }
                }

                "B" -> { //PAPER
                    points += when (data[1]) {
                        "X" -> ROCK.points
                        "Y" -> PAPER.points + DRAW.points
                        else -> SCISSORS.points + WIN.points
                    }
                }

                "C" -> { //SCISSORS
                    points += when (data[1]) {
                        "X" -> ROCK.points + WIN.points
                        "Y" -> PAPER.points
                        else -> SCISSORS.points + DRAW.points
                    }
                }
            }
        }
        println("Par12: $points")
    }

    /**
     * A -> ROCK
     * B -> PAPER
     * C -> SCISSORS
     *
     * X -> LOSE
     * Y -> DRAW
     * Z -> WIN
     */
    private fun part2() {
        var points = 0
        input.get(FILE).forEach { input ->
            val data = input.split(" ")

            points +=
                when (data[1]) {
                    "X" -> {
                        when (data[0]) {
                            "A" -> SCISSORS.points
                            "B" -> ROCK.points
                            else -> PAPER.points
                        }
                    }

                    "Y" -> {
                        DRAW.points +
                                when (data[0]) {
                                    "A" -> ROCK.points
                                    "B" -> PAPER.points
                                    else -> SCISSORS.points
                                }
                    }

                    else -> {
                        WIN.points +
                                when (data[0]) {
                                    "A" -> PAPER.points
                                    "B" -> SCISSORS.points
                                    else -> ROCK.points
                                }
                    }
                }

        }
        println("Part2: $points")

    }

    companion object {
        const val FILE = "/2022/input2"

        enum class Score(val points: Int) {
            ROCK(1),
            PAPER(2),
            SCISSORS(3),
            DRAW(3),
            WIN(6)
        }
    }
}