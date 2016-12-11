



package bootstrap.liftweb

import code.model._
import net.liftmodules.JQueryModule
import net.liftweb._
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.http.js.jquery.JQueryArtifacts
import net.liftweb.mapper._
import net.liftweb.sitemap.Loc._
import net.liftweb.sitemap._
import net.liftweb.util._


/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {// check if there is using the DBVendor object
      sys.props.put("h2.implicitRelativePath", "true")
      val vendor =  new StandardDBVendor(
          //          Props.get("db.driver") openOr "org.h2.Driver",
          //          Props.get("db.url") openOr "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
          //          Props.get("db.user"), Props.get("db.password"))

          Props.get("db.driver") openOr "org.postgresql.Driver",
          Props.get("db.url") openOr "jdbc:postgresql://localhost:5432/myLift",
          Props.get("db.user"), Props.get("db.password"))
      DB.defineConnectionManager(util.DefaultConnectionIdentifier, vendor)

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

    }
//    /Users/zhanghongwei/Desktop/lift-lift_26_sbt-3c4742e/scala_211/lift_basic
    //
    //
    // /src/main/scala/bootstrap/liftweb/Boot.scala



    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User,Author, BookAuthors, Book, Publisher)

    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    def sitemap = SiteMap(
      Menu.i("Home") / "index" >> User.AddUserMenusAfter, // the simple way to declare a menu

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"),
        "Static Content")))

    def sitemapMutators = User.sitemapMutator

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMapFunc(() => sitemapMutators(sitemap))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery191
    JQueryModule.init()

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
  }
}
