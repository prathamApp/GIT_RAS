package com.pratham.readandspeak.interfaces;

public interface API_Content_Result {

    void receivedContent(String header, String response);

    void receivedError(String header);

}
