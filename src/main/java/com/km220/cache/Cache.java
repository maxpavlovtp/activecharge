package com.km220.cache;

public interface Cache<K, V> {

  V get(K id);

  V put(K id, V value);
}
