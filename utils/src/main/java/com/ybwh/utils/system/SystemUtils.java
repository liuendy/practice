package com.ybwh.utils.system;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class SystemUtils {
	private static int PID = -1;

	/**
	 * 获取进程ID
	 * 
	 * @return
	 */
	public static int getPid() {
		if (PID < 0) {
			try {
				RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
				String name = runtime.getName(); // format: "pid@hostname"
				PID = Integer.parseInt(name.substring(0, name.indexOf('@')));
			} catch (Throwable e) {
				PID = 0;
			}
		}
		return PID;
	}

}
