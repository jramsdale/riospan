import java.util.logging.Level
import org.rioproject.config.Constants

deployment(name:'RioSpan') {

	/* Configuration for the discovery group that the service should join.
	 * This first checks if the org.rioproject.groups property is set, if not
	 * the user name is used */
	groups System.getProperty(Constants.GROUPS_PROPERTY_NAME,
			System.getProperty('user.name'))

	artifact id:'client', 'org.rioproject.substrates.riospan:hotrod-proxy:1.0.0-SNAPSHOT'
	artifact id:'service', 'org.rioproject.substrates.riospan:hotrod-service:1.0.0-SNAPSHOT'

	logging { logger 'org.rioproject.substrates', Level.FINE }

	service(name: 'Hot Rod Server') {
		interfaces {
			classes 'org.rioproject.substrates.riospan.HotRodClientFactory'
			artifact ref:'client'
		}
		implementation(
				class:'org.rioproject.substrates.riospan.HotRodServiceImpl') { artifact ref:'service' }
		maintain 1
		maxPerMachine 1
		configuration file: 'classpath:HotRodConfig.groovy'
		
		comment 'Infinispan'
	}

}