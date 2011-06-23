package org.rioproject.substrates.riospan;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Properties;

import net.jini.id.Uuid;
import net.jini.id.UuidFactory;

import org.infinispan.server.core.Main;
import org.infinispan.server.hotrod.HotRodServer;
import org.rioproject.bean.CreateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotRodServiceImpl extends RioSpanServiceImpl implements HotRodService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private HotRodServer hotRodServer;
	private HotRodProxy proxy;

	private Properties properties = new Properties();

	@CreateProxy
	public HotRodProxy createProxy(HotRodService exported) {
		final Uuid uuid = UuidFactory.generate();
		proxy = HotRodProxy.getInstance(exported, uuid);
		return proxy;
	}

	@Override
	protected void init() {
		hotRodServer = new HotRodServer();
		try {
			properties.put(Main.PROP_KEY_HOST(), getInnerServerAddress());
		} catch (UnknownHostException e) {
			throw new RuntimeException("Failed to get server address", e);
		}
		properties.put(Main.PROP_KEY_PORT(), getInnerServerPort());
	}

	@Override
	protected void startup() {
		logger.info("Starting Hot Rod Server");
		hotRodServer.start(properties, getEmbeddedCacheManager());
	}

	@Override
	protected void shutdown() {
		logger.info("Stopping Hot Rod Server");
		hotRodServer.stop();
	}

	private String getInnerServerAddress() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	@Override
	public String getServerAddress() throws RemoteException {
		try {
			return getInnerServerAddress();
		} catch (UnknownHostException e) {
			throw new RemoteException("Bad Host", e);
		}
	}

	private int getInnerServerPort() {
		return 11222;
	}

	@Override
	public int getServerPort() throws RemoteException {
		return getInnerServerPort();
	}

}
