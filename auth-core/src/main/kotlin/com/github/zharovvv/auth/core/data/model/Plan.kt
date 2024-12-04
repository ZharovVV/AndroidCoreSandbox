package com.github.zharovvv.auth.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Plan(

    @SerialName("name")
    val name: String? = null,
    @SerialName("space")
    val space: Int? = null,
    @SerialName("private_repos")
    val privateRepos: Int? = null,
    @SerialName("collaborators")
    val collaborators: Int? = null

)