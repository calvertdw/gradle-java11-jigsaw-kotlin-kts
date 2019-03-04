package com.example

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import java.util.stream.Stream

class JigsawPlugin : Plugin<Project>
{
   override fun apply(project: Project)
   {
      project.task("jlink") {
         it.run {
            doLast {
               val buildLibsDir = project.buildDir.resolve("libs").toPath()
               Files.createDirectories(buildLibsDir)

               val str = "An 😀awesome 😃string with a few 😉emojis!"

               val fileListStream = Files.list(buildLibsDir).parallel()

               var javaHome = Paths.get(System.getProperty("java.home"))
               var modulesHome = Paths.get(System.getProperty("java.home"))

               fileListStream.flatMap<String> {
                  var jdepsCommand = arrayOf(javaHome.resolve("bin/java").toString(),
                                             "--list-modules",
                                             "--module-path",
                                             modulesHome.resolve("jmods").toString())

                  val commandProcess = Runtime.getRuntime().exec(jdepsCommand);
                  commandProcess.waitFor(20, TimeUnit.MINUTES)
               }
         }
      }
   }

//      fun dependentModulesOfJar(jarPath: Path): Stream<String>
//      {
//
//      }
   }
}