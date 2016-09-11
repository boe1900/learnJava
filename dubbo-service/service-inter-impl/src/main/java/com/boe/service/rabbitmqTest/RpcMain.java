package com.boe.service.rabbitmqTest;

import java.io.IOException;

/**
 * Created by Boe on 2016-09-10.
 */
public class RpcMain {
    public static void main(String[] args) throws Exception {
        RpcClient rpcClient = new RpcClient();
        System.out.println(" [x] Requesting getMd5String(abc)");
        String response = rpcClient.call("abc");
        System.out.println(" [.] Got '" + response + "'");
        rpcClient.close();
    }
}
