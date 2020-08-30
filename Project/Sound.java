import java.time.LocalDateTime;
import java.util.ArrayList;
import java.lang.System;
import javax.sound.sampled.*;
public class Sound
{
    public static float SAMPLE_RATE = 44100f;
    public static void tone(int hz, int msecs) 
    throws LineUnavailableException 
    {
        tone(hz, msecs, 1.0);
    }

    public static void tone(int hz, int msecs, double vol)                            // Single tone
    throws LineUnavailableException 
    {
        byte[] buf = new byte[2];
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 16, 1, true, true);     
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        
        //System.out.println(System.currentTimeMillis());
        for (int i=0; i < msecs * 8; i++) {
              double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
              buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
              sdl.write(buf, 0, 2);
        }
        
        //System.out.println(System.currentTimeMillis());
        sdl.drain();
        sdl.stop();
        sdl.close();
        //System.out.println(System.currentTimeMillis());
    }

    public static void tone(int hz1, int hz2, int msecs, double vol)                   // Dual tone
    throws LineUnavailableException 
    {
        
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);     
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();

        for (int i=0; i < msecs * 8; i++) {
              double angle1 = i / (SAMPLE_RATE / hz2) * 2.0 * Math.PI;
              double angle = i / (SAMPLE_RATE / hz1) * 2.0 * Math.PI;
              buf[0] = (byte)(0.5 * Math.sin(angle) * 127.0 * vol + 0.5 * Math.sin(angle1) * 127 * vol);
              sdl.write(buf, 0, 1);
        }

        sdl.drain();
        sdl.stop();
        sdl.close();
    }
    
    public static void tone1(ArrayList<Integer> voiceLs, int msecs, double vol) throws LineUnavailableException {
    	
    	byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);     
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        
        for (int j = 0; j < voiceLs.size(); j ++ ) {
        	int hz = 1600 + 100 * voiceLs.get(j);
    		for (int i = 0; i < msecs*8; i ++ ) {
	           	 double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
	           	 buf[0] = (byte)(Math.sin(angle) * 127.0 * vol );
                 sdl.write(buf, 0, 1);
           }
        }
 
        long second = System.currentTimeMillis();
        sdl.drain();
        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println("cost:"+ String.valueOf(end - second));
        sdl.stop();
        sdl.close();
    }
}