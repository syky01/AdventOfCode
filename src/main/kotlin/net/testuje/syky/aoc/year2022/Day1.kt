package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input

class Day1 {
    private val input = Input()

    fun run() {
        //Last line of file must be empty
        val elves = mutableSetOf<Int>()
        var calories = 0
        input.get(FILE).forEach { line ->
            if(line.trim().isEmpty()) {
                elves.add(calories)
                calories = 0
                return@forEach
            }
            calories += line.toInt()
        }
        part1(elves)
        part2(elves.sorted())

        return
    }

    private fun part1(elves: Set<Int>){
        println("Part1: ${elves.max()}")
    }

    private fun part2(elves: List<Int>) {
        println("Part 2: ${elves.takeLast(3).sum()}")

    }

    companion object{
        const val FILE = "/2022/input1"
    }
}