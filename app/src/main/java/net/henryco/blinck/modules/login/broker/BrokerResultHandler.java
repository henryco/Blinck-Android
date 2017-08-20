package net.henryco.blinck.modules.login.broker;

/**
 * Created by HenryCo on 20/08/17.
 */

public interface BrokerResultHandler<T, E extends Exception> {

	void process(BrokerResult<T, E> brokerResult);
}
