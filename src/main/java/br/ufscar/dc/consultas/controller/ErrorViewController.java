package br.ufscar.dc.consultas.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ErrorViewController implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> map) {

        ModelAndView model = new ModelAndView("error");
        model.addObject("status", status.value());
        switch (status.value()) {
        case 403:
            model.addObject("error", "403");
            model.addObject("message", "Forbidden");
            break;
        case 404:
            model.addObject("error", "404");
            model.addObject("message", "Not found");
            break;
        default:
            model.addObject("error", "Unkown error");
            model.addObject("message", "Some error occurred!");
            break;
        }
        return model;
    }
}