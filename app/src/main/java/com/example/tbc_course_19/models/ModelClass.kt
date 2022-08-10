package com.example.tbc_course_19.models

import com.squareup.moshi.Json

data class ModelClass(
    @Json(name = "field_id")
    val fieldId: Int?,
    val hint: String?,
    @Json(name = "field_type")
    val fieldType: String?,
    val keyboard: String?,
    val required: Any?,
    @Json(name = "is_active")
    val isActive: Boolean?,
    val icon: String?,
)


