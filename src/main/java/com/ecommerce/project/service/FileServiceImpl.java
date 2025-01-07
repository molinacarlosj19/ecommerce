package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

        // Ensure the directory exists
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Create the full file path
        String filePath = path + File.separator + fileName;

        Files.copy(file.getInputStream(), Paths.get(filePath));
        File destinationFile = new File(filePath);

        // Copy the file content
        Files.copy(file.getInputStream(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

}
