package org.rioproject.substrates.riospan;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStarted;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStopped;
import org.infinispan.notifications.cachemanagerlistener.annotation.ViewChanged;
import org.infinispan.notifications.cachemanagerlistener.event.CacheStartedEvent;
import org.infinispan.notifications.cachemanagerlistener.event.CacheStoppedEvent;
import org.infinispan.notifications.cachemanagerlistener.event.ViewChangedEvent;
import org.rioproject.watch.GaugeWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Listener
public class RioSpanListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final GaugeWatch numNodes;
	private final GaugeWatch numCaches;

	public RioSpanListener(GaugeWatch numNodes, GaugeWatch numCaches) {
		this.numNodes = numNodes;
		this.numCaches = numCaches;
	}

	@CacheStarted
	public void cacheStarted(CacheStartedEvent event) {
		logger.info("Infinispan cache started: {}", event.getCacheName());
		numCaches.addValue(1);
	}

	@CacheStopped
	public void cacheStopped(CacheStoppedEvent event) {
		logger.info("Infinispan cache stopped: {}", event.getCacheName());
		numCaches.addValue(-1);
	}

	@ViewChanged
	public void viewChanged(ViewChangedEvent event) {
		logger.info("Infinispan cluster membership changed: {}", event.getCacheManager().getClusterName());
		numNodes.addValue(event.getOldMembers().size() - event.getNewMembers().size());
	}

}
