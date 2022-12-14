package ru.duremika.core.engine;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cache<K, V> {
    private static final long DEFAULT_LIFE_TIME = TimeUnit.DAYS.toMillis(1);

    private volatile ConcurrentHashMap<Key, V> globalMap;
    private final ScheduledExecutorService scheduler;

    {
        globalMap = new ConcurrentHashMap<>();
        scheduler = Executors.newSingleThreadScheduledExecutor(
                runnable -> new Thread(runnable) {{
                    setDaemon(true);
                }});
    }

    public Cache() {
        Runnable command = () -> {
            long now = System.currentTimeMillis();
            for (Key key : globalMap.keySet()) {
                if (!key.isLive(now)) {
                    globalMap.remove(key);
                }
            }
        };
        scheduler.scheduleAtFixedRate(
                command,
                1,
                DEFAULT_LIFE_TIME / 4,
                TimeUnit.MILLISECONDS
        );
    }

    public V put(K key, V data) {
        return globalMap.put(new Key(key), data);
    }

    public void setAll(Map<K, V> newMap) {
        globalMap = new ConcurrentHashMap<>();
        addAll(newMap);
    }

    public void addAll(Map<K, V> expMap) {
        expMap.forEach(this::put);
    }

    public V getOrDefaultWithSave(K key, V defaultValue) {
        V res = globalMap.get(new Key(key));
        if (res == null) {
            put(key, defaultValue);
            res = defaultValue;
        }
        return res;
    }

    public V remove(K key) {
        return globalMap.remove(new Key(key));
    }

    public void clear() {
        globalMap.clear();
    }

    @EqualsAndHashCode
    private static class Key {
        @Getter
        private final Object key;

        @EqualsAndHashCode.Exclude
        private final long lifeTime;

        public Key(Object key) {
            this.key = key;
            this.lifeTime = System.currentTimeMillis() + DEFAULT_LIFE_TIME;
        }

        public boolean isLive(long currentTimeMillis) {
            return currentTimeMillis < lifeTime;
        }
    }
}
