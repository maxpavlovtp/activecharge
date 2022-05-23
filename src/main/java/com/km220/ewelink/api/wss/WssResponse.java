package com.km220.ewelink.api.wss;


import com.km220.ewelink.api.wss.wssrsp.WssRspMsg;

public interface WssResponse {

    void onMessage(String s);

    void onMessageParsed(WssRspMsg rsp);

    void onError(String error);
}
