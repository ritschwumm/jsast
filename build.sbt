Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / versionScheme := Some("early-semver")

name			:= "jsast"
organization	:= "de.djini"
version			:= "0.36.0"

scalaVersion	:= "3.1.3"
scalacOptions	++= Seq(
	"-feature",
	"-deprecation",
	"-unchecked",
	"-Wunused:all",
	"-Xfatal-warnings",
	"-Ykind-projector:underscores",
)

conflictManager	:= ConflictManager.strict withOrganization "^(?!(org\\.scala-lang|org\\.scala-js)(\\..*)?)$"

wartremoverErrors ++= Seq(
	Wart.StringPlusAny,
	Wart.EitherProjectionPartial,
	Wart.OptionPartial,
	Wart.Enumeration,
	Wart.FinalCaseClass,
	Wart.JavaConversions,
	Wart.Option2Iterable,
	Wart.TryPartial
)
