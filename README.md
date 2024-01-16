
# News App

This is a News Android application written using MVVM Architecture and Jetpack Components with unit testing.



### Features

 - Instant search using Flow operators like Debounce,
   Filter, DistinctUntilChanged, FlatMapLatest
 - Offline news to keep user updated without connection
 - Fetches TopHeadLine of the news
 - User can chose to see country specific news
 - News available in multiple languages
 - Multiple news sources available to fetch news
 - Pagination

 


### Lessons Learned

While building this project I have read various blogs to understand the below concepts.

- MVVM Architecture
- Offline First architecture with a single source of   truth
- Kotlin
- Dagger
- Room Database
- Retrofit
- Coroutines
- Flow
- StateFlow
- ViewBinding
- Pagination
- Unit Test




### Dependency Used

- Recycler View for listing
```
implementation "androidx.recyclerview:recyclerview:1.2.1"
implementation 'androidx.recyclerview:recyclerview-selection:1.1.0' //multi item selection
```
- Glide for image loading
```
implementation 'com.github.bumptech.glide:glide:4.11.0'
```
- Retrofit for networking
```
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.8.0'
```
- Android Lifecycle aware component 
```
implementation 'android.arch.lifecycle:extensions:1.1.1'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
```
- Dagger for dependency Injection 
```
implementation "com.google.dagger:dagger:2.42"
kapt "com.google.dagger:dagger-compiler:2.42"
```
- For WebView browser 
```
implementation 'androidx.browser:browser:1.4.0'
```
- Room Database 
```
implementation "androidx.room:room-runtime:2.4.2"
kapt "androidx.room:room-compiler:2.4.2"
// optional - Kotlin Extensions and Coroutines support for Room
implementation "androidx.room:room-ktx:2.4.2"
```

- Paging library 
```
implementation "androidx.paging:paging-runtime:3.1.1"
```





### Run Locally

Clone the project

```bash
  git clone https://github.com/Tanya-jain99/NewsApp.git
```

Go to the project directory

```bash
  cd my-project
```

Now you just have to import project in Android studio and Build it and Run.


### Complete Project structure

```
├───java
│   └───com
│       └───tanya
│           └───newsapp
│               ├───data
│               │   ├───api
│               │   │   └───models
│               │   ├───local
│               │   │   ├───dao
│               │   │   └───entities
│               │   ├───model
│               │   ├───paging
│               │   └───repository
│               ├───di
│               │   ├───component
│               │   └───module
│               ├───ui
│               │   ├───adapter
│               │   ├───base
│               │   ├───view
│               │   └───viewmodel
│               └───utils
└───res
    ├───layout
    ├───values
```
## 🚀 About Me
Hi there! My name is Tanya Jain, I work as a Software Developer and like to expand my skill set in my spare time.

If you have any questions or want to connect, feel free to reach out to me on : 

- [LinkedIn](https://www.linkedin.com/in/tanyajain06)
- [GitHub](https://github.com/Tanya-Jain99)
