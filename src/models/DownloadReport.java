package models;

public class DownloadReport {

    private final String linkName;
    private final long elapsedTime;
    private final long kilobytes;

    public DownloadReport(String linkName, long elapsedTime, long kilobytes) {
        this.linkName = linkName;
        this.elapsedTime = elapsedTime;
        this.kilobytes = kilobytes / 1024;
    }

    public String getLinkName() {
        return linkName;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getKilobytes() {
        return kilobytes;
    }
}
