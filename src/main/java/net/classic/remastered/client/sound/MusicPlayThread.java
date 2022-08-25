/*
 * 
 */
package net.classic.remastered.client.sound;

import de.jarnbjo.ogg.EndOfOggStreamException;
import net.classic.remastered.client.sound.Music;

import java.nio.ByteBuffer;

final class MusicPlayThread
extends Thread {
    private Music music;

    public MusicPlayThread(Music music) {
        this.music = music;
        this.setPriority(10);
        this.setDaemon(true);
    }

    @Override
    public final void run() {
        try {
            do {
                if (this.music.stopped) {
                    return;
                }
                if (this.music.playing == null && this.music.current != null) {
                    this.music.playing = this.music.current;
                    this.music.current = null;
                    this.music.playing.clear();
                }
                if (this.music.playing != null && this.music.playing.remaining() != 0) {
                    while (this.music.playing.remaining() != 0) {
                        ByteBuffer buffer = this.music.playing;
                        int bytesRead = this.music.stream.readPcm(buffer.array(), buffer.position(), buffer.remaining());
                        buffer.position(buffer.position() + bytesRead);
                        if (bytesRead > 0) continue;
                        this.music.finished = true;
                        this.music.stopped = true;
                        break;
                    }
                }
                if (this.music.playing != null && this.music.previous == null) {
                    this.music.playing.flip();
                    this.music.previous = this.music.playing;
                    this.music.playing = null;
                }
                Thread.sleep(10L);
            } while (this.music.player.running);
            return;
        }
        catch (EndOfOggStreamException buffer) {
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        finally {
            this.music.finished = true;
        }
    }
}

