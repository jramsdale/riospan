package org.rioproject.substrates.riospan.examples.hotrod;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.rioproject.associations.Association;
import org.rioproject.associations.AssociationDescriptor;
import org.rioproject.associations.AssociationManagement;
import org.rioproject.associations.AssociationMgmt;
import org.rioproject.substrates.riospan.HotRodClientFactory;

public class HotRodClient {

	public static void main(String[] args) throws InterruptedException, ExecutionException, RemoteException {
		System.setSecurityManager(new RMISecurityManager());

		final AssociationManagement associationManager = new AssociationMgmt();
		final AssociationDescriptor descriptor = AssociationDescriptor.create("Hot Rod Server", HotRodClientFactory.class,
		        System.getProperty("user.name"));
		final Association<HotRodClientFactory> association = associationManager.addAssociationDescriptor(descriptor);
		final HotRodClientFactory rioSpanService = association.getServiceFuture().get();
		final CacheContainer cacheContainer = rioSpanService.createHotRodCacheContainer();

		final Cache<Object, Object> cache = cacheContainer.getCache();
		if (cache.containsKey("hello")) {
			System.out.println("Found existing value: " + cache.get("hello"));
		} else {
			cache.put("hello", "world");
			System.out.println(cache.get("hello"));
		}
		associationManager.terminate();
	}
	
}
