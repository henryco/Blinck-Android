package net.henryco.blinck.groups.login.broker;

/**
 * Created by HenryCo on 20/08/17.
 */

interface BrokerResultHandler<T, E extends Exception> {

	void process(BrokerResult<T, E> brokerResult);
}
