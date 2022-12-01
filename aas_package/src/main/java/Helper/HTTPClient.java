package Helper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
//Example


public class HTTPClient {

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder(URI.create("Serveradresse")).method("HEAD", HttpRequest.BodyPublishers.noBody()).build();

    // implement URI Builder according to Object -> See Swagger Description of API

}
