package by.tms.korzun.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class VersionController {

    private final BuildProperties properties;

    @Autowired
    public VersionController(BuildProperties properties) {
        this.properties = properties;
    }

    @GetMapping(path = "/version")
    public String getVersion() {
        return properties.getVersion();
    }
}
