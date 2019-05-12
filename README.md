# Planner App

Simple Android application project, which is using for trying and discovering some development practices. For now there is no some plans to release this application in production using, it\'s just about testing.

## Main libraries used

 * Kotlin
 * Kotlin Coroutines
 * Anko Layouts
 * AndroidX Lifecycle extensions
 * Room
 
## Description

Application UI based on a single activity, which represents entry point of application ui, providing lifecycle of the screen and initializing ui. UI building from a fragments called `ViewComponent`, which are similar to fragments in Android SDK. Mostly view components gets its own `ViewScope`, which can be reusing between component instances, for example, when ui rebuilds, because of configuration changing.
