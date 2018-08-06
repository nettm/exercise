package com.nettm.exercise.io.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.FileLock;

public class ByteBufferDemo {

    private static final String path = "D:\\tmp\\demo.data";

    private FileLock lock;

    public static void main(String[] args) {
        ByteBufferDemo demo = new ByteBufferDemo();
        demo.write();
        demo.read();
    }

    public void write() {
        RandomAccessFile file = null;
        FileChannel fileChannel = null;
        try {
            file = new RandomAccessFile(path, "rw");
            fileChannel = file.getChannel();
            lock = fileChannel.lock();

            MappedByteBuffer mapped = fileChannel.map(MapMode.READ_WRITE, 0, 1024);

            // if not load file to ram
            if (!mapped.isLoaded()) {
                mapped.load();
            }

            mapped.putInt(1);
            mapped.putInt(2);
            mapped.putInt(3);
            mapped.putInt(4);
            mapped.putInt(5);

            mapped.force();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
                fileChannel.close();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void read() {
        File file = new File(path);
        try (FileChannel fileChannel = new RandomAccessFile(path, "rw").getChannel()) {
            MappedByteBuffer mapped = fileChannel.map(MapMode.READ_ONLY, 0, file.length());

            // if not load file to ram
            if (!mapped.isLoaded()) {
                mapped.load();
            }

            ByteBuffer buffer = mapped.slice();
            System.out.println(buffer.getInt());
            System.out.println(buffer.getInt());
            System.out.println(buffer.getInt());
            System.out.println(buffer.getInt());
            System.out.println(buffer.getInt());

            mapped.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
