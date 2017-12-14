lazy val root = project.in( file( "." ) )
    .enablePlugins( AndroidLib )
    .settings( Settings.common )
    .settings(
        resolvers += "google-maven" at "https://maven.google.com",
        libraryDependencies ++=
            "com.android.support" % "appcompat-v7" % "26.1.0" ::
            "com.android.support" % "design" % "26.1.0" ::
            "com.novocode" % "junit-interface" % "0.11" % "test" ::
            "junit" % "junit" % "4.12" % "test" ::
            "org.assertj" % "assertj-core" % "3.8.0" % "test" ::
            "org.robolectric" % "robolectric" % "3.5.1" % "test" ::
            Nil,
        name := "signature-view",
        publishArtifact in ( Compile, packageDoc ) := false,
        fork in Test := true,
        unmanagedClasspath in Test ++= (bootClasspath in Android).value
    )

lazy val sample = project
    .enablePlugins( AndroidApp )
    .settings( Settings.common )
    .settings(
        organization := organization.value + ".signature_view.sample",
        run := ( run in Android ).evaluated
    )
    .dependsOn( root )