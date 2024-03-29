package bg.softuni.myhome.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

@Service
public class CloudinaryService {

    private final static String TEMP_FILE = "temp-file";
    private final static String URL = "url";

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadPicture(MultipartFile multipartFile) {String url = "";
        try {
            File file = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);

            return this.cloudinary
                    .uploader()
                    .upload(file, Collections.emptyMap())
                    .get(URL)
                    .toString();
        } catch (IOException e){
            throw new NullPointerException("File is empty");
        }
    }


}
