

public class Clip {

    private String emotion;
    private double start;
    private double end;

    // return clip
    public Clip(String emotion, double start, double end) {

        this.start = Math.floor(start);
        this.end = Math.ceil(end);
        this.emotion = emotion;

    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }

    public String getEmotion() {
        return emotion;
    }

    public double getDuration() {
        return end - start;
    }
}
