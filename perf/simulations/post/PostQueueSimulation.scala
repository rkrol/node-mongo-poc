package post
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import PostQueueScenario._

class PostQueueSimulation extends Simulation {

	def apply = {

		val urlBase = System.getProperty("urlBase", "http://localhost:3000")
		println("urlBase = " + urlBase)
		val nbUsers = Integer.getInteger("users", 1)
		println("nbUsers = " + nbUsers)
		val ramp = Integer.getInteger("ramp", 0)

		val httpConf = httpConfig.baseURL(urlBase).disableFollowRedirect

		List(
			PostQueueScenario.scn.configure.users(nbUsers).ramp(ramp).protocolConfig(httpConf))
	}
}
