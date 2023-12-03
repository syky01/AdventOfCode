package net.testuje.syky.aoc.year2023

import net.testuje.syky.aoc.Utils.Input

class Day3 {
    private val input = Input()

    fun run() {
        val parts = findParts()

        part1(parts)
        part2(parts)

        return
    }

    private fun part1(parts: Map<Pair<Int, Int>, Part>) {

        val result = parts.map { it.value.partNumbers.sum() }.sum()
        println("Part1: $result")

    }

    private fun part2(parts: Map<Pair<Int, Int>, Part>) {
        val result = parts.filter {
            it.value.symbol == '*' && it.value.partNumbers.size == 2
        }.map {
            it.value.partNumbers[0] * it.value.partNumbers[1]
        }.sum()

        println("Part2: $result")

    }

    private fun findParts(): Map<Pair<Int, Int>, Part> {
        val data = input.get(FILE).map { it.replace('.', ' ') }
        val parts = mutableMapOf<Pair<Int, Int>, Part>()
        val regex = Regex("\\d+")

        data.forEachIndexed { index, line ->
            val numbers = regex.findAll(line).map { Detail(it.range, index, it.value.toInt()) }.toList()
            numbers.forEach { detail ->
                findPartForPartNumber(data, detail)?.let { values ->
                    val part = parts.computeIfAbsent(values.second) { Part(values.first, mutableListOf()) }
                    part.partNumbers.add(detail.number)
                }
            }
        }

        return parts
    }

    private fun findPartForPartNumber(data: List<String>, detail: Detail): Pair<Char, Pair<Int, Int>>? {
        val partRegex = Regex("\\S")

        //Is part located above?
        if (detail.y > 0) {
            val findFrom = detail.x.first - 1
            val findTo = detail.x.last + 2
            val possiblePartLocation =
                data[detail.y - 1].substring(findFrom.coerceAtLeast(0), findTo.coerceAtMost(data[0].count() - 1))

            partRegex.find(possiblePartLocation)?.let {
                val x = findFrom + it.range.first
                val y = detail.y - 1
                return Pair(it.value.first(), Pair(x, y))
            }

        }

        // Is part located below?
        if (detail.y < data[0].count() - 1) {
            val findFrom = detail.x.first - 1
            val findTo = detail.x.last + 2
            val possiblePartLocation =
                data[detail.y + 1].substring(findFrom.coerceAtLeast(0), findTo.coerceAtMost(data[0].count() - 1))

            partRegex.find(possiblePartLocation)?.let {
                val x = findFrom + it.range.first
                val y = detail.y + 1
                return Pair(it.value.first(), Pair(x, y))
            }
        }

        // Is part located before?
        if (detail.x.first > 0) {
            val point = data[detail.y][detail.x.first - 1]
            if (point != ' ') {
                return Pair(point, Pair(detail.x.first - 1, detail.y))
            }
        }
        // Is part located after?
        if (detail.x.last < data[0].count() - 1) {
            val point = data[detail.y][detail.x.last + 1]
            if (point != ' ') {
                return Pair(point, Pair(detail.x.last + 1, detail.y))
            }
        }

        //No matching part for part number
        return null
    }


    companion object {
        const val FILE = "/2023/input3.my"

        data class Detail(
            val x: IntRange,
            val y: Int,
            val number: Int
        )

        data class Part(
            val symbol: Char,
            val partNumbers: MutableList<Int> = mutableListOf()
        )
    }
}


