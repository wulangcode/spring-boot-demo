package com.wulang.demo.zuul.gateway.model;

public class GatewayApiRoute {
 
	private String id;
	private String path;
	private String serviceId;
	private String url;
	/**
	 * 带前缀
	 */
	private boolean stripPrefix = true;
	/**
	 * 可重试
	 */
	private Boolean retryable;
	/**
     * 已启用
	 */
	private Boolean enabled;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isStripPrefix() {
		return stripPrefix;
	}
	public void setStripPrefix(boolean stripPrefix) {
		this.stripPrefix = stripPrefix;
	}
	public Boolean getRetryable() {
		return retryable;
	}
	public void setRetryable(Boolean retryable) {
		this.retryable = retryable;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
