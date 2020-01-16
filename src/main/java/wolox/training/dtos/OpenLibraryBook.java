package wolox.training.dtos;

import java.util.List;
import java.util.Optional;

public class OpenLibraryBook {

    private String title;
    private Optional<String> subtitle;
    private int numberOfPages;
    private String publishDate;
    private List<String> publishers;
    private List<String> authors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<String> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Optional<String> subtitle) {
        this.subtitle = subtitle;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

}
