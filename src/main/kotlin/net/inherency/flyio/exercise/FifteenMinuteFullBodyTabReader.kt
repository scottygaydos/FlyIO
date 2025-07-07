package net.inherency.flyio.exercise

import net.inherency.google.base.GoogleSheetClient
import net.inherency.google.base.GoogleSheetReadRepository
import org.springframework.stereotype.Service

@Service
class FifteenMinuteFullBodyTabReader(
    private val googleSheetClient: GoogleSheetClient): GoogleSheetReadRepository<ExerciseTabRow> {

    /**
     * Find all exercises from google sheet. Don't use caching; we rarely read, and we want latest data.
     */
    override fun findAllSortedCacheable(): List<ExerciseTabRow> {
        return googleSheetClient.reportNonHeaderRowsFromTab(
            tabName(),
            this::mapRowsFromSheetToObjects)
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
        return "15 Minute Full Body"
    }

}