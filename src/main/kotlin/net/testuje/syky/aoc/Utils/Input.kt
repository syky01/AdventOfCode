package net.testuje.syky.aoc.Utils

class Input {
    fun get(file: String): List<String> = this::class.java.getResource(file).readText().trim().lines()

    fun read(file: String): String = this::class.java.getResource(file).readText().trim()
}