package com.boot_camp.Boot_Camp.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


@Service
public class SaveFileService {

    private final RestTemplate restTemplate ;

    public SaveFileService( ) {
        this.restTemplate = new RestTemplate();
    }

    private final String domain = "http://save:6700";

    public void saveImage(MultipartFile file,String name) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        body.add("name",name);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String path = domain + "/save";
        restTemplate.postForEntity(path, requestEntity, String.class);
    }

    public String saveImage(MultipartFile file) throws IOException {
        String name = String.valueOf(System.currentTimeMillis());
        this.saveImage(file,name);
        return name;
    }



    public ResponseEntity<byte[]> getImages(String fileName) throws IOException {
        String path = domain + "/image/" + fileName;

        URL url = new URL(path);
        try (InputStream inputStream = url.openStream()) {
            byte[] imageBytes = inputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        }
    }
}
