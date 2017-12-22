package io.zipkin.brave;

import com.google.auto.value.AutoValue;

import brave.Tracing;

@AutoValue
public abstract class DubboTracing {
	public static DubboTracing create(Tracing tracing) {
		return newBuilder(tracing).build();
	}

	public static Builder newBuilder(Tracing tracing) {
		return new AutoValue_DubboTracing.Builder().tracing(tracing).clientParser(new DubboClientParser())
				.serverParser(new DubboServerParser());
	}

	abstract Tracing tracing();

	abstract DubboClientParser clientParser();

	abstract DubboServerParser serverParser();

	public abstract Builder toBuilder();

	@AutoValue.Builder
	public static abstract class Builder {
		abstract Builder tracing(Tracing tracing);

		public abstract Builder clientParser(DubboClientParser clientParser);

		public abstract Builder serverParser(DubboServerParser serverParser);

		public abstract DubboTracing build();

		Builder() {
		}
	}

	DubboTracing() { // intentionally hidden
	}
}