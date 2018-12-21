package com.juja.controller.web;

import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {
    boolean canProcess(String url);

    void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException;
    void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException;
}
