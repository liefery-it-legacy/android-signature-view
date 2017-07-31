lazy val root = project.in( file( "." ) )
    .enablePlugins( AndroidLib )
    .settings( Settings.common )
    .settings(
        resolvers += "google-maven" at "https://maven.google.com",
        libraryDependencies ++=
            "org.robolectric" % "robolectric" % "3.3.2" % "test" ::
            "junit" % "junit" % "4.11" % "test" ::
            "com.novocode" % "junit-interface" % "0.11" % "test" ::
            "org.assertj" % "assertj-core" % "1.7.1" % "test" ::
            "com.android.support" % "support-annotations" % "25.4.0" % "compile" ::
            "com.android.support" % "support-compat" % "25.4.0" ::
            "com.android.support" % "appcompat-v7" % "25.4.0" ::
            Nil,
        name := "signature-view",
        publishArtifact in ( Compile, packageDoc ) := false,
        fork in Test := true,
        unmanagedClasspath in Test ++= (bootClasspath in Android).value,
            jacoco.settings
    )

lazy val sample = project
    .enablePlugins( AndroidApp )
    .settings( Settings.common )
    .settings(
        organization := organization.value + ".signature_view.sample",
        run := ( run in Android ).evaluated,
            jacoco.settings
    )
    .dependsOn( root )