package com.codeup.autoconnect.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Keys {

    @Value("${MAPBOX_KEY}")
    private String MAPBOX_KEY;
    @Value("${API_KEY1}")
    private String API_KEY1;

    @Value("${FILESTACK_KEY}")
    private String FILESTACK_KEY;


    public String getFILESTACK_KEY() {
        return FILESTACK_KEY;
    }

    public void setFILESTACK_KEY(String FILESTACK_KEY) {
        this.FILESTACK_KEY = FILESTACK_KEY;
    }

    public String getMAPBOX_KEY() {
        return MAPBOX_KEY;
    }

    public void setMAPBOX_KEY(String MAPBOX_KEY) {
        this.MAPBOX_KEY = MAPBOX_KEY;
    }

    public String getAPI_KEY1() {
        return API_KEY1;
    }

    public void setAPI_KEY1(String API_KEY1) {
        this.API_KEY1 = API_KEY1;
    }
}
