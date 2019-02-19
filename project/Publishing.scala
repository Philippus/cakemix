import sbt._
import sbt.Keys._
import xerial.sbt.Sonatype._
import sbtrelease.ReleasePlugin.autoImport._
import com.typesafe.sbt.pgp._

object Publishing {
  lazy val publishingSettings = sonatypeSettings ++ Seq(
    releaseCrossBuild := true,
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    publishTo := {
      val nexus = "https://oss.sonatype.org/"

      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    credentials ++= (
      for {
        username ← sys.env.get("SONATYPE_USERNAME")
        password ← sys.env.get("SONATYPE_PASSWORD")
      } yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq,
    pomExtra := (
      <url>https://github.com/xebia/cakemix#readme</url>
      <scm>
        <url>git@github.com:xebia/cakemix.git</url>
        <connection>scm:git@github.com:xebia/cakemix.git</connection>
      </scm>
      <developers>
        <developer>
          <id>agemooij</id>
          <name>Age Mooij</name>
          <url>http://github.com/agemooij</url>
        </developer>
        <developer>
          <id>raboof</id>
          <name>Arnout Engelen</name>
          <url>http://github.com/raboof</url>
        </developer>
      </developers>
    ))
}
