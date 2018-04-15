package githubrepo.com.githubrepo.bean;

/**
 * Created by tania on 15/4/18.
 */

public class GitHubRepo {

    String id,fullName,starCount,forkCount,url;


    public GitHubRepo() {

    }

    public GitHubRepo(String id, String fullName, String starCount, String forkCount, String url) {
        this.id = id;
        this.fullName = fullName;
        this.starCount = starCount;
        this.forkCount = forkCount;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStarCount() {
        return starCount;
    }

    public void setStarCount(String starCount) {
        this.starCount = starCount;
    }

    public String getForkCount() {
        return forkCount;
    }

    public void setForkCount(String forkCount) {
        this.forkCount = forkCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GitHubRepo{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", starCount='" + starCount + '\'' +
                ", forkCount='" + forkCount + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

