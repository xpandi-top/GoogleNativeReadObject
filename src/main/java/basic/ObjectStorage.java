package basic;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;

import java.io.BufferedReader;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;

public class ObjectStorage {
    public static void readBlob(Storage storage, String containerName, String objectName){
       ReadChannel reader = storage.reader(containerName,objectName);
       BufferedReader br = new BufferedReader(Channels.newReader(reader, StandardCharsets.UTF_8));
//       br.lines().forEach(System.out::println);
       System.out.println(br.lines().count());
    }

    //    public static ReadChannel readBlobContent(Storage storage, String containerName, String objectName){
//        return storage.reader(containerName,objectName);
//    }
//
//
//    public static void writeBlob(
//            Storage storage, String bucketName, String objectName, String contents) throws IOException {
//        BlobId blobId = BlobId.of(bucketName, objectName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//        byte[] content = contents.getBytes(StandardCharsets.UTF_8);
//        storage.createFrom(blobInfo, new ByteArrayInputStream(content));
//    }
//
//    // https://cloud.google.com/storage/docs/creating-buckets#storage-create-bucket-code_samples
//    public static void createContainer(Storage storage, String bucketName){
//       boolean exists = storage.get(bucketName)!=null;
//        if (!exists) {
//            storage.create(
//                    BucketInfo.newBuilder(bucketName)
//                            .build()
//            );
//            System.out.println("Created");
//        }else {
//            System.out.println(bucketName + " is existed");
//        }
//    }
//    public static void deleteContainer(Storage storage,String bucketName){
//        boolean exists = storage.get(bucketName)!=null;
//        Bucket bucket = storage.get(bucketName);
//        if (exists){
//            bucket.delete();
//            System.out.println("Deleted");
//        }else {
//            System.out.println("No bucket name "+bucketName);
//        }
//
//    }
//    public static void deleteBlob(Storage storage, String bucketName, String objectName){
//        boolean exists = storage.get(bucketName,objectName)!=null;
//        if (exists){
//            storage.delete(bucketName, objectName);
//            System.out.println(objectName+" was deleted");
//        }else {
//            System.out.println("No object Name " + objectName);
//        }
//    }
}
