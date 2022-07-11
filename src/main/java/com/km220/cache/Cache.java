package com.km220.cache;

import java.util.UUID;

public interface Cache<T> {

  T get(UUID id);

  T put(UUID id, T value);
}
