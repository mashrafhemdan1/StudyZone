package com.example.mystudyzone;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;


public interface MyJsonService {

    @GET("/1kpjf")
    void listEvents(Callback<List<Event>> eventsCallback);

}
