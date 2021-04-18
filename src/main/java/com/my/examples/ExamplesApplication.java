package com.my.examples;

import com.my.examples.data.CommandLineArgs;
import com.my.examples.service.CovidVaccineAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamplesApplication implements ApplicationRunner
{
    @Autowired
    private CovidVaccineAvailability covidVaccineAvailability;

    @Autowired
    private CommandLineArgs commandLineArgs;

    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(ExamplesApplication.class);
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        System.out.println("commandLineArgs.getName()==>" +commandLineArgs.getName());
        String name = commandLineArgs.getName();
        switch (name.toLowerCase()) {
            case "covidvaccineavailability":
                covidVaccineAvailability.isCovidVaccineAvailable(commandLineArgs);
                break;
            default:
                System.out.println("NOTHING TO WORK HERE");
        }
    }
}
