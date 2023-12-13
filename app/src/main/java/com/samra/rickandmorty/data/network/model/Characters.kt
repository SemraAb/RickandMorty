package com.samra.rickandmorty.data.network.model

data class Characters(
    val info: Info?=null,
    val results: List<Result>?=null
)