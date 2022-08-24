// The "Image" DSL is the easiest way to create images
import doodle.effect.Writer.{Jpg, Png}
import doodle.image._
// Colors and other useful stuff
import doodle.core._
// Extension methods
import doodle.image.syntax.all._
// Render to a window using Java2D (must be running in the JVM)
import doodle.java2d._

// Need the Cats Effect runtime to run everything
import cats.effect.unsafe.implicits.global
import cats.effect.IOApp.Simple
import cats.effect.IOApp
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import scala.util.Random
import doodle.java2d.effect.Size
import doodle.java2d.effect.Center.CenteredOnPicture
import doodle.interact.algebra.Redraw
import doodle.java2d.effect.Canvas.apply
import cats.implicits._
import doodle.reactor._
import doodle.image.syntax._
import doodle.image.syntax.core._
import doodle.reactor._
import java.awt.RadialGradientPaint

object Main extends App {
  case class CircleParams(radius: Int, xValue: Int, yValue: Int)

  val circlesPerRow   = 100
  val maxCircleRadius = 80
  val maxXValue       = 10

  object Circles {
    def createYValues(count: Int, init: List[Int]): List[Int] =
      init.flatMap(y => List.fill(count)(y))

    def printAllCircles(circ: List[Image]): Image =
      circ match {
        case Nil       => Image.empty
        case x :: tail => x.on(printAllCircles(tail))
      }

    def createABunchOfCircles(ys: List[Int]): List[Image] = {
      val circleParamsList = ys.map(y => CircleParams(Random.nextInt(100), Random.nextInt(600), y))
      circleParamsList.map(p =>
        Image
          .circle(p.radius)
          .at(p.xValue, p.yValue)
          .fillColor(Color.rgba(Random.nextInt(255), 0, Random.nextInt(175), Random.between(0.0, 0.5)))
          .noStroke
      )
    }
  }

  val frame                       = Frame.fitToPicture(10).background(Color.rgb(100, 100, 200))
  val yValues                     = Circles.createYValues(100, List(100, 200, 300, 400, 500))
  val circlesToPrint: List[Image] = Circles.createABunchOfCircles(yValues)
  val image                       = Circles.printAllCircles(circlesToPrint)

  Reactor.init(image).withRender(identity).run(frame)
}
