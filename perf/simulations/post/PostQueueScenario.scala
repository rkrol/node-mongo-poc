package post
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import scala.collection.mutable

object PostQueueScenario {

	val scn = scenario("Send POST requests to Queue Manager")
		.loop(
			chain
			  .pause(1)
				.exec(
					http("POST request")
						.get("/post/")
						.check(status.is(200))))
		.times(1000)

}
