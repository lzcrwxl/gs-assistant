package com.fbee.modules.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	private static final Logger log = LoggerFactory.getLogger("com.fbee");
	
	public static boolean isDebugEnable() {
		return log.isDebugEnabled();
	}
	
	public static void debug(Object msg) {
		log.debug(msg == null ? "" : msg.toString());
	}
	
	public static void debug(Object msg, Throwable t) {
		log.debug(msg == null ? "" : msg.toString(), t);
	}
	
	public static void info(Object msg) {
		log.info(msg == null ? "" : msg.toString());
	}
	
	public static void info(Object msg, Throwable t) {
		log.info(msg == null ? "" : msg.toString(), t);
	}
	
	public static void warn(Object msg) {
		log.warn(msg == null ? "" : msg.toString());
	}
	
	public static void warn(Object msg, Throwable t) {
		log.warn(msg == null ? "" : msg.toString(), t);
	}
	
	public static void error(Object msg) {
		log.error(msg == null ? "" : msg.toString());
	}
	
	public static void error(Object msg, Throwable t) {
		log.error(msg == null ? "" : msg.toString(), t);
	}
}
