package com.arzirtime.support.socket;

import java.util.HashMap;

public class ConnectManager {

    private static ConnectManager instance = null;
    private HashMap<String, TcpConnectClient> connectClients = new HashMap<String, TcpConnectClient>();

    private ConnectManager() {
    }

    public static ConnectManager getInstance() {
        if (instance == null) {
            instance = new ConnectManager();
        }
        return instance;
    }

    /**
     * 创建 ConnectClient
     */
    public TcpConnectClient CreateConnectClient(String ip, int port) {
        return CreateConnectClient(ip, port, NetConstanct.SOCKET_TIMEOUT);
    }

    /**
     * 创建 ConnectClient
     */
    public TcpConnectClient CreateConnectClient(String ip, int port, int timeout) {
        TcpConnectClient client = new TcpConnectClient(ip, port, timeout);
        connectClients.put(client.getId(), client);

        return client;
    }

    /**
     * 根据Id获取 ConnectClient
     */
    public TcpConnectClient findConnectClientById(String connectClientId) {
        if (connectClients.containsKey(connectClientId)) {
            return connectClients.get(connectClientId);
        } else {
            return null;
        }
    }

}

