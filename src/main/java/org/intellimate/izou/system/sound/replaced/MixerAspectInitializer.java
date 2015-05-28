package org.intellimate.izou.system.sound.replaced;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellimate.izou.addon.AddOnModel;
import org.intellimate.izou.main.Main;
import org.intellimate.izou.security.exceptions.IzouPermissionException;
import org.intellimate.izou.system.sound.IzouSoundDataLine;
import org.intellimate.izou.system.sound.IzouSoundLine;
import org.intellimate.izou.system.sound.IzouSoundSourceDataLine;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.SourceDataLine;

/**
 * this class is only used to connect the MixerAspect to Izou (not able to instantiate it).
 * @author LeanderK
 * @version 1.0
 */
public class MixerAspectInitializer {
    static Main main = null;
    private static final Logger logger= LogManager.getLogger(MixerAspectInitializer.class);

    public static synchronized void init(Main main) {
        if (MixerAspectInitializer.main == null)
            MixerAspectInitializer.main = main;
    }

    /**
     * creates the appropriate
     * @param line the line
     * @return an IzouSoundLine if an addon requested the line
     */
    static Line getAndRegisterLine(Line line) {
        AddOnModel addOnModel;
        try {
            addOnModel = main.getSecurityManager().getOrThrowAddOnModelForClassLoader();
        } catch (IzouPermissionException e) {
            logger.debug("the SoundManager will not manage this line, obtained by system");
            return line;
        }
        IzouSoundLine izouSoundLine;
        if (line instanceof SourceDataLine) {
            izouSoundLine = new IzouSoundSourceDataLine((SourceDataLine)line, main, false, addOnModel);
        } else if (line instanceof DataLine) {
            izouSoundLine = new IzouSoundDataLine((DataLine) line, main, false, addOnModel);
        } else {
            izouSoundLine = new IzouSoundLine(line, main, false, addOnModel);
        }
        main.getSoundManager().addIzouSoundLine(addOnModel, izouSoundLine);
        return izouSoundLine;
    }
}
