package com.sani.corporation.travellers.data.source.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sani.corporation.travellers.data.source.models.Travel

data class NetworkTravel (
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("invited_emails")
    val invitedEmails: List<String>?,
    @SerializedName("id")
    @Expose(serialize = false, deserialize = true)
    val id: String?,
    @SerializedName("owner")
    @Expose(serialize = false, deserialize = true)
    val owner: Boolean? = false
) {

    fun toExternal() = Travel(
        title = title,
        description = description,
        invitedEmails = invitedEmails,
        id = id,
        owner = owner
    )
}