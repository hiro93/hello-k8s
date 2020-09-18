package com.example.hellok8s;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloController {
    @GetMapping(path = "version")
    public Version version() {
        Version v = new Version();
        v.setVersion("v0.1");
        v.setHostname(getHostName());
        return v;
    }

    private static String getHostName() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    static class Version implements Serializable {
        private static final long serialVersionUID = -7722085011183665106L;
        private String hostname;
        private String version;

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
