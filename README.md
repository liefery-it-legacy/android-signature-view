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
Using the SignaturePreviewWidget to trigger the SignatureActivity and display the results in the SignaturePreviewWidget
```java
public class Activity extends SignaturePreviewActivity {

    SignaturePreviewWidget signatureView;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main );

        signatureView = (SignaturePreviewWidget) findViewById( R.id.signature_preview );
    }

    @Override
    public String getSignatureActivityTitle() {
        return "Kundenunterschrift";
    }

    @Override
    public String getSignatureActivitySubtitle() {
        return "HS9H-AX1U";
    }

    @Override
    public SignaturePreviewWidget getSignaturePreviewWidget() {
        return signatureView;
    }
}
```

All components like `SignatureActivity`, `SignaturePaintView`, `SignaturePathView`, `SignaturePreviewActivity` and `SignaturePreviewWidget` can be used independently from each other as well.

Please have a look at the sample application for more details.