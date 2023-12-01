package net.testuje.syky.aoc.year2022

import net.testuje.syky.aoc.Utils.Input
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode


/**
 * Advent of Code 11/2022
 * more details at https://adventofcode.com/2022/day/11
 */
@Suppress("UNCHECKED_CAST")
class Day11 {
    private val input = Input()

    fun run() {
        val startData = parseInput(input.get(FILE))
//        simulate(20, startData, true)
//        val divisor = startData.toList().fold(1) { acc, monkey -> acc * monkey.second.testDivisor.toInt() }
        simulate(10000, startData, false)
        return
    }

    private fun simulate(numberOfRounds: Int, monkeys: Map<Int, Monkey>, damageRelief: Boolean) {
        for (i in 1 .. numberOfRounds) {
            monkeys.forEach {
                val monkey = it.value
                monkey.items.forEach { item ->
                    monkey.inspections++
                    if(damageRelief) {
                        item.level = monkey.operation(item.level)
                    } else {
                        item.level = monkey.operation2(item.level)
                    }
                    if (monkey.test(item.level)) {
                        monkeys[monkey.successfulTarget]!!.items.add(item)
                    } else {
                        monkeys[monkey.failureTarget]!!.items.add(item)
                    }
                }
                monkey.items.clear()

            }
            if(listOf(1,20,1000,5000,10_000).contains(i)) {
                printRound(monkeys, i)
            }
        }
    }

    fun printRound(monkeys: Map<Int, Monkey>, round: Int) {
        println("== ROUND $round")
        monkeys.forEach {
            println("Monkey ${it.value.id} inspected: ${it.value.inspections} times")
        }
    }

    private fun parseInput(stringList: List<String>): Map<Int, Monkey> {
        val monkeys = mutableMapOf<Int, Monkey>()
        val input = mutableMapOf<String, Any>()
        stringList.forEach { line ->

            when {
                line.trim().startsWith("Monkey ") -> {
                    input["id"] = line.trim()
                        .replace("Monkey ", "")
                        .replace(":", "")
                        .toInt()
                }

                line.trim().startsWith("Starting ") -> {
                    input["items"] = line.trim()
                        .replace("Starting items: ", "")
                        .split(", ")
                        .map { Item(it.toLong()) }
                }

                line.trim().startsWith("Operation:") -> {
                    input["operation"] = line.trim()
                        .replace("Operation: ", "")
                }

                line.trim().startsWith("Test:") -> {
                    input["test"] = line.trim()
                        .replace("Test: divisible by ", "")
                        .toLong()
                }

                line.trim().startsWith("If true") -> {
                    input["successfulTarget"] = line.trim()
                        .replace("If true: throw to monkey ", "")
                        .toInt()

                }

                line.trim().startsWith("If false") -> {
                    input["failureTarget"] = line.trim()
                        .replace("If false: throw to monkey ", "")
                        .toInt()
                }

                line.trim().isEmpty() -> {
                    val monkey = newMonkey(input)
                    monkeys[monkey.id] = monkey
                    input.clear()
                }
            }
        }
        if (input.isNotEmpty()) {
            val monkey = newMonkey(input)
            monkeys[monkey.id] = monkey
        }

        return monkeys
    }

    private fun newMonkey(input: MutableMap<String, Any>): Monkey {
        return Monkey(
            input["id"] as Int,
            input["items"] as ArrayList<Item>,
            createOperationLambda(input["operation"] as String),
            createOperation2Lambda(input["operation"] as String),
            input["test"] as Long,
            createTestLambda(input["test"] as Long),
            input["successfulTarget"] as Int,
            input["failureTarget"] as Int
        )
    }

    private fun createOperationLambda(string: String): (Long) -> Long {
        val divider = 3L
        when {
            string.contains("+") -> {
                val data = string.split("+")
                if (data[1].contains(Regex("\\d+"))) {
                    val op = data[1].trim().toLong()
                    return {i : Long -> (i+op).floorDiv(divider)}
                } else {
                    return {i : Long -> (i*i).floorDiv(divider)}
                }
            }
            else -> {
                val data = string.split("*")
                if (data[1].contains(Regex("\\d+"))) {
                    val op = data[1].trim().toLong()
                    return {i : Long -> (i*op).floorDiv(divider)}

                }
            }
        }
        return {i: Long -> (i * i).floorDiv(divider)}
    }
    private fun createOperation2Lambda(string: String): (Long) -> Long {
        when {
            string.contains("+") -> {
                val data = string.split("+")
                if (data[1].contains(Regex("\\d+"))) {
                    val op = data[1].trim().toLong()
                    return { i: Long -> (i + op) % 9699690 }
                } else {
                    return { i: Long -> (i + i) % 9699690 }
                }
            }

            else -> {
                val data = string.split("*")
                if (data[1].contains(Regex("\\d+"))) {
                    val op = data[1].trim().toLong()
                    return { i: Long -> (i * op) % 9699690 }
                }
            }
        }

        return { i: Long -> (i * i) % 9699690}
    }

    private fun createTestLambda(condition: Long): (Long) -> Boolean {
        return { i: Long -> i % condition  == 0L }
    }

    companion object {
        const val FILE = "/2022/input11"
        const val FILE_TEST = "/2022/input11-test"

        data class Item(
            var level: Long
        )

        class Monkey(
            val id: Int,
            val items: ArrayList<Item>,
            val operation: (Long) -> Long,
            val operation2: (Long) -> Long,
            val testDivisor: (Long),
            val test: (Long) -> Boolean,
            val successfulTarget: Int,
            val failureTarget: Int,
            var inspections: Int = 0
        )
    }

    fun Collection<Int>.product() = reduce { acc, i -> acc * i }

}

