package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input

class Day5 {
    private val input = Input()
    fun run() {
        val stackInput = mutableSetOf<List<String>>()
        val commands = mutableListOf<List<Int>>()
        var i = 0
        input.get(FILE).forEach { line ->
            val data = line.split(",")
            if (line.contains(Regex("\\["))) {
                stackInput.add(line.replace(Regex("\\[|]"), "").replace(Regex("\\s\\s\\s\\s"), " ").split(" "))
            } else {
                commands.add(line.split(",").map { it.toInt() })
            }
        }

        part1(createStack(stackInput.reversed()), commands)
        part2(createStack(stackInput.reversed()), commands)

        return
    }

    private fun part1(stack: MutableList<MutableList<String>>, commands: MutableList<List<Int>>) {
        commands.forEach { command ->
            val count = command[0]
            val from = command[1] - 1 //command indexes start from 1
            val to = command[2] - 1
            stack[from].takeLast(count).reversed().forEach {
                stack[to].add(it)
            }
            stack[from] = stack[from].dropLast(count).toMutableList()
        }
        val result = StringBuilder()
        stack.forEach {
            result.append(it.last())
        }

        println("Part1: $result")

    }

    private fun part2(stack: MutableList<MutableList<String>>, commands: MutableList<List<Int>>) {
        commands.forEach { command ->
            val count = command[0].toInt()
            val from = command[1].toInt() - 1 //command indexes start from 1
            val to = command[2].toInt() - 1
            stack[to].addAll(stack[from].takeLast(count))
            stack[from] = stack[from].dropLast(count).toMutableList()
        }
        val result = StringBuilder()
        stack.forEach {
            result.append(it.last())
        }

        println("Part2: $result")
    }

    private fun createStack(stackInput: List<List<String>>): MutableList<MutableList<String>> {
        val stack = MutableList<MutableList<String>>(stackInput[0].size) { mutableListOf() }
        stackInput.forEach {
            it.withIndex().forEach { item ->
                if (item.value.isNotBlank()) {
                    stack[item.index].add(item.value)
                }
            }
        }
        return stack
    }

    companion object {
        const val FILE = "/2022/input5"
        const val FILE_TEST = "/2022/input5-test"
    }
}