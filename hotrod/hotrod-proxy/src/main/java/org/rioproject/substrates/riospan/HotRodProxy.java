package org.rioproject.substrates.riospan;

import java.rmi.RemoteException;

import net.jini.id.Uuid;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.manager.CacheContainer;
import org.rioproject.resources.servicecore.AbstractProxy;

public class HotRodProxy extends AbstractProxy implements HotRodClientFactory {

	private static final long serialVersionUID = 1L;

	private HotRodProxy(HotRodService service, Uuid id) {
		super(service, id);
	}

	@Override
	public CacheContainer createHotRodCacheContainer() throws RemoteException {
		return new RemoteCacheManager(getServerAddress(), getServerPort());
	}

	@Override
	public String getServerAddress() throws RemoteException {
		return ((HotRodService) server).getServerAddress();
	}

	@Override
	public int getServerPort() throws RemoteException {
		return ((HotRodService) server).getServerPort();
	}
	
	static HotRodProxy getInstance(HotRodService service, Uuid id) {
		return (new HotRodProxy(service, id));
	}

}
