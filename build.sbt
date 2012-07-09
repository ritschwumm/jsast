name			:= "jsast"

organization	:= "de.djini"

version			:= "0.0.1"

scalaVersion	:= "2.9.2"

//publishArtifact in (Compile, packageBin)	:= false

publishArtifact in (Compile, packageDoc)	:= false

publishArtifact in (Compile, packageSrc)	:= false

libraryDependencies	++= Seq(
	"de.djini"		%%	"scutil"	% "0.0.6"	% "compile"
)

scalacOptions	++= Seq("-deprecation", "-unchecked")
