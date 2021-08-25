package gr.zisis.interestapi.domain;

import java.util.Objects;

public class ProjectDetails {

    private String url;
    private String token = null;

    public ProjectDetails() { }

    public ProjectDetails(String url) {
        this.url = url;
    }

    public ProjectDetails(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDetails that = (ProjectDetails) o;
        return Objects.equals(url, that.url) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, token);
    }
}
