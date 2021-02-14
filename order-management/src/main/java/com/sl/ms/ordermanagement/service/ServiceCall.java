package com.sl.ms.ordermanagement.service;
import java.util.Map;
import java.util.UUID;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ServiceCall {

    @Autowired
    RestTemplate restTemplate;

    @Value("${rest.url}")
    private String url;

    @Value("${rest.token}")
    private String token;

    @HystrixCommand(fallbackMethod = "reliable")
    public Object callInventoryMgmt(int productid, String uuid) {
        int quantity = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.add("UUID",uuid);
        HttpEntity<String> entity = new HttpEntity<>(headers);

       try {
            ResponseEntity<Map> ent = restTemplate.exchange(url + "/{productid}", HttpMethod.GET, entity, Map.class,
                    productid);
            Object jsonObject = new JSONObject(ent.getBody());
            return jsonObject;
        } catch (Exception e) {
            return e;
        }
    }
    public String reliable(int productid, String uuid) {
        return "Inventory Service is Down....Reply from Hystrix UUID: "+ uuid ;
    }

}
