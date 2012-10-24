package get
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import scala.collection.mutable

object GetQueueScenario {

	val scn = scenario("Send GET requests to Queue Manager")
		.pause(0, 1000, MILLISECONDS)
		.loop(
			chain
				.exec(
					http("GET request")
						.get("/get/")
						.check(status.is(200))))
		.times(300)

}
