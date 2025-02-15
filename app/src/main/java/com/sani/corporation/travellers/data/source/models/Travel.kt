package com.sani.corporation.travellers.data.source.models

import com.google.gson.annotations.SerializedName
import com.sani.corporation.travellers.data.source.network.models.NetworkTravel

data class Travel (
    val title: String,
    val description: String?,
    val invitedEmails: List<String>? = null,
    val id: String? = null,
    val owner: Boolean? = false
) {
    fun toNetwork() = NetworkTravel(
        title = title,
        description = description,
        invitedEmails = invitedEmails,
        id = id,
        owner = owner
    )
}