package com.comichentai.elasticsearch;


import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.util.CancellableThreads;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by dintama on 16/5/1.
 */

public class TestElasticsearch {

    Client client = null;

    public TestElasticsearch() throws UnknownHostException {


    }

    @Before
    public void init()throws Exception{
        client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9200));
    }

    @Test
    public void testGet(){
        GetResponse getFields = client.prepareGet("comichentai", "comic", null).setOperationThreaded(false).get();

        System.out.println(getFields);

    }

}
