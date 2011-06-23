package org.rioproject.substrates.riospan;

import net.jini.config.ConfigurationException;

import org.infinispan.config.InfinispanConfiguration;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.rioproject.bean.Initialized;
import org.rioproject.bean.PreDestroy;
import org.rioproject.bean.Started;
import org.rioproject.core.jsb.ServiceBeanContext;
import org.rioproject.jsb.ServiceBeanAdapter;
import org.rioproject.watch.GaugeWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public abstract class RioSpanServiceImpl extends ServiceBeanAdapter {

	static {
		SLF4JBridgeHandler.install();
	}
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String COMPONENT = RioSpanServiceImpl.class.getPackage().getName();

	private DefaultCacheManager cacheManager;
	private InfinispanConfiguration infinispanConfiguration;

	private RioSpanListener rioSpanListener;

	public void setServiceBeanContext(ServiceBeanContext context) throws ConfigurationException {
		infinispanConfiguration = (InfinispanConfiguration) context.getConfiguration().getEntry(COMPONENT, "infinispanConfiguration",
		        InfinispanConfiguration.class);

		final GaugeWatch numNodes = new GaugeWatch("numNodes");
		final GaugeWatch numCaches = new GaugeWatch("numCaches");
		rioSpanListener = new RioSpanListener(numNodes, numCaches);
		context.getWatchRegistry().register(numNodes);
		context.getWatchRegistry().register(numCaches);
	}

	@Initialized
	public void initialized() {
		System.setProperty("java.net.preferIPv4Stack", "true");
		init();
	};

	protected abstract void init();

	@Started
	public void started() {
		logger.info("Creating RioSpanService CacheManager");
		cacheManager = new DefaultCacheManager(infinispanConfiguration.parseGlobalConfiguration(), infinispanConfiguration.parseDefaultConfiguration());
//		cacheManager.addListener(rioSpanListener);
		startup();
	};

	protected abstract void startup();
	
	@PreDestroy
	public void preDestroy() {
		shutdown();
		cacheManager.stop();
	};

	protected abstract void shutdown();

	protected InfinispanConfiguration getInfinispanConfiguration() {
		return infinispanConfiguration;
	}

	protected EmbeddedCacheManager getEmbeddedCacheManager() {
		return cacheManager;
	}
	
}

