<h1 align="center">RAWG Android App</h1>
<p align="center">RAWG is the largest video game database and game discovery service. This Android application developed with RAWG API.</p>

## Prerequisites

1. Obtain an API key from [RAWG](https://rawg.io/apidocs)
2. Modify `api_key` attribute in `gradle.properties`

```
api_key="<<your_api_key>>"
```

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [MVVM](https://developer.android.com/jetpack/guide) Architecture
- [Architecture Components](https://developer.android.com/topic/libraries/architecture/) 
    - [View Binding](https://developer.android.com/topic/libraries/view-binding)
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Retrofit2 & OkHttp3](https://square.github.io/retrofit/) for API calls
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- [Glide](https://github.com/bumptech/glide) for images loading
- [Navigation Component](https://developer.android.com/guide/navigation) for transitions between fragments
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for pagination
- [Lottie](https://lottiefiles.com/) animation library
- [Keyboard Events Library](https://github.com/yshrsmz/KeyboardVisibilityEvent) for adding callbacks when keyboard show/hide events
- [Scalable Dp Library](https://github.com/intuit/sdp) scalable unit size for different screen sizes 


## Architecture
<p align="center"><img alt="architecture" src="/images/architecture.png" width="35%"></img></p>
