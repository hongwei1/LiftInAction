package code 
package snippet 

import java.util.Date

import code.lib._
import net.liftweb.common._
import net.liftweb.util.Helpers._

class HelloWorld {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  // replace the contents of the element with id "time" with the date
  def howdy = "#time *" #> date.map(_.toString ) //#> is implicit conversion, to make the time
  def howdy1 = "#content *" #> date.map("\n"+_.toString+ "HongweiZHang!!!!!!!" ) //#> is implicit conversion, to make the time


  /*
   lazy val date: Date = DependencyFactory.time.vend // create the date via factory

   def howdy = "#time *" #> date.toString
   */
}

