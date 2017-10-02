package net.henryco.blinck.util.task;

import android.util.Log;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by HenryCo on 02/10/17.
 */
@NoArgsConstructor
public class RefreshableTimer {


	private Thread thread;

	@Setter
	private Runnable runnable;

	private final AtomicLong delay = new AtomicLong();
	private final AtomicLong timer = new AtomicLong();


	public RefreshableTimer(long delay, Runnable runnable) {
		setRunnable(runnable);
		setDelay(delay);
	}


	public void refresh() {

		timer.set(delay.get());

		if (thread == null || !thread.isAlive())
			start();
	}

	public void stop() {
		thread.interrupt();
	}

	private void start() {


		thread = new Thread(() -> {

			while (!thread.isInterrupted() && timer.get() > 0) {

				Log.d("Refreshable", ""+timer.get());
				long t0 = System.currentTimeMillis();
				try {
					Thread.sleep(timer.get() / 2);

				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();

				} finally {
					long l = System.currentTimeMillis() - t0;
					timer.set(timer.get() - l);
				}
			}

			runnable.run();
		});

		thread.start();
	}

	public void setDelay(long delay) {
		this.delay.set(delay);
	}
}
