package com.km220.ewelink;

import com.km220.ewelink.model.ws.WssResponse;

public interface WSClientListener {

  void onMessage(WssResponse message);

  void onError(Throwable error);
}
