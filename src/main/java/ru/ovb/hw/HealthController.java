package ru.ovb.hw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class HealthController {

    @Autowired
    private Environment env;

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

    @RequestMapping(path = "/env", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> getEnv() {
        Map<String, String> rtn = new HashMap<>();
        if (env instanceof ConfigurableEnvironment) {
            for (PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
                if (propertySource instanceof EnumerablePropertySource) {
                    for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                        rtn.put(key, String.valueOf(propertySource.getProperty(key)));
                    }
                }
            }
        }
        return rtn;
    }

}
