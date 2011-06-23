package org.rioproject.substrates.riospan.ui;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.jini.core.lookup.ServiceItem;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.rioproject.associations.Association;
import org.rioproject.associations.AssociationDescriptor;
import org.rioproject.associations.AssociationManagement;
import org.rioproject.associations.AssociationMgmt;
import org.rioproject.substrates.riospan.HotRodService;
import org.rioproject.substrates.riospan.RioSpanService;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class RioSpanUI extends JFrame {

	static {
		SLF4JBridgeHandler.install();
	}
	
    private static final long serialVersionUID = 1L;

	public RioSpanUI(Object obj) {
		super("RioSpan Client");
		getAccessibleContext().setAccessibleName("RioSpan Client");
		final ServiceItem item = (ServiceItem) obj;
		final HotRodService service = (HotRodService) item.service;
		
		Container content = getContentPane();
		JPanel main = new JPanel(new MigLayout());
		main.add(new JLabel("Infinispan Host:"));
		final String serverAddress;
		final int serverPort;
        try {
	        serverAddress = service.getServerAddress();
			serverPort = service.getServerPort();
        } catch (RemoteException e) {
        	throw new RuntimeException(e);
        }
		final JTextField serverAddressField = new JTextField(serverAddress);
		serverAddressField.setEnabled(false);
		main.add(serverAddressField, new LC().wrap());
		main.add(new JLabel("Infinispan Port:"));
		final JTextField serverPortField = new JTextField(Integer.toString(serverPort));
		serverPortField.setEnabled(false);
		main.add(serverPortField);
		content.add(main);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//				terminate();
			}
		});
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.setSecurityManager(new RMISecurityManager());

		final AssociationManagement associationManager = new AssociationMgmt();
		final AssociationDescriptor descriptor = AssociationDescriptor.create("Infinispan", RioSpanService.class,
		        System.getProperty("user.name"));
		final Association<RioSpanService> association = associationManager.addAssociationDescriptor(descriptor);
		new RioSpanUI(association.getServiceItem());
    }

}
