package net.inherency.flyio

import net.inherency.flyio.exercise.ExerciseService
import net.inherency.flyio.exercise.ExerciseTabReader
import net.inherency.flyio.exercise.ExerciseTabRow
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
class ExerciseController(
    val exerciseTabReader: ExerciseTabReader,
    val exerciseService: ExerciseService) {

    @GetMapping("/")
    fun viewHomePage(model: Model): String {
        val serverTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        model["serverTime"] = serverTime
        model["allExercisesList"] = exerciseTabReader.findAllSortedCacheable()
        return "index"
    }

    @GetMapping("/shortWorkout.html")
    fun shortWorkout(model: Model,
                     @RequestParam("workoutInstanceId") workoutInstanceId: String?,
                     @RequestParam("currentExerciseName") currentExerciseName: String?,
                     @RequestParam("started") started: Boolean?): String {

        val hasStarted = started != null && started
        val instanceIdAndExercise: Pair<String, ExerciseTabRow> = if (workoutInstanceId == null || currentExerciseName == null) {
            exerciseService.getNextExerciseForWorkoutInstance()
        } else {
            exerciseService.getNextExerciseForWorkoutInstance(workoutInstanceId, currentExerciseName)
        }
        model["nextExercise"] = instanceIdAndExercise.second
        model["workoutInstanceId"] = instanceIdAndExercise.first
        model["started"] = hasStarted
        return "shortWorkout"
    }

}