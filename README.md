<h1 align="center">Anime King</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a> 
</p>

<p align="center">  
Anime King is an anime trivia game based on modern Android application tech-stacks and the MVVM architecture.<br>
</p>
</br>

## Download
Visit the [Play Store](https://play.google.com/store/apps/details?id=com.murrayde.animekingandroid) and check it out!


<img src="main_screen_preview.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 26
- [RxJava](https://github.com/ReactiveX/RxJava) for asynchronous-based events and parallel API network calls.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Gson](https://github.com/google/gson) - convert Java Objects into their JSON representation.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection.
- [Firebase](https://firebase.google.com/docs/build) cloud-hosted [NoSQL database, serverless functions, auth]
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View <-> ViewModel <-> Model)
  - Repository pattern
- [Timber](https://github.com/JakeWharton/timber) - logging.
- [Glide](https://github.com/bumptech/glide) - loading images.
- [Epoxy](https://github.com/airbnb/epoxy) - building a complex RecyclerView.
- [Custom Views](https://developer.android.com/guide/topics/ui/custom-components) - animated view components.


## Architecture
Anime King is based on the MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## Kitsu API

<img src="kitsu.png" align="right" width="4%"/>

Anime King uses the [Kitsu Api](https://kitsu.docs.apiary.io/) for fetching resources.<br>


# License
```xml
Designed and developed by cleverSheep (Dean Murray) 2021

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
