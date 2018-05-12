package com.tianyao.lock;

import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;

public class ZkClientExt extends ZkClient {
    public ZkClientExt(String serverstring) {
        super(serverstring);
    }

    public ZkClientExt(String zkServers, int connectionTimeout) {
        super(zkServers, connectionTimeout);
    }

    public ZkClientExt(String zkServers, int sessionTimeout, int connectionTimeout) {
        super(zkServers, sessionTimeout, connectionTimeout);
    }

    public ZkClientExt(String zkServers, int sessionTimeout, int connectionTimeout, ZkSerializer zkSerializer) {
        super(zkServers, sessionTimeout, connectionTimeout, zkSerializer);
    }

    public ZkClientExt(String zkServers, int sessionTimeout, int connectionTimeout, ZkSerializer zkSerializer, long operationRetryTimeout) {
        super(zkServers, sessionTimeout, connectionTimeout, zkSerializer, operationRetryTimeout);
    }

    public ZkClientExt(IZkConnection connection) {
        super(connection);
    }

    public ZkClientExt(IZkConnection connection, int connectionTimeout) {
        super(connection, connectionTimeout);
    }

    public ZkClientExt(IZkConnection zkConnection, int connectionTimeout, ZkSerializer zkSerializer) {
        super(zkConnection, connectionTimeout, zkSerializer);
    }

    public ZkClientExt(IZkConnection zkConnection, int connectionTimeout, ZkSerializer zkSerializer, long operationRetryTimeout) {
        super(zkConnection, connectionTimeout, zkSerializer, operationRetryTimeout);
    }
}
