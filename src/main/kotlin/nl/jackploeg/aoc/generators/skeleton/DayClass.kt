package nl.jackploeg.aoc.generators.skeleton

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import nl.jackploeg.aoc.generators.InputGenerator
import nl.jackploeg.aoc.generators.InputGenerator.InputGeneratorFactory
import nl.jackploeg.aoc.generators.skeleton.SkeletonGenerator.Companion.SRC_DIRECTORY
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject

object DayClass {
  private val INPUT_GENERATOR_FACTORY_PACKAGE = InputGenerator::class.java.let { "${it.packageName}.${it.simpleName}" }
  private val INPUT_GENERATOR_FACTORY_NAME = InputGeneratorFactory::class.java.simpleName

  fun generateDayClass(year: String, day: String) {
    val factoryClassName = ClassName(INPUT_GENERATOR_FACTORY_PACKAGE, INPUT_GENERATOR_FACTORY_NAME)
    val file = FileSpec.builder("nl.jackploeg.aoc._$year.calendar.day$day", "Day$day")
      .addImport(
        INPUT_GENERATOR_FACTORY_PACKAGE,
        INPUT_GENERATOR_FACTORY_NAME
      )
      .addImport(
        "java.io",
        "File"
      )
      .addType(
        TypeSpec.classBuilder("Day$day")
          .primaryConstructor(
            FunSpec.constructorBuilder()
              .addAnnotation(Inject::class)
              .addParameter("generatorFactory", factoryClassName)
              .build()
          ).addProperty(
            PropertySpec.builder("generatorFactory", factoryClassName)
              .initializer("generatorFactory")
              .addModifiers(PRIVATE)
              .build()
          )
          .addFunction(
            FunSpec.builder("partOne")
              .addParameter("filename", String::class)
              .addModifiers()
              .returns(Int::class)
              //.addStatement("return·generatorFactory.forFile(filename).readAs(::day$day)·{·input·->\n  -1\n}")
              .addStatement("val input = File(filename).readLines()\nreturn -1")
              .build()
          )
          .addFunction(
            FunSpec.builder("partTwo")
              .addParameter("filename", String::class)
              .returns(Int::class)
              //.addStatement("return·generatorFactory.forFile(filename).readAs(::day$day)·{·input·->\n  -1\n}")
              .addStatement("val input = File(filename).readLines()\nreturn -1")
              .build()
          )
          .addFunction(
            FunSpec.builder("day$day")
              .addModifiers(PRIVATE)
              .addParameter("line", String::class)
              .addStatement("return 4")
              .build()
          )
          .build()
      )
      .build()

    file.writeTo(File(SRC_DIRECTORY))

    // remove all the extraneous `public` modifiers
    // and the extraneous (and incorrect) `Unit` return types
    // and the safety import of `kotlin.String`
    // and the safety import of `kotlin.Unit`
    val path = Paths.get("$SRC_DIRECTORY/nl/jackploeg/aoc/_$year/calendar/day$day/Day$day.kt")
    val content = String(Files.readAllBytes(path))
      .replace("public ".toRegex(), "")
      .replace(": Unit".toRegex(), "")
      .split("\n")
      .filterNot { it == "import kotlin.String" }
      .filterNot { it == "import kotlin.Unit" }
      .joinToString("\n")
    Files.write(path, content.toByteArray())
  }
}
