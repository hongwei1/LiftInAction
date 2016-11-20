import net.liftweb.mapper._
import net.liftweb.util.FieldError

new bootstrap.liftweb.Boot().boot

import code.model._

//10.2.1 Creating data
//Publisher.create.name("Manning").description("What a great company")
//
//Publisher.create.name("Manning").description("Great company").save()
//
//Publisher.create.name("Manning").description("Great company").saveMe()
//
val manning = Publisher.create.name("Manning").saveMe
//
val me: Book = Book.create.title("Lift in ").publisher(manning).saveMe()
val validations: List[(String) => List[FieldError]] = Book.title.validations
//
//manning.books += Book.create.title("Another book")
//manning.books.save

//10.2.2 Querying data
//eg1: by primary key
//val find: Box[Publisher] = Publisher.find(10)

//eg2: find a publisher by the name field
//Publisher.find(By(Publisher.name, "Manning"))
//val by: Cmp[Publisher, String] = By(Publisher.description, "Great company")
//val find: Box[Publisher] = Publisher.find(by)
//Publisher.find(NotBy(Publisher.description, "Great company"))

//eg3 :
//import java.text.{DateFormat => Df}
//Book.find(By(Book.title, "Wind In The Willows"))
//Book.find(By(Book.publishedOn, Df.getDateInstance.parse("March 10, 1908")))
//Author.find(NotBy(Author.email, "demo@demo.com"))
//Publisher.findAll()

//eg4:ManyOtherMethods
//Book.findAll(ByList(Book.title, "Wind In The Willows" :: "Scala in Depth" :: Nil))
//Book.findAll(By_<(Book.publishedOn, Df.getDateInstance.parse("March 10, 1908")))
//Book.findAll(By_>(Book.publishedOn, Df.getDateInstance.parse("March 10, 1908")))
//Book.findAll( BySql("createdat < (CURRENT_DATE - interval '2 days')", IHaveValidatedThisSQL("Hongwei", "2016-11-19")))
//Book.findAll(BySql("id BETWEEN ? AND ?",IHaveValidatedThisSQL("Hongwei", "2016-11-19"),12, 13))

//eg5: full example
//val manning = Publisher.find(By(Publisher.name, "Manning"))
//val name = manning.map(_.name.get).openOr("unknown")
//for {
//  publisher <- manning
//  book <- publisher.books.all
//} print(book.toString())
////  yield book//.title.is.orElse("No Name")
//
////
//val book = Book.find(By(Book.title, "Lift in Action"))
//val or: Serializable = book.flatMap(_.publisher.obj.map(_.name)).openOr("No Publisher")

//eg6: more complex
//Book.findAll(In(Book.publisher, Publisher.id, Like(Publisher.name, "Man%"))).map(_.title)

//Book.findAll(InRaw( Book.publisher,"SELECT id FROM publishers WHERE createdat > (CURRENT_DATE - interval '2 days')", IHaveValidatedThisSQL("Hongwei", "2016-11-20")))
Book.findAll(Like(Book.title, "% in Action"))
Book.findAll(Like(Book.title, "% in Action"))
Book.findAll(Like(Book.title, "% in Action"))//add three
//Book.findAll(StartAt(0),MaxRows(1))
//Book.findAll(OrderBy(Book.title, Ascending))
//Book.findAll(OrderBy(Book.title, Descending))
//Book.findAll( OrderBySql("createdat DESC, title DESC", IHaveValidatedThisSQL("Hongwei", "2016-11-20")))
//Book.find(By(Book.id, 19), PreCache(Book.publisher))

//eg7 : sql
//Author.findAllByInsecureSql(
//  """
//    |SELECT *
//    |FROM authors
//    |WHERE email LIKE '%@demo.com'
//    |OR email LIKE '@another.co.uk'""".stripMargin,
//  IHaveValidatedThisSQL("Hongwei", "2010-1-2"))

//import net.liftweb.mapper.DB
//
//val result = DB.runQuery(
//  """
//    |SELECT *
//    |FROM authors
//    |WHERE email LIKE '%@demo.com'
//    |OR email LIKE '@another.co.uk'""".stripMargin)

//10.2.3 Updating and deleting data
//val manning = Publisher.find(By(Publisher.name, "Manning"))
//update
//manning.map(_.name("Manning Publications").saveMe)
//delete
//manning.map(_.delete_!).openOr(false)

//Book.bulkDelete_!!(Like(Book.title, "%in Action"))