package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone;

import com.jss.smarthome.loxone.smarthomeloxone.config.properties.LoxoneProperties;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LoxoneConfigPayload;
import cz.smarteon.loxone.LoxoneAuth;
import cz.smarteon.loxone.LoxoneHttp;
import cz.smarteon.loxone.LoxoneWebSocket;
import cz.smarteon.loxone.Protocol;
import lombok.Data;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 *
 */
@Data
public class LoxoneConnection {

	private LoxoneWebSocket loxoneWebSocket;
	private LoxoneHttp loxoneHttp;

	public LoxoneConnection(LoxoneProperties loxoneProperties) {
		String address = loxoneProperties.getAddress();
		String user = loxoneProperties.getUser();
		String password = loxoneProperties.getPassword();
		String uiPassword = loxoneProperties.getUipassword();

		Security.addProvider(new BouncyCastleProvider());

		loxoneHttp = new LoxoneHttp(address);
		loxoneWebSocket = new LoxoneWebSocket(address, new LoxoneAuth(loxoneHttp, user, password, uiPassword));
	}

	// TODO need to cache the request that is made -> also this should be a websocket
	// TODO request not http as the library currently does not currently support it
	public LoxoneConfigPayload fetchLoxoneConfig() {
		return loxoneHttp.get(Protocol.C_APP, loxoneWebSocket.getLoxoneAuth(), LoxoneConfigPayload.class);
	}

	public void shutDown() {
		loxoneWebSocket.close();
	}
}
