package wolox.training.services.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import wolox.training.dtos.OpenLibraryBook;
import wolox.training.factories.dtos.OpenLibraryBookBuilder;

public class OpenLibraryService {

    private final ObjectMapper mapper = new ObjectMapper();

    public Optional<OpenLibraryBook> bookInfo(String isbn) {
        String key = "ISBN:" + isbn;
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://openlibrary.org/api/books")
                .header("accept", "application/json")
                .queryString("bibkeys", key)
                .queryString("format", "json")
                .queryString("jscmd", "data")
                .asJson();
            if (response.getStatus() == HttpStatus.OK.value()) {
                JSONObject contents = response.getBody().getObject().getJSONObject(key);
                OpenLibraryBookBuilder builder = new OpenLibraryBookBuilder();
                builder.title(contents.getString("title"));
                builder.subtitle(Optional.ofNullable(contents.getString("subtitle")));
                builder.numberOfPages(contents.getInt("number_of_pages"));
                builder.publishDate(contents.getString("publish_date"));
                JSONArray authors = contents.getJSONArray("authors");
                for (int i = 0; i < authors.length(); ++i) {
                    builder.author(authors.getJSONObject(i).getString("name"));
                }
                JSONArray publishers = contents.getJSONArray("publishers");
                for (int i = 0; i < publishers.length(); ++i) {
                    builder.publisher(publishers.getJSONObject(i).getString("name"));
                }
                return Optional.of(builder.build());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
