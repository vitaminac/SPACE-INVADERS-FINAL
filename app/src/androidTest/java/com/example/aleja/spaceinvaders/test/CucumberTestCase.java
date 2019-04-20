package com.example.aleja.spaceinvaders.test;

import cucumber.api.CucumberOptions;

@CucumberOptions(
        features = {"features"},
        plugin = {"pretty"},
        glue = {"com.example.aleja.spaceinvaders"},
        tags = {"@e2e", "@smoke"}
)
@SuppressWarnings("unused")
public class CucumberTestCase {
}