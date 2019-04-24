package main;

public class ExtraData {

    public long length;
    public String styleClass;
    public int job = -1;
    public int activity;


    public ExtraData(long lengthMs, String styleClass, int job, int activity) {
        super();
        this.length = lengthMs;
        this.styleClass = styleClass;
        this.job = job;
        this.activity = activity;
    }
    public ExtraData(long lengthMs, String styleClass) {
        super();
        this.length = lengthMs;
        this.styleClass = styleClass;
    }
    public long getLength() {
        return length;
    }
    public void setLength(long length) {
        this.length = length;
    }
    public String getStyleClass() {
        return styleClass;
    }
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }


}
