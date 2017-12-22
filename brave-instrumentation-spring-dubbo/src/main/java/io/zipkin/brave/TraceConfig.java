package io.zipkin.brave;

public class TraceConfig {

	private TraceConfigProperties configProperties;

	public TraceConfig(TraceConfigProperties configProperties) {
		this.configProperties = configProperties;
	}

	public TraceConfig() {
	}

	public String getServerName() {
		return configProperties.getServerName();
	}

	public float getRate() {
		return configProperties.getRate();
	}

	public String getEndpoint() {
		return configProperties.getEndpoint();
	}

	@Override
	public String toString() {
		return "TraceConfig [configProperties=" + configProperties + "]";
	}
	
	
}
