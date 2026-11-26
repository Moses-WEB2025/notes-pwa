package ch.qos.logback.core.net.server;

import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.util.CloseUtil;
import com.miui.maml.ActionCommand;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

/* JADX INFO: loaded from: classes.dex */
class RemoteReceiverStreamClient extends ContextAwareBase implements RemoteReceiverClient {
    private final String clientId;
    private final OutputStream outputStream;
    private BlockingQueue<Serializable> queue;
    private final Socket socket;

    RemoteReceiverStreamClient(String str, OutputStream outputStream) {
        this.clientId = "client " + str + ": ";
        this.socket = null;
        this.outputStream = outputStream;
    }

    public RemoteReceiverStreamClient(String str, Socket socket) {
        this.clientId = "client " + str + ": ";
        this.socket = socket;
        this.outputStream = null;
    }

    private ObjectOutputStream createObjectOutputStream() throws IOException {
        return this.socket == null ? new ObjectOutputStream(this.outputStream) : new ObjectOutputStream(this.socket.getOutputStream());
    }

    @Override // ch.qos.logback.core.net.server.Client, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Socket socket = this.socket;
        if (socket == null) {
            return;
        }
        CloseUtil.closeQuietly(socket);
    }

    @Override // ch.qos.logback.core.net.server.RemoteReceiverClient
    public boolean offer(Serializable serializable) {
        BlockingQueue<Serializable> blockingQueue = this.queue;
        if (blockingQueue != null) {
            return blockingQueue.offer(serializable);
        }
        throw new IllegalStateException("client has no event queue");
    }

    @Override // java.lang.Runnable
    public void run() {
        StringBuilder sb;
        addInfo(this.clientId + ActionCommand.USB_CONNECTED);
        ObjectOutputStream objectOutputStreamCreateObjectOutputStream = null;
        try {
            try {
                objectOutputStreamCreateObjectOutputStream = createObjectOutputStream();
                loop0: while (true) {
                    int i = 0;
                    while (true) {
                        if (Thread.currentThread().isInterrupted()) {
                            break loop0;
                        }
                        try {
                            objectOutputStreamCreateObjectOutputStream.writeObject(this.queue.take());
                            objectOutputStreamCreateObjectOutputStream.flush();
                            i++;
                            if (i >= 70) {
                                try {
                                    objectOutputStreamCreateObjectOutputStream.reset();
                                    break;
                                } catch (InterruptedException unused) {
                                    i = 0;
                                    Thread.currentThread().interrupt();
                                }
                            } else {
                                continue;
                            }
                        } catch (InterruptedException unused2) {
                        }
                    }
                }
                if (objectOutputStreamCreateObjectOutputStream != null) {
                    CloseUtil.closeQuietly(objectOutputStreamCreateObjectOutputStream);
                }
                close();
                sb = new StringBuilder();
            } catch (RuntimeException e) {
                addError(this.clientId + e);
                if (objectOutputStreamCreateObjectOutputStream != null) {
                    CloseUtil.closeQuietly(objectOutputStreamCreateObjectOutputStream);
                }
                close();
                sb = new StringBuilder();
            } catch (SocketException e2) {
                addInfo(this.clientId + e2);
                if (objectOutputStreamCreateObjectOutputStream != null) {
                    CloseUtil.closeQuietly(objectOutputStreamCreateObjectOutputStream);
                }
                close();
                sb = new StringBuilder();
            } catch (IOException e3) {
                addError(this.clientId + e3);
                if (objectOutputStreamCreateObjectOutputStream != null) {
                    CloseUtil.closeQuietly(objectOutputStreamCreateObjectOutputStream);
                }
                close();
                sb = new StringBuilder();
            }
            addInfo(sb.append(this.clientId).append("connection closed").toString());
        } catch (Throwable th) {
            if (objectOutputStreamCreateObjectOutputStream != null) {
                CloseUtil.closeQuietly(objectOutputStreamCreateObjectOutputStream);
            }
            close();
            addInfo(this.clientId + "connection closed");
            throw th;
        }
    }

    @Override // ch.qos.logback.core.net.server.RemoteReceiverClient
    public void setQueue(BlockingQueue<Serializable> blockingQueue) {
        this.queue = blockingQueue;
    }
}
