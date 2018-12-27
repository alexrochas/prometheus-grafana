package br.com.alex.prometheus

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EchoApi(val prometheusMetrics: PrometheusMetrics) {

    @GetMapping("/echo")
    fun echo(): String {
        prometheusMetrics.count()
        return "echo"
    }
}
