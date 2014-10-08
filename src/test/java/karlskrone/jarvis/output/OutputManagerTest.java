package karlskrone.jarvis.output;

import karlskrone.jarvis.contentgenerator.ContentData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OutputManagerTest {

    @Test
    public void testAddOutputExtension() throws Exception {
        OutputManager outputManager = new OutputManager();
        OutputPlugin outputPlugin = new OutputPlugin("1234") {
            @Override
            public void renderFinalOutput() {

            }
        };
        OutputExtension outputExtension = new OutputExtension("abcd") {
            @Override
            public Object call() throws Exception {
                return null;
            }
        };
        outputManager.addOutputPlugin(outputPlugin);
        outputManager.addOutputExtension(outputExtension, outputPlugin.getId());
        assertTrue(outputManager.getOutputPluginsList().get(0).getOutputExtensionList().contains(outputExtension));
    }

    @Test
    public void testRemoveOutputExtension() throws Exception {
        OutputManager outputManager = new OutputManager();
        OutputPlugin outputPlugin = new OutputPlugin("1234") {
            @Override
            public void renderFinalOutput() {

            }
        };
        OutputExtension outputExtension = new OutputExtension("abcd") {
            @Override
            public Object call() throws Exception {
                return null;
            }
        };
        outputManager.addOutputPlugin(outputPlugin);
        outputManager.addOutputExtension(outputExtension, outputPlugin.getId());
        outputManager.removeOutputExtension(outputPlugin.getId(), outputExtension.getId());
        assertTrue(outputManager.getOutputPluginsList().get(0).getOutputExtensionList().isEmpty());
    }

    @Test
    public void testPassDataToOutputPlugin() throws Exception {
        List<ContentData> list = new ArrayList<>();
        OutputManager outputManager = new OutputManager();
        OutputPlugin outputPlugin = new OutputPlugin("1234") {
            @Override
            public void renderFinalOutput() {

            }
        };
        OutputExtension outputExtension = new OutputExtension("abcd") {
            @Override
            public Object call() throws Exception {
                return null;
            }
        };

        ContentData cD1 = new ContentData("1");
        ContentData cD2 = new ContentData("2");
        ContentData cD3 = new ContentData("3");
        list.add(cD1);
        list.add(cD2);
        list.add(cD3);

        outputManager.addOutputPlugin(outputPlugin);
        outputManager.addOutputExtension(outputExtension, outputPlugin.getId());
        outputExtension.addContentDataToWishList("2");
        outputManager.passDataToOutputPlugins(list);
        assertTrue(outputManager.getOutputPluginsList().get(0).getOutputExtensionList().size() == 1);
    }
}