import org.infinispan.config.InfinispanConfiguration
import org.rioproject.config.Component
import org.rioproject.entry.UIDescriptorFactory
import org.rioproject.resolver.ResolverHelper
import org.rioproject.resolver.Resolver
import net.jini.core.entry.Entry

@Component('org.rioproject.substrates.riospan')
class RioSpanConfig {

	Entry[] getServiceUIs(String codebase) {
		def entry = []
		if(codebase!=null) {
			Resolver r = ResolverHelper.getInstance()
			String uiClass = 'org.rioproject.substrates.riospan.ui.RioSpanIntro'
			def classpath = []
			for(String s : r.getClassPathFor("org.rioproject.substrates.riospan:hotrod-ui:1.0.0-SNAPSHOT",
											 (File)null,
											 true)) {
				if(s.startsWith(ResolverHelper.M2_HOME))
					s = s.substring(ResolverHelper.M2_HOME.length()+1)
				classpath << s
			}
			entry = [UIDescriptorFactory.getJComponentDesc(codebase,
														   classpath as String[],
														   uiClass)]
		}
		return entry as Entry[]
	}
	
	InfinispanConfiguration getInfinispanConfiguration() {
		return InfinispanConfiguration.newInfinispanConfiguration('infinispan-config.xml', null)
	}
	
}