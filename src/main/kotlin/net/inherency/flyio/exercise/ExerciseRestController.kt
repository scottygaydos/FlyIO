package net.inherency.flyio.exercise

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("exercise")
class ExerciseRestController(
    val fifteenMinuteFullBodyTabReader: FifteenMinuteFullBodyTabReader) {

    @GetMapping("/list")
    fun listExercises(): List<String> {
        return fifteenMinuteFullBodyTabReader.findAllSortedCacheable().map { it.name }
    }

}