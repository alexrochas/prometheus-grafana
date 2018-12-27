package br.com.alex.prometheus

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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