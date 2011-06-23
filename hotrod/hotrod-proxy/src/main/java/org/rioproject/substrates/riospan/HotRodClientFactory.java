package org.rioproject.substrates.riospan;

import java.rmi.RemoteException;

import org.infinispan.manager.CacheContainer;

public interface HotRodClientFactory extends HotRodService {

	public CacheContainer createHotRodCacheContainer() throws RemoteException;

}
