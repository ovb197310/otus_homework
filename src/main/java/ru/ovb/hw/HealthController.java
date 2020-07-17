package ru.ovb.hw;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@Slf4j
public class HealthController {
    @RequestMapping(path = "/health", method = RequestMethod.GET, produces = "application/json")
    public HealthStatus healthStatus() {
        return new HealthStatus(HealthStatus.HealthStatuses.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = "text/plain")
    public String getInfo() {
        try {
            return "App run on: " + InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("On getHostName: {}", e.getMessage());
            return e.getMessage();
        }
    }

}
