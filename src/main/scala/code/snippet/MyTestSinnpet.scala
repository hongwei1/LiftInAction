package code.snippet

import java.util.Date

import code.lib._
import net.liftweb.common._
import net.liftweb.util.Helpers._

class MyTestSinnpet {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  def myFormat = " *" #> <strong>hello world!</strong>


}

