package school.hei.haapi.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import java.util.List;

public class DetectFaceService {

    String photo = "source.jpg";
    String bucket = "rekognition-picture-storage";


    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withRegion("eu-west-2").build();

    DetectLabelsRequest request = new DetectLabelsRequest()
            .withImage(new Image()
                    .withS3Object(new S3Object()
                            .withName(photo).withBucket(bucket)))
            .withMaxLabels(10)
            .withMinConfidence(75F);


        DetectLabelsResult result = rekognitionClient.detectLabels(request);
        List <Label> labels = result.getLabels();

}
