package com.samra.rickandmorty.data.network.model

import java.io.Serializable

data class Location(
    val name: String,
    val url: String
): Serializable