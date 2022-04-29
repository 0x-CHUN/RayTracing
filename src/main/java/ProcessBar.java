import java.io.IOException;

public class ProcessBar {
    private int barSize;
    private int curSize;
    private int lens = 50;

    public ProcessBar(int barSize) {
        this.barSize = barSize;
        this.curSize = 0;
    }

    public void step(int stepSize) throws IOException {
        StringBuilder bar = new StringBuilder();
        curSize += stepSize;
        bar.append('\r');
        bar.append('[');
        float percent = (float) curSize / (float) barSize;
        int curLens = (int) (lens * percent);
        bar.append(String.valueOf('█').repeat(curLens));
        bar.append(String.valueOf(' ').repeat(lens - curLens));
        bar.append(']');
        bar.append(String.format("%.2f", percent));
        System.out.write(bar.toString().getBytes());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBar processBar = new ProcessBar(100);
        for (int i = 0; i < 100; i++) {
            processBar.step(1);
            Thread.sleep(100);
        }
    }
}
