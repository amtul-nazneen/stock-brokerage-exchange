package app.test;

import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class MemcachedJava {
	public static void main(String[] args) throws Exception {

		MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
		mcc.delete("COMPANY_KEY");
		
	}
}
