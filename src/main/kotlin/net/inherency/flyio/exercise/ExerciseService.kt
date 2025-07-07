package net.inherency.flyio.exercise

import net.inherency.google.base.GoogleSheetReadRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExerciseService(
    tabReaders: List<GoogleSheetReadRepository<ExerciseTabRow>>) {

    private final val log: Logger = LoggerFactory.getLogger(ExerciseService::class.java)
    private final val readersByTabName: Map<String, GoogleSheetReadRepository<ExerciseTabRow>> = tabReaders.associateBy {  it.tabName() }
    private final var workoutInstances: LinkedHashMap<String, List<ExerciseTabRow>> = LinkedHashMap()

    fun getNextExerciseForWorkoutInstance(tabName: String): Pair<String, ExerciseTabRow> {
        if (workoutInstances.size > 10) {
            val oldestKey = workoutInstances.firstEntry().key
            log.info("Removing oldest workout instance={}", oldestKey)
            workoutInstances.remove(oldestKey)
        }
        val newWorkoutInstanceId = UUID.randomUUID().toString()
        val tabReader = readersByTabName[tabName]
        if (tabReader == null) {
            throw IllegalArgumentException("No tab named $tabName")
        }
        val exercises = tabReader.findAllSortedCacheable()
        workoutInstances[newWorkoutInstanceId] = exercises

        return Pair(newWorkoutInstanceId, exercises[0])
    }

    fun getNextExerciseForWorkoutInstance(workoutInstanceId: String, currentExerciseName: String): Pair<String, ExerciseTabRow> {
        val workoutInstance = workoutInstances[workoutInstanceId]
        if (workoutInstance == null) {
            throw IllegalArgumentException("There is no workout instance by that Id")
        }
        val indexOfCurrentExercise = workoutInstance.map {  it.name }.indexOf(currentExerciseName)
        if (indexOfCurrentExercise == -1) {
            throw IllegalArgumentException("There is no exercise by name of $currentExerciseName in workout instance $workoutInstanceId")
        }
        return if (indexOfCurrentExercise >= workoutInstance.size) {
            //https://media1.tenor.com/m/edV_ZgaVaqQAAAAC/chris-evans-captain-america.gif
            //https://i.pinimg.com/originals/e3/68/12/e36812ffa8170849b3fd854ae760294a.gif
            Pair(workoutInstanceId, ExerciseTabRow("All Done", "", 0, "", "https://64.media.tumblr.com/eeb1ea52ffc6f8aaf0ec3ef26a8a2795/04e24493b0d4f661-cb/s540x810/560ba2344a0fdcc4ecc462b5fcbd06646788e3ba.gif"))
        } else {
            Pair(workoutInstanceId, workoutInstance[indexOfCurrentExercise+1])
        }
    }
}