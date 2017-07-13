Work in Progress
--------
=======
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

## Features

- `SignaturePreviewWidget`, `SignaturePaintView` and `SignaturePathView` to obtain, display and handle signatures in you Android application
- Signature Path can easily be converted to a `PathDescriptor` which is Parcelable
- Easily generate Bitmaps from Signatures
- Paint Stroke completely customizable

## Usage

Using only the SignatureView, that can be drawn on:
```xml
<com.liefery.android.signature_view.SignaturePaintView
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

Using the SignaturePreviewWidget to trigger the SignatureActivity and display the results in the SignaturePreviewWidget
```java
// Get a SignaturePreviewWidget and set the SignatureActivity to open on click
signatureView = (SignaturePreviewWidget) findViewById( R.id.signature_preview );
signatureView.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick( View view ) {
        Intent i = new Intent(
            getApplicationContext(),
            SignatureActivity.class );
        startActivityForResult( i, REQUEST_CODE_SIGNATURE );
    }
} );
 
...
 
@Override
protected void onActivityResult(
    int requestCode,
    int resultCode,
    Intent data ) {
    if ( requestCode == REQUEST_CODE_SIGNATURE ) {
        if ( resultCode == Activity.RESULT_OK ) {
            // Getting the result from the "result" field of the Intent
            // and setting it do display in the SignaturePreview
            PathDescriptor result = data.getParcelableExtra( "result" );
            signatureView.set( result );
        }
    }
}
```

Please have a look at the sample application for more details.
=======
