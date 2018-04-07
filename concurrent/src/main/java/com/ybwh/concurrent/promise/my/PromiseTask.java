package com.ybwh.concurrent.promise.my;

import java.util.concurrent.Callable;

public interface PromiseTask {
	Promise excute(Runnable command);
	<V> Promise excute(Callable<V> command);
}
