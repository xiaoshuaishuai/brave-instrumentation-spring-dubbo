package io.zipkin.brave;

import javax.annotation.Generated;

import brave.Tracing;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
public class AutoValue_DubboTracing extends DubboTracing{

	private final Tracing tracing;
	private final DubboClientParser clientParser;
	private final DubboServerParser serverParser;

	private AutoValue_DubboTracing(
	      Tracing tracing,
	      DubboClientParser clientParser,
	      DubboServerParser serverParser) {
	    this.tracing = tracing;
	    this.clientParser = clientParser;
	    this.serverParser = serverParser;
	  }

	@Override
	Tracing tracing() {
		return tracing;
	}

	@Override
	DubboClientParser clientParser() {
		return clientParser;
	}

	@Override
	DubboServerParser serverParser() {
		return serverParser;
	}

	@Override
	public String toString() {
		return "DubboTracing{" + "tracing=" + tracing + ", " + "clientParser=" + clientParser + ", " + "serverParser="
				+ serverParser + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof DubboTracing) {
			DubboTracing that = (DubboTracing) o;
			return (this.tracing.equals(that.tracing())) && (this.clientParser.equals(that.clientParser()))
					&& (this.serverParser.equals(that.serverParser()));
		}
		return false;
	}

	@Override
	public int hashCode() {
		int h = 1;
		h *= 1000003;
		h ^= this.tracing.hashCode();
		h *= 1000003;
		h ^= this.clientParser.hashCode();
		h *= 1000003;
		h ^= this.serverParser.hashCode();
		return h;
	}

	@Override
	public DubboTracing.Builder toBuilder() {
		return new Builder(this);
	}

	static final class Builder extends DubboTracing.Builder {
		private Tracing tracing;
		private DubboClientParser clientParser;
		private DubboServerParser serverParser;

		Builder() {
		}

		private Builder(DubboTracing source) {
			this.tracing = source.tracing();
			this.clientParser = source.clientParser();
			this.serverParser = source.serverParser();
		}

		@Override
		DubboTracing.Builder tracing(Tracing tracing) {
			if (tracing == null) {
				throw new NullPointerException("Null tracing");
			}
			this.tracing = tracing;
			return this;
		}

		@Override
		public DubboTracing.Builder clientParser(DubboClientParser clientParser) {
			if (clientParser == null) {
				throw new NullPointerException("Null clientParser");
			}
			this.clientParser = clientParser;
			return this;
		}

		@Override
		public DubboTracing.Builder serverParser(DubboServerParser serverParser) {
			if (serverParser == null) {
				throw new NullPointerException("Null serverParser");
			}
			this.serverParser = serverParser;
			return this;
		}

		@Override
		public DubboTracing build() {
			String missing = "";
			if (this.tracing == null) {
				missing += " tracing";
			}
			if (this.clientParser == null) {
				missing += " clientParser";
			}
			if (this.serverParser == null) {
				missing += " serverParser";
			}
			if (!missing.isEmpty()) {
				throw new IllegalStateException("Missing required properties:" + missing);
			}
			return new AutoValue_DubboTracing(this.tracing, this.clientParser, this.serverParser);
		}
	}

}