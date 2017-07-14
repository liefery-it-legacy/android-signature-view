lazy val root = project.in( file( "." ) )
    .enablePlugins( AndroidLib )
    .settings( Settings.common )
    .settings(
        resolvers += "google-maven" at "https://maven.google.com",
        libraryDependencies ++=
            "com.android.support" % "support-annotations" % "25.4.0" % "compile" ::
            "com.android.support" % "support-compat" % "25.4.0" ::
            "com.android.support" % "appcompat-v7" % "25.4.0" ::
            Nil,
        name := "signature-view",
        publishArtifact in ( Compile, packageDoc ) := false
    )

lazy val sample = project
    .enablePlugins( AndroidApp )
    .settings( Settings.common )
    .settings(
        organization := organization.value + ".signature_view.sample",
        run := ( run in Android ).evaluated
    )
    .dependsOn( root )