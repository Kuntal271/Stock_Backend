package com.spring.service;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateStocksService {

    public void updateStocksFromCSV(MultipartFile file) throws Exception;

}
