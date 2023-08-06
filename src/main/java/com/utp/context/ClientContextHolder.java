package com.utp.context;

import com.utp.constant.ClientEnum;

public class ClientContextHolder {
	private static ThreadLocal<ClientEnum> threadLocal = new ThreadLocal<>();
	  
    public static void setClientContext(ClientEnum clientEnum) {
        threadLocal.set(clientEnum);
    }
  
    public static ClientEnum getClientContext() {
        return threadLocal.get();
    }
  
    public static void clearClientContext() {
        threadLocal.remove();
    }
}
