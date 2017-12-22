package io.zipkin.brave;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dubbo.zipkin")
public class TraceConfigProperties {


	private String serverName = "UNKNOWN";

	private String endpoint = "http://localhost:9411/api/v2/spans";

	private float rate = 1.0f;


	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public String toString() {
		return "TraceConfigProperties [serverName=" + serverName + ", endpoint=" + endpoint + ", rate=" + rate + "]";
	}


}
