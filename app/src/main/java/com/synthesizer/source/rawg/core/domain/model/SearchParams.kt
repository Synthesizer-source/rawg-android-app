package com.synthesizer.source.rawg.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SearchParams(val search: String, val ordering: String, val dates: String) : Parcelable