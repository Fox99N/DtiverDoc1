package docreader;


import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

import javax.websocket.server.PathParam;
import javax.xml.ws.Response;
import java.io.File;
import java.text.NumberFormat;
import java.util.logging.Logger;

i


/**
 * Created by fox on 19.02.18.
 */
public class RestFileDownloadExample {

    static Logger logger =Logger.getLogger(RestFileDownloadExample.class);
    private static final String api_version = "1.01F rev.1";
    private static  final String FILE_LOADER = "c://tmp/";

 //    @Path("/version")

    public  String returnVersion(){
        return "<p>Version: "+ api_version+ "<p>";

    }
     public Response downloadFileQuery(@PathParam("filename") String fileName) {
        return  download(fileName);

     }


     private  Response download(String fileName) {
        String fileLocation = FILE_LOADER + fileName;
         Response response = null;
         NumberFormat myFormat = NumberFormat.getInstance();
         myFormat.setGroupingUsed(true);


         File file = new File(FILE_LOADER + fileName);

         if (file.exists()) {
             ResponseBuilder builder = Response.ok(file);
             builder.header("Content_Disposition", "attachment; fileName" + file.getName() );
             response = builder.build();


             long file_size =  file.length();
                logger.info(String.format("Inside diwnloadFile ==> fileName: %s, fileSize: %s bytes",
                fileName, myFormat.format(file_size)));

         } else {
             logger.error(String.format("Inside downloadFile==> FILE NOT FOUND: fileName: %s", fileName));

             response = Response.status(404).entity("FILE NOT FOUND: " + fileLocation).type("text/plan").build();

         } return  response;
     }
}
