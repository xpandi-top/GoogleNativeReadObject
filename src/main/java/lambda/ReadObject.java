package lambda;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import saaf.Inspector;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import static basic.MetadataRetriever.*;
import static basic.ObjectStorage.readBlob;

public class ReadObject implements HttpFunction {
    private static long initializeConnectionTime;
    private static Storage storage;
    public void procedure(Request request, Inspector inspector) throws IOException {
        if (!isMac){
            inspector.inspectAll();
        }
        //*************FunctionStart**************
        // get parameters
        boolean connect = false;
        int count = 2;
        String myObjectName = objectName;
        String myContainerName = containerName;
        if (request!=null&&request.getObjectName()!=null) myObjectName = request.getObjectName();
        if (request!=null&&request.getContainerName()!=null) myContainerName = request.getContainerName();
        if (request!=null && request.getCount()>0) count = request.getCount();
        int actual_count = count;
        // initialize storage
        if (storage==null){
            storage = StorageOptions
                    .newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials.fromStream(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(credential))))
                    .build().getService();
            initializeConnectionTime = new Date().getTime();
            System.out.println("start initializing");
        }
        // function
        try{
            readBlob(storage,myContainerName,myObjectName);
            connect = true;
        }catch (Exception e){
            inspector.addAttribute("duration", new Date().getTime()-initializeConnectionTime);
            e.printStackTrace();
        }
        //*********************Function end
        getMetrics(isMac,inspector,count,actual_count,connect,initializeConnectionTime);
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        Inspector inspector = new Inspector();
        procedure(new Request(httpRequest),inspector);
        getResponse(httpResponse,inspector);
    }
}
