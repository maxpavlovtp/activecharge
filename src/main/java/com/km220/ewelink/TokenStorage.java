package com.km220.ewelink;

import java.util.function.Supplier;

public interface TokenStorage {

  String get(Supplier<String> tokenExtractor);

  String refresh(Supplier<String> tokenExtractor, String oldToken);
}
