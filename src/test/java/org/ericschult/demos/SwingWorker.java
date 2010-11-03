package org.ericschult.demos;


/**
 * An abstract class that you subclass to perform
 * GUI-related work in a dedicated thread.
 * For instructions on using this class, see 
 * http://java.sun.com/products/jfc/tsc/articles/threads/threads2.html
 */


public abstract class SwingWorker 
{
    public static final int READY = 0;
    public static final int RUNNING = 1;
    public static final int INTERRUPTED = 2;
    public static final int FINISHED = 3;
    public static final int ABORTED = 4;

    private final ThreadVar threadVar;
    private Object value;
    private int status = READY;
    private Throwable throwable = null;
    private static int workerNumber = 0;


    /** 
     * Class to maintain reference to current worker thread
     * under separate synchronization control.
     */
    private static class ThreadVar {
        private Thread thread;

        ThreadVar(Thread t) { 
	    thread = t; 
	}

        synchronized Thread get() { 
	    return thread; 
	}

        synchronized void clear() { 
	    thread = null; 
	}
    }

    /** 
     * Get the value produced by the worker thread, or null if it 
     * hasn't been constructed yet.
     */
    protected synchronized Object getValue() { 
        return value; 
    }


    /** 
     * Set the value produced by worker thread 
     */
    private synchronized void setValue(Object x) { 
        value = x; 
    }


    /** 
     * Compute the value to be returned by the <code>get</code> method. 
     */
    public abstract Object construct();


    /**
     * Called on the event dispatching thread (not on the worker thread)
     * after the <code>construct</code> method has returned.
     */
    public void finished() {
    }


    /**
     * Interrupts the worker thread.  Call this method
     * to force the worker to abort what it's doing.
     */
    public void interrupt() {
        Thread t = threadVar.get();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
    }


    /**
     * Return the value created by the <code>construct</code> method.  
     * Returns null if either the constructing thread or
     * the current thread was interrupted before a value was produced.
     * 
     * @return the value created by the <code>construct</code> method
     */
    public Object get() {
        while (true) {  
            Thread t = threadVar.get();
            if (t == null) {
                return getValue();
            }
            try {
                t.join();
            }
            catch (InterruptedException e) {
                // Thread.currentThread().interrupt(); 
		System.out.println(t + " Interrupted Exception");
		t.interrupt();  // exception clears the bit
                return null;
            }
        }
    }


    /** 
     * Start the worker thread.
     */
    public void start() {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }


    /**
     * If the caller doesn't provide an explicit thread name, we 
     * generate one.
     */
    private static synchronized int nextWorkerNumber() {
	return workerNumber++;
    }


    /**
     * Create a thread that will call the <code>construct</code> method
     * and then exit.  
     */
    public SwingWorker(String name) 
    {
        final Runnable doFinished = new Runnable() {
	    public void run() { 
		finished(); 
	    }
        };

        Runnable doConstruct = new Runnable() { 
            public void run() {
		status = RUNNING;
                try {
                    setValue(construct());
		    status = isInterrupted() ? INTERRUPTED : FINISHED;
                }
		catch (Throwable e) {
		    status = isInterrupted() ? INTERRUPTED : ABORTED;
		    throwable = e;
		}
                finally {
                    threadVar.clear();
                }
                java.awt.EventQueue.invokeLater(doFinished);
            }
	    boolean isInterrupted() {
		return Thread.currentThread().isInterrupted();
	    }
        };

	Thread thr = new Thread(doConstruct, name);
	thr.setPriority(Thread.MIN_PRIORITY);
        threadVar = new ThreadVar(thr);
    }


    public SwingWorker() {
	this("SwingWorker thread-" + nextWorkerNumber());
    }


    public Throwable getThrowable() {
	return throwable;
    }


    /** 
     * Return one of the following to indicate the current status of this
     * worker:
     * <ul>
     * <li> READY - The worker thread has been constructed but not started.
     * <li> RUNNING - The worker thread is running
     * <li> INTERRUPTED - The worker thread has been interrupted
     * <li> FINISHED - The worker thread finished without error or interruption
     * <li> ABORTED - The worker thread threw an uncaught exception
     * </ul>
     * If status is FINISHED then the <code>get</code> method will return
     * the non-null value computed by the <code>construct</code> method.
     * The values of the status constants are guaranteed to be increasing 
     * in they're listed above.  So it's safe to write:
     * <pre>
     * if (worker.getStatus() > SwingWorker.RUNNING) {
     *     // assume that the worker is INTERRUPTED, FINISHED, or ABORTED
     * }
     * </pre>
     */
    public int getStatus() {
	return status;
    }
}
