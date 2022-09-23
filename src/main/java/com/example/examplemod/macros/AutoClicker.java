package com.example.examplemod.macros;

import com.example.examplemod.Main;
import com.example.examplemod.events.MillisecondEvent;
import com.example.examplemod.utils.Perlin2D;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class AutoClicker {
    private long lastClickTime = 0;
    public static int PerlinNoice(int multiply) {
        Perlin2D perlin = new Perlin2D(new Random().nextInt());
        float Phi = 0.70710678118f;
        float noice = perlin.Noise(5, 5) + perlin.Noise((25 - 25) * Phi, (25 + 25) * Phi) * -1;
        return (int) (noice * multiply);
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void TickEvent(MillisecondEvent event) {
        if (Main.keyBindings[7].isKeyDown() && Main.mc.thePlayer != null) {
            if(System.currentTimeMillis() - lastClickTime < (long) 40 + PerlinNoice(15) - 1) return;
            try {
                Robot bot = new Robot();
                if (Main.mc.objectMouseOver == null) {
                    bot.mousePress(InputEvent.BUTTON1_MASK);
                    bot.mouseRelease(InputEvent.BUTTON1_MASK);
                    lastClickTime = System.currentTimeMillis();
                }
                else {
                    switch (Main.mc.objectMouseOver.typeOfHit) {
                        case ENTITY:
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            lastClickTime = System.currentTimeMillis();
                            break;
                        case MISS:
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            lastClickTime = System.currentTimeMillis();
                            break;
                        case BLOCK:
                            if (Main.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
                                bot.mousePress(InputEvent.BUTTON2_MASK);
                                bot.mouseRelease(InputEvent.BUTTON2_MASK);
                                lastClickTime = System.currentTimeMillis();
                            } else {
                                bot.mousePress(InputEvent.BUTTON1_MASK);
                                bot.mouseRelease(InputEvent.BUTTON1_MASK);
                                lastClickTime = System.currentTimeMillis();
                            }
                            break;
                        default:
                            bot.mousePress(InputEvent.BUTTON1_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_MASK);
                            lastClickTime = System.currentTimeMillis();
                            break;
                    }
                }
            }
            catch (AWTException ignore) { }
        }
    }
}