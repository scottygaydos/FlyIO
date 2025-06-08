package net.inherency.flyio.exercise

import net.inherency.google.base.GoogleSheetClient
import net.inherency.google.base.GoogleSheetReadRepository
import org.springframework.stereotype.Service

@Service
class ExerciseTabReader(
    private val googleSheetClient: GoogleSheetClient): GoogleSheetReadRepository<ExerciseTabRow> {

    //use very simple, local caching.
    private var rows: List<ExerciseTabRow> = emptyList()

    override fun findAllSortedCacheable(): List<ExerciseTabRow> {
        if (rows.isEmpty()) {
            rows = googleSheetClient.reportNonHeaderRowsFromTab(
                tabName(),
                this::mapRowsFromSheetToObjects
            )
        }
        return rows
    }

    override fun mapRowsFromSheetToObjects(row: List<String>): ExerciseTabRow {
        return ExerciseTabRow(
            name = row[0],
            targetArea = row[1],
            repCount = row[2].toInt(),
            weightDetails = row[3],
            image = row[4]
        )
    }

    override fun tabName(): String {
        return "App Workout List"
    }

}