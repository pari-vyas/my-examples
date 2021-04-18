package com.my.examples.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.my.examples.data.CommandLineArgs;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CovidVaccineAvailability
{
    /*public static void main(String[] args) {
        try {
            new CovidVaccineAvailability().isCovidVaccineAvailable(new CommandLineArgs());
        } catch (Exception e) {

        }
    }*/

    public void isCovidVaccineAvailable(CommandLineArgs args) {
        System.out.println("******************************************************************");
        System.out.println("START: checking if Covid-19 vaccine is available");
        System.out.println("******************************************************************");

        try {
            Runnable worker = new CovidVaccineAvailability.MyRunnable();

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(50);
            executor.scheduleWithFixedDelay(worker, 1, 10, TimeUnit.MINUTES);
            executor.execute(worker);
        } catch (Exception e) {
            System.err.println("Error while checking if class has courseware resource");
            e.printStackTrace();
        }

        System.out.println("\nFinished check vaccine availability");
    }

    public class MyRunnable implements Runnable
    {
        @Override
        public void run() {
            try {
                String urlString = "https://www.cvs.com/immunizations/covid-19-vaccine.vaccine-status.NJ.json?vaccineinfo";

                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                // By default it is GET request
                con.setRequestMethod("GET");
                //add request header
                con.setRequestProperty("referer", "https://www.cvs.com/immunizations/covid-19-vaccine?icid=cvs-home-hero1-link2-coronavirus-vaccine");

                int responseCode = con.getResponseCode();
                System.out.println("Sending get request : " + url);
                System.out.println("Response code : " + responseCode);

                // Reading response from input Stream
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();

                //printing result from response
                System.out.println("response : " + response);
                if(response!=null && response.toString().toLowerCase().contains("available")) {
                    System.out.println(Calendar.getInstance().getTime() + " Covid vaccination slot is available on cvs.com for NJ");
                    final String msg = "Covid vaccination slot is available on cvs.com for NJ";

                    /*AWSCredentials credentials = new BasicAWSCredentials("Access Key Id","Access Key Secret");
                    AmazonSNSClient snsClient = new AmazonSNSClient(credentials);
                    final PublishRequest publishRequest = new PublishRequest("Topic ARN", msg);
                    final PublishResult publishResponse = snsClient.publish(publishRequest);
                    System.out.println("MessageId: " + publishResponse.getMessageId());*/
                } else {
                    System.out.println(Calendar.getInstance().getTime() + " Covid vaccination slot is still NOT AVAILABLE on cvs.com for NJ");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}