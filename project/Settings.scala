import android.Keys._
import sbt.Keys._
import sbt._

object Settings {
    val common = Def.settings(
        autoScalaLibrary := false,
        javacOptions ++=
            "-source" :: "1.7" ::
            "-target" :: "1.7" ::
            Nil,
        minSdkVersion := "14",
        organization := "com.liefery.android",
        platformTarget := "android-26",
        scalaVersion := "2.11.12",
        targetSdkVersion := "26"
    )
}