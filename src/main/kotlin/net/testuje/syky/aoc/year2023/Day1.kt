package net.testuje.syky.aoc.year2023

import net.testuje.syky.aoc.Utils.Input

class Day1 {
    private val input = Input()

    fun run() {
        part1()
        part2()

        return
    }

    private fun part1(){

        val result = input.get(FILE).sumOf {line ->
            val first = line.find { it.isDigit()}
            val last = line.findLast { it.isDigit()}

            first!!.digitToInt()*10 + last!!.digitToInt()
        }

        println("Part1: $result")

    }

    private fun part2() {
        val result = input.get(FILE).sumOf { line ->
            val first = line.findAnyOf(numbers)!!.second
            val last = line.findLastAnyOf(numbers)!!.second

            //Thanks to extra "zero" and "0" in numbers list, we can use this
            numbers.indexOf(first)%10*10 + numbers.indexOf(last)%10
        }

        println("Part2: $result")

    }

    companion object{
        const val FILE = "/2023/input1"
        //In my input there are noo any "zero" or "0", so it doesn't affect the result
        val numbers = listOf(
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
        )
    }
}