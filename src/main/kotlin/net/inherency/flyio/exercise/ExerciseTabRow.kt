package net.inherency.flyio.exercise

import net.inherency.google.base.GoogleSheetNameMatchable
import net.inherency.google.base.GoogleSheetSimpleReportNameMatchable

data class ExerciseTabRow (
    override val name: String,
    val targetArea: String,
    val repCount: Int,
    val weightDetails: String,
    val image: String
) : GoogleSheetNameMatchable, GoogleSheetSimpleReportNameMatchable