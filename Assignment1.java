
import java.awt.Container;
import javax.swing.JFrame;
import java.util.ArrayList;

/**
 * Assignment 1, different threads push and pop on the same stack object.
 */
public class Assignment1 {

	public static void main(String[] args) {
		JFrame myWindow = new JFrame();

		myWindow.setTitle("CS380 Assignment 1");
		myWindow.setSize(1000, 600);
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyPanelTop mytop = new MyPanelTop();
		MyStack stack = new MyStack(mytop.textArea, mytop.stackTextArea);

		//Creates 4 threads, 2 of them push, 2 of them pop
		MyThread t1 = new MyThread("Thread 1 (Pusher)", stack, 1750, false);
		MyThread t2 = new MyThread("Thread 2 (Pusher)", stack, 1800, false);
		MyThread t3 = new MyThread("Thread 3 (Popper)", stack, 1750, true);
		MyThread t4 = new MyThread("Thread 4 (Popper)", stack, 1800, true);

		
		ArrayList<MyThread> threadList = new ArrayList<MyThread>();
		threadList.add(t1);
		threadList.add(t2);
		threadList.add(t3);
		threadList.add(t4);
		
		mytop.setThreads(threadList);
		mytop.setStack(stack);

		Container pane = myWindow.getContentPane();
		pane.add("Center", mytop);
		myWindow.setVisible(true);
	}
}