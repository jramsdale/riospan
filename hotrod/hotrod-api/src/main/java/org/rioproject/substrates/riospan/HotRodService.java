package org.rioproject.substrates.riospan;

import java.rmi.RemoteException;

import org.rioproject.substrates.riospan.RioSpanService;

public interface HotRodService extends RioSpanService {

	public String getServerAddress() throws RemoteException;

	public int getServerPort() throws RemoteException;
	
}
