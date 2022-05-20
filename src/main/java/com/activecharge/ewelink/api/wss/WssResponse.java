package com.activecharge.ewelink.api.wss;


import com.activecharge.ewelink.api.wss.wssrsp.WssRspMsg;

public interface WssResponse {

    void onMessage(String s);

    void onMessageParsed(WssRspMsg rsp);

    void onError(String error);
}
