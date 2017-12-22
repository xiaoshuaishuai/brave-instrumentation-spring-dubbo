brave-instrumentation-spring-dubbo
===
官方 https://github.com/openzipkin/brave/tree/master/instrumentation  扩展

使用方式：

		1.pom.xml
				<dependency>
						<groupId>io.zipkin.brave</groupId>
						<artifactId>brave-instrumentation-spring-dubbo</artifactId>
						<version>1.0.0</version>
				</dependency>
    
		2.application.properties
		dubbo.zipkin.enabled=true
		dubbo.zipkin.serverName=my-provider-01
		dubbo.zipkin.endpoint=http://localhost:9411/api/v2/spans
		dubbo.zipkin.rate=1.0f

		3.
		@Configuration
		public class TracingConfiguration {
			@Autowired
			TraceConfig config;

			@Bean
			public Tracing tracing() {
				return Tracing.newBuilder().localServiceName(config.getServerName()).spanReporter(spanReporter())
						.sampler(Sampler.create(config.getRate())).build();
			}

			@Bean
			HttpTracing httpTracing(Tracing tracing) {
				return HttpTracing.create(tracing);
			}

			@Bean
			public AsyncReporter<Span> spanReporter() {
				return AsyncReporter.create(sender());
			}

			@Bean
			public Sender sender() {
				return OkHttpSender.create(config.getEndpoint());
			}

			@Bean
			DubboTracing dubboTracing(Tracing tracing) {
				return DubboTracing.create(tracing);
			}
		}
		4.访问zipkin web-ui (http://localhost:9411/zipkin)


[代码参考](https://github.com/xiaoshuaishuai/springboot-dubbo-zipkin-brave) 
