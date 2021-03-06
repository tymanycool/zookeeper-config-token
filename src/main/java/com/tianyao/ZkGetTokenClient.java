package com.tianyao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tianyao.entity.TokenConfig;
import com.tianyao.lock.SimpleDistributedLockMutex;
import com.tianyao.lock.ZkClientExt;
import com.tianyao.util.DateUtil;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("prototype")
public class ZkGetTokenClient implements Runnable{
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ZkTokenManager zkTokenManager;

    private ZkClientExt zk = new ZkClientExt("192.168.33.128:2181");

    private TokenConfig config;

    public TokenConfig getConfig() {
//        ZkClient zk = new ZkClient("192.168.33.128:2181");
        // 如果不存在就向localhost:8080/applyToken 申请token
        // 返回数据格式：{"token":"ed1cd3c6-769c-4bd5-879a-aa9562a9c810","expdate":"20180511000742"}
        if(!zk.exists("/zkConfig")){
          applyToekn();
        }else {
            config = (TokenConfig)zk.readData("/zkConfig");
            System.out.println("加载到配置："+config.toString());
            // token 过期
            if(config.getExpDate().compareTo(DateUtil.getCurrentTime())<0){
                TokenConfig tokenConfig = zkTokenManager.downLoadConfigFromDB();
                // 如果数据库的token是不可以用的（过期的），说明这是token过期后最先过来的请求
                if(tokenConfig.getExpDate().compareTo(DateUtil.getCurrentTime())<0){
                    applyToekn();
                }else {
                    // 数据库的token 已经被前面的请求刷新好了，直接用数据库的token就可以了
                    config = tokenConfig;
                }
            }
        }
        //监听配置文件修改
        zk.subscribeDataChanges("/zkConfig", new IZkDataListener(){
            @Override
            public void handleDataChange(String arg0, Object arg1)
                    throws Exception {
                config = (TokenConfig) arg1;
                System.out.println("监听到配置文件被修改："+config.toString());
            }

            @Override
            public void handleDataDeleted(String arg0) throws Exception {
                config = null;
                System.out.println("监听到配置文件被删除");
            }
        });
        return config;
    }
    //申请token
    private void applyToekn() {
        // 添加分布式锁
        SimpleDistributedLockMutex lock = new SimpleDistributedLockMutex(zk, "/tokenlock");
        try {
            lock.acquire();
            System.out.println("获取token，，，，，添加分布式锁。。。。。");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = restTemplate.getForObject("http://localhost:8080/applyToken", String.class);
        JSONObject jsonObject = (JSONObject) JSON.parse(str);
        String token = (String)jsonObject.get("token");
        String expdate = (String)jsonObject.get("expdate");
        if(config==null) config = new TokenConfig();
        config.setTokenValue(token);
        config.setExpDate(expdate);

        // 等待10s=====test
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //ZkTokenManager zkTokenManager = new ZkTokenManager();
        // 更新数据库
        zkTokenManager.upLoadConfigToDB(token,expdate);
        // 同步配置到zookeeper
        zkTokenManager.syncConfigToZk();
        System.out.println("zookeeper没有token配置，调用网状网初始化.....");

        // 释放分布式锁
        try {
            lock.release();
            System.out.println("获取token，，，，，释放分布式锁。。。。。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //ZkGetTokenClient client = new ZkGetTokenClient();

        while (true){
            config = getConfig();
            System.out.println(config.toString());
            System.out.println(Thread.currentThread()+"======"+DateUtil.getCurrentTime()+"test:====:"+config.toString()+"zkClient====:" +zk.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        ZkGetTokenClient client = new ZkGetTokenClient();
//        client.getConfig();
//        System.out.println(client.config.toString());
//        while (true){
//            System.out.println(DateUtil.getCurrentTime()+"test:====:"+client.config.toString());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
 //   }

}
