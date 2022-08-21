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

//image consists of r number of rows
//each row contains a series of circles which spans the width of the frame
//each circle is filled with a colour which is selected randomly from a list
//each circle has a consistent transparency

//To make concentric circles, the x must be small enough that it looks like it's all within one circle
//If the x is larger then they will be overlap
case class CircleParams(diam: Double, xValue: Int)

val circlesPerRow = 100
val maxCircleRadius = 80
val maxXValue = 10

object Circles {
    def singleCircle(radius: Int, xValue: Int, yValue: Int): Image = {
      Image
      .circle(Random.nextInt(radius))
      .at(Random.nextInt(xValue), yValue)
      .fillColor(Color.rgba(Random.nextInt(255), 0, Random.nextInt(175), Random.between(0.0, 0.5)))
      .noStroke
    }

    def listOfCircles(numberPerRow: Int): List[Image] = {
    List.tabulate(numberPerRow)(_ => singleCircle(maxCircleRadius, maxXValue, 100))
  }
    
    def allCircles(circ:List[Image]): Image = {
      circ match {
        case Nil => Image.empty
        case x :: tail => x.beside(allCircles(tail))
      }
    }

  }

 val frame = Frame.size(600,600).background(Color.rgb(100, 100, 200))
 val image = Circles.allCircles(Circles.listOfCircles(100))

  Reactor.init(image).withRender(identity).run(frame)
  //TODO create a set of stacked circles in another location
  //Create a line of overlapping circles

}
