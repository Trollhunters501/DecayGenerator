package cn.wode490390.nukkit.decaygen;

import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.wode490390.nukkit.decaygen.util.MetricsLite;

public class DecayGeneratorPlugin extends PluginBase {

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this, 6991);
        } catch (Throwable ignore) {

        }

        this.saveDefaultConfig();
        Config config = this.getConfig();

        String node = "overworld";
        boolean overworld = true;
        try {
            overworld = config.getBoolean(node, overworld);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        boolean nether = true;
        node = "nether";
        try {
            nether = config.getBoolean(node, nether);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        if (overworld) {
            Generator.addGenerator(DecayGenerator.class, "overut", Generator.TYPE_INFINITE);
        }
        if (nether) {
            Generator.addGenerator(DecayNetherGenerator.class, "netherut", Generator.TYPE_NETHER);
        }
    }

    private void logConfigException(String node, Throwable t) {
        this.getLogger().alert("An error occurred while reading the configuration '" + node + "'. Use the default value.", t);
    }
}
