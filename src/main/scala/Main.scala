
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


object Main extends App{

  case class CircleParams(diam: Double, xValue: Int, yValue: Int)

  val listOfRandomCircleParams: List[CircleParams] = List.tabulate(100)(n => CircleParams(Random.nextInt(100).toDouble, Random.nextInt(10), 100))
  
  def createARandomListOfCircleParams: List[CircleParams] = {
  List.tabulate(100)(n => CircleParams(Random.nextInt(100).toDouble, Random.nextInt(10), 100))}

  def createCircleFromParams(circ: CircleParams): Image = {
    Image.circle(circ.diam).at(circ.xValue, circ.yValue)
  }

  lazy val listOfCircles: List[Image] = listOfRandomCircleParams.map(createCircleFromParams)
  val anotherListOfCircles: List[Image] = listOfRandomCircleParams.map(createCircleFromParams)
  
   def combineCircles(circs: List[Image]): Image = circs match {
      case Nil => Image.empty
      case x :: tail => x.on(combineCircles(tail))
   }

  def createAListOfSets(howMany: Int): List[Image] = {
    List.tabulate(10)(_ => combineCircles(listOfCircles))
  }

  def lineUpSets(sets: List[Image]): Image = sets match {
    case Nil => Image.empty 
    case x :: tail => x.beside(lineUpSets(tail))
  }

  val frame = Frame.size(600,600).background(Color.rgb(100, 100, 200))

  val image: Image = lineUpSets(createAListOfSets(10))

      //Reactor.init(combineCircles(listOfCircles)).withRender(identity).run(frame)    
      //Reactor.init(set1.beside(set2).beside(set3)).withRender(identity).run(frame)
      Reactor.init(image).withRender(identity).run(frame)


      //TODO create a set of stacked circles in another location 
      //Create a line of overlapping circles 

  }
