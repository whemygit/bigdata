name := "MyMllib"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.2.0"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.2.0"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.2.0"

libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.2.0"

libraryDependencies += "org.apache.spark" % "spark-graphx_2.11" % "2.2.0"

libraryDependencies += "org.lionsoul" % "jcseg-core" % "2.1.1"

libraryDependencies += "org.lionsoul" % "jcseg-analyzer" % "2.1.1"

libraryDependencies += "org.lionsoul" % "jcseg-server" % "2.1.1"

libraryDependencies += "org.lionsoul" % "jcseg-elasticsearch" % "2.1.1"

libraryDependencies += "org.lionsoul" % "jcseg" % "2.1.1" pomOnly()

libraryDependencies += "org.apache.lucene" % "lucene-core" % "4.7.2"

libraryDependencies += "org.apache.lucene" % "lucene-queryparser" % "4.7.2"

libraryDependencies += "org.apache.lucene" % "lucene-analyzers-common" % "4.7.2"

libraryDependencies += "com.janeluo" % "ikanalyzer" % "2012_u6"