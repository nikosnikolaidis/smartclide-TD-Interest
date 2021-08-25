package gr.zisis.interestapi.controller.response.entity;

import java.util.Objects;

public class Project {

    private Long id;
    private String url;
    private String owner;
    private String repo;

    public Project() { }

    public Project(Long id, String url, String owner, String repo) {
        this.id = id;
        this.url = url;
        this.owner = owner;
        this.repo = repo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) && Objects.equals(url, project.url) && Objects.equals(owner, project.owner) && Objects.equals(repo, project.repo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, owner, repo);
    }
}
