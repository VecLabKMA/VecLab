package main_java.controllers.tutorial_window;

public class TutorialAnimation extends Thread{
    private final Runnable startAnim;
    private final Runnable endAnim;
    private boolean finished = false;
    public TutorialAnimation(Runnable target, Runnable end) {
        super(target);
        startAnim = target;
        this.endAnim = end;
    }

    @Override
    public void run() {
        startAnim.run();
        endAnim.run();
        finished = true;
    }

    public void endAnimation() {
        if (!finished) {
            interrupt();
            endAnim.run();
        }
    }
}
