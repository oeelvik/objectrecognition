package no.iprog.ml.objectrecognition.webapp.resources;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import no.iprog.ml.objectrecognition.api.FileService;
import no.iprog.ml.objectrecognition.api.FileStatus;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Øystein Schrøder Elvik
 */
@Controller
@RequestMapping("/image")
public class ImageResource {
    @Autowired
    private FileService fileService;
    
    
    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void listImages(HttpServletResponse response) throws IOException{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        ServletOutputStream out = response.getOutputStream();
        out.write("[".getBytes());
        
        boolean first = true;
        for (FileStatus file : fileService.listFiles("images")) {
            if(!file.isDir()) continue;
            
            if(first) {
                first = false;
            } else {
                out.write(", ".getBytes());
            }
            out.write(("{ \"name\" : \"" + file.getName() + "\"}").getBytes());
        }
        
        out.write("]".getBytes());
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            String name = UUID.randomUUID().toString() + ".jpg";
            
            fileService.write("images/" + name, file.getBytes());
            
            return "[{\"name\": \"" + name + "\"}]";
        }
        return "[]";
    }
    
    @RequestMapping(
            value = "/{image:.*}",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable("image") String image) throws IOException {
        return IOUtils.toByteArray(fileService.openInputStream("images/" + image));
    }
}