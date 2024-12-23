package model;

public class Report {
    protected int id;
    protected int userID;
    protected String content_report;
    protected String message;
    protected String status;

    public Report(int userID, String content_report, String message, String status) {
        this.userID = userID;
        this.content_report = content_report;
        this.message = message;
        this.status = status;
    }

    public Report(int id, int userID, String content_report, String message, String status) {
        this.id = id;
        this.userID = userID;
        this.content_report = content_report;
        this.message = message;
        this.status = status;
    }

    public Report() {
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public String getContent_report() {
        return content_report;
    }

    public String getMassage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setContent_report(String content_report) {
        this.content_report = content_report;
    }

    public void setMassage(String massage) {
        this.message = massage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", userID=" + userID +
                ", content_report='" + content_report + '\'' +
                ", massage='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
