//Enable the sbt web plugin : This plugin will allow you to start and stop your application.
addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.7.0")

//Enable the sbt idea plugin
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

//Enable the sbt eclipse plugin
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")

resolvers += "Sonatype snapshots" at
  "http://oss.sonatype.org/content/repositories/snapshots/"