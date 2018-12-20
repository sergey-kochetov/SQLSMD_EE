package com.juja.service;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceFactory {
    @Autowired
    private Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
