package no.iprog.ml.objectrecognition.webapp.resources;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import no.iprog.ml.objectrecognition.api.FileService;
import no.iprog.ml.objectrecognition.api.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private void listImages(HttpServletResponse response) throws IOException{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        ServletOutputStream out = response.getOutputStream();
        out.write("[".getBytes());
        
        boolean first = true;
        for (FileStatus file : fileService.listFiles("/objectrecognition/images")) {
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
}
