package app.cache;

import java.net.InetSocketAddress;

import app.config.Config;
import app.config.Constants;
import app.utils.Logger;
import net.spy.memcached.MemcachedClient;

public class SingletonMemCache {
	public static MemcachedClient cacheClient;
	public static SingletonMemCache cache;

	private SingletonMemCache() {
		try {
			cacheClient = new MemcachedClient(new InetSocketAddress(Config.MEMCACHE_HOST, Config.MEMCACHE_PORT));
		} catch (Exception meme) {
			meme.printStackTrace();
		}
	}

	public static synchronized SingletonMemCache getMemCacheCon() {
		if (cache == null) {
			cache = new SingletonMemCache();
		}
		return cache;
	}

	public Object getFromCache(String key) {
		if (cacheClient.get(key) != null) {
			Logger.info("Key:" + key + ", found in the Cache. Returning the Cache");
			return cacheClient.get(key);
		} else {
			Logger.info("Key:" + key + ", not found in the Cache. Returning null");
			return null;
		}

	}

	public void addToCache(String key, int exp, Object object) {
		if (cacheClient.get(key) != null) {
			Logger.info("Key:" + key + ", found in the Cache. Updating the Cache");
			cacheClient.add(key, exp, object);
		} else {
			Logger.info("Key:" + key + ", not found in the Cache. Adding to Cache");
			cacheClient.add(key, exp, object);
		}

	}

	public void addToCache(String key, Object object) {
		if (cacheClient.get(key) != null) {
			Logger.info("Key:" + key + ", found in the Cache. Updating the Cache");
			cacheClient.add(key, Constants.CACHE_EXP, object);
		} else {
			Logger.info("Key:" + key + ", not found in the Cache. Adding to Cache");
			cacheClient.add(key, Constants.CACHE_EXP, object);
		}

	}

}
