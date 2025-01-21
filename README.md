My Android App
Overview
This Android application demonstrates how to fetch data from a public API and display it in a list with detailed views. Built using modern Android development tools and techniques such as Jetpack Compose, MVVM architecture, and Dependency Injection (Koin), this app ensures a clean and efficient codebase.

Features
Home Screen:

Fetch and display two lists of items (e.g., books, recipes, or products) using a public API.

Toggle button or tabs at the top to switch between the two lists.

Efficient rendering with Jetpack Compose's LazyColumn.

Shimmer layout during data loading to enhance user experience.

Details Screen:

Clicking on an item in the list opens a detailed view with relevant information.

API Integration:

Perform two API calls simultaneously using Single.zip (RxKotlin) to fetch different datasets.

Networking handled with Retrofit.

Error Handling:

Proper error management with user-friendly messages.

Ensures no crashes during API calls or data handling.

Architecture:

MVVM (Model-View-ViewModel) architecture for a well-organized codebase.

Dependency Injection with Koin for clean, modular, and testable code.

Installation
Clone the repository:

bash
https://github.com/Coding110011/Jetpack.git
Open the project in Android Studio.

Build and run the app on an emulator or physical device.

Dependencies
Jetpack Compose: For building UI.

Retrofit: For networking.

RxKotlin: For managing API calls.

Koin: For dependency injection.

Shimmer: For loading animations.

Usage
Launch the app to see the Home Screen with two lists of items.

Use the toggle button or tabs to switch between the two lists.

Click on an item to open the Details Screen with more information.

Enjoy the smooth and efficient user experience!

Download Apk file for Testing https://github.com/Coding110011/Jetpack/raw/refs/heads/master/task.apk
