package net.inherency.flyio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlyIoAppApplication

fun main(args: Array<String>) {
    runApplication<FlyIoAppApplication>(*args)
}
