package get
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import GetQueueScenario._

class GetQueueSimulation extends Simulation {

	def apply = {

		val urlBase = System.getProperty("urlBase", "http://localhost:3000")
		println("urlBase = " + urlBase)
		val nbUsers = Integer.getInteger("users", 1)
		println("nbUsers = " + nbUsers)
		val ramp = Integer.getInteger("ramp", 0)

		val httpConf = httpConfig.baseURL(urlBase).disableFollowRedirect

		List(
			GetQueueScenario.scn.configure.users(nbUsers).ramp(ramp).protocolConfig(httpConf))
	}
}
