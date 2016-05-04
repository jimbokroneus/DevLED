package com.ziglinski;

import com.logitech.gaming.LogiLED;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.Scanner;


public class Main implements NativeKeyListener {

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        switch(e.getKeyCode()){
            case NativeKeyEvent.VC_ESCAPE:
                try {
                    GlobalScreen.unregisterNativeHook();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                break;

            //control is pressed
            case NativeKeyEvent.VC_CONTROL_L:

                setControlGroup();

                break;

        }
    }

    public void setControlGroup(){

        int delayMilli = 100;
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.A, 0,0,100);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.X, 0,0,100);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.C, 0,0,100);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.V, 0,0,100);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.F, 0,0,100);

        LogiLED.LogiLedPulseSingleKey(LogiLED.A, 0,0,100, 100,0,0,delayMilli,false);


    }

    public void removeControlGroup(){
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.A, 100,0,0);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.X, 100,0,0);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.C, 100,0,0);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.V, 100,0,0);
        LogiLED.LogiLedSetLightingForKeyWithKeyName(LogiLED.F, 100,0,0);
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        switch(e.getKeyCode()){

            //control is pressed
            case NativeKeyEvent.VC_CONTROL_L:
                removeControlGroup();
                break;

        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }


    public static void main(String[] args) {
	// write your code here

        int red = 100;
        int blue = 0;
        int green = 0;

        int delayMilli = 500;
        int intervalMilli = 500;
        LogiLED.LogiLedSaveCurrentLighting();

        LogiLED.LogiLedInit();
        System.out.println("Initiating");
        boolean run = true;
        Scanner reader = new Scanner(System.in);

        //set up native hook;

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new Main());


        //main loop
        while(run) {
            //System.out.print("Enter Blue Value: ");
           // blue = reader.nextInt();

            LogiLED.LogiLedSetLighting(red, blue, green);
            System.out.println("Set Lighting to (" + red + ", " + green + ", " + blue + ")");

            //LogiLED.LogiLedFlashLighting(0, 100, green, delayMilli, intervalMilli);
            System.out.print("Quit?");
            if (reader.next().contains("y")) {
                LogiLED.LogiLedShutdown();
                run = false;
            }
        }

        //restore previousl lighting on exit
        LogiLED.LogiLedRestoreLighting();
    }
}
