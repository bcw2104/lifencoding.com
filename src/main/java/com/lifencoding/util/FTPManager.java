package com.lifencoding.util;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.springframework.beans.factory.annotation.Value;

public class FTPManager {
	@Value("${cdn.host}")
	private String host;
	@Value("${cdn.username}")
	private String username;
	@Value("${cdn.password}")
	private String password;

	public FTPClient connect() {
		FTPClient client = new FTPClient();
		client.setControlEncoding("UTF-8");

		FTPClientConfig config = new FTPClientConfig();
		client.configure(config);

		try {
			client.connect(host, 21);
			if(client.login(username, password)) {
				client.setFileType(FTP.BINARY_FILE_TYPE);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return client;
	}

	public void disconnect(FTPClient client) {
		if (client != null && client.isConnected()) {
			try {
				client.disconnect();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
