# Prometheus with Grafana
> Minor POC to test time based metrics alternatives

## How

Like a cake recipe.

1. Simple Kotlin/Spring Boot app
2. Prometheus server up and running
3. Grafana container (because install in Mac **sucks**).

### Kotlin/Spring Boot app

After a working rest endpoint just add those dependencies:

```groovy
dependencies {
  // ...
  implementation("io.micrometer:micrometer-core")
  implementation("io.micrometer:micrometer-registry-prometheus")
  // ...
}
```

and this parameters to *application.properties*:

```properties
#Metrics related configurations
management.endpoint.metrics.enabled=true
management.endpoints.web.exposure.include=*
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
```

This will add endpoints to actuar and expose metrics through a rest api.

In the code, create objects to collect the metrics like:

```kotlin
@Component
class PrometheusMetrics(@Autowired val meterRegistry: MeterRegistry) {
   private final val callCounter : Counter = Counter
             .builder("echocounter")
             .description("this is an echo counter")
             .register(meterRegistry)

    fun count() {
        println("Count!")
         callCounter.increment()
    }
}
```

once ```count()``` is called, it will add ONE in the prometheus metrics.

### Prometheus

[Download](https://prometheus.io/download/) Prometheus and create a config file as bellow:

```yml
#App Config file
#Global configurations
global:
  scrape_interval:     5s # Set the scrape interval to every 5 seconds.
  evaluation_interval: 5s # Evaluate rules every 5 seconds.
scrape_configs:
  - job_name: 'echo-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```

now start it `./prometheus --config.file=app-config.yml`.

### Grafana

Start with docker-compose and add a new Prometheus Datasource. (simple right?)

## Meta

Alex Rocha - [about.me](http://about.me/alex.rochas)
