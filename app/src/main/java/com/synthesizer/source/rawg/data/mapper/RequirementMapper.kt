package com.synthesizer.source.rawg.data.mapper

import com.synthesizer.source.rawg.data.domain.RequirementsDomain
import com.synthesizer.source.rawg.data.remote.Requirements

fun Requirements.minimumToDomain(): RequirementsDomain {
    val info = minimum?.split(delimiters = arrayOf(":"))
    var os = ""
    var processor = ""
    var memory = ""
    var graphics = ""
    var storage = ""
    info?.let {
        os = it[2].substring(0, it[2].indexOf("Processor")).trim()
        processor = it[3].substring(0, it[3].indexOf("Memory")).trim()
        memory = it[4].substring(0, it[4].indexOf("Graphics")).trim()
        graphics = it[5].substring(0, it[5].indexOf("Storage")).trim()
        storage = it[6].substring(0, it[6].indexOf("spaceSound")).trim()
    }
    return RequirementsDomain(
        os = os,
        processor = processor,
        memory = memory,
        graphics = graphics,
        storage = storage
    )
}

fun Requirements.recommendedToDomain(): RequirementsDomain {
    val info = recommended?.split(delimiters = arrayOf(":"))
    var os = ""
    var processor = ""
    var memory = ""
    var graphics = ""
    var storage = ""
    info?.let {
        os = it[2].substring(0, it[2].indexOf("Processor")).trim()
        processor = it[3].substring(0, it[3].indexOf("Memory")).trim()
        memory = it[4].substring(0, it[4].indexOf("Graphics")).trim()
        graphics = it[5].substring(0, it[5].indexOf("Storage")).trim()
        storage = it[6].substring(0, it[6].indexOf("spaceSound")).trim()
    }
    return RequirementsDomain(
        os = os,
        processor = processor,
        memory = memory,
        graphics = graphics,
        storage = storage
    )
}