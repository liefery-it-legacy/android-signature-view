# Android Signature Widget

> A simple view where a signature can be drawn

[![](https://jitpack.io/v/liefery/android-signature-widget.svg)](https://jitpack.io/#liefery/android-signature-widget)

![Sample app screenshot](https://liefery.github.io/android-signature-widget/screenshot.png)

## Installation

### sbt

```scala
resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.liefery" % "android-signature-widget" % "1.0.0"
```

### Gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    compile 'com.github.liefery:android-signature-widget:1.0.0'
}
```

## Usage

```xml
<com.liefery.android.signature_view.SignatureView
    android:layout_width="50dp"
    android:layout_height="50dp"
    app:stopBadge_circleColor="#4c8c4a"
    app:stopBadge_shapeColor="#003300"
    app:stopBadge_shadowColor="#000000"
    app:stopBadge_shadowDx="1dp"
    app:stopBadge_shadowDy="1dp"
    app:stopBadge_shadowRadius="3dp"
    app:stopBadge_stopNumber="123" />
```

Please have a look at the sample application for more details.
=======
