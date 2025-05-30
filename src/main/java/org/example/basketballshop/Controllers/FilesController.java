package org.example.basketballshop.Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.basketballshop.Services.ImageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FilesController {
    @Autowired
    private ImageInfoService imageInfoService;

    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        imageInfoService.writeImageToResponse(fileName, response);
    }
}
