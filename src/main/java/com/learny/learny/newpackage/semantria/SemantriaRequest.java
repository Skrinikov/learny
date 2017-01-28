package com.learny.learny.newpackage.semantria;

import java.util.List;

/**
 * Class that will take care of the communication in-between the web application
 * and the Semantria API.
 *
 * @author Uen Yi Cindy Hung
 * @since January 28, 2017
 */
public class SemantriaRequest {

    private SemantriaAPI api;

    /**
     * Default constructor
     */
    public SemantriaRequest() {
        api = new SemantriaAPI();
    }

    public List<String> requestToSemantria(String text) {
        List<String> response = null;

        return response;
    }

}
