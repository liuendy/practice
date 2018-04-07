package com.ybwh.concurrent.promise.my;

public interface Promise {
	<T> void resolve(T result);

	<T> void reject(T result);

	<T> void complete(ResultType type, T result);

	<T> void then(ResultType type, T result);

	static enum ResultType {
		/**
		 * 未完成
		 */
		unfulfilled, 
		/**
		 * 已完成
		 */
		fulfilled,
		/**
		 * 失败
		 */
		failed
	}
}
