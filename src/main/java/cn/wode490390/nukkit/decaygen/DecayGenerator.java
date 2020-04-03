package cn.wode490390.nukkit.decaygen;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.level.generator.Normal;
import cn.nukkit.math.MathHelper;
import cn.nukkit.math.NukkitRandom;

import java.util.Collections;
import java.util.Map;

public class DecayGenerator extends Normal {

    public DecayGenerator() {
        this(Collections.emptyMap());
    }

    public DecayGenerator(Map<String, Object> options) {
        super(options);
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {
        super.populateChunk(chunkX, chunkZ);
        ChunkManager level = this.getChunkManager();
        BaseFullChunk chunk = level.getChunk(chunkX, chunkZ);

        NukkitRandom random = new NukkitRandom(0xdeadbeef ^ (chunkX << 8) ^ chunkZ ^ level.getSeed());
        int chunkDistance = chunkX * chunkX + chunkZ * chunkZ;
        int offset = Math.min(MathHelper.floor(Math.sqrt(chunkDistance) / 3 + 1), 16);

        for (int i = 0; i < random.nextBoundedInt(Math.min(chunkDistance / 2 + 1, 32768)); ++i) {
            int x1 = random.nextBoundedInt(16);
            int z1 = random.nextBoundedInt(16);
            int y1 = random.nextBoundedInt(Math.min(chunk.getHighestBlockAt(x1, z1) + 5, 256));
            int x2 = random.nextBoundedInt(16);
            int y2 = random.nextBoundedInt(Math.min(y1 + offset, 256));
            int z2 = random.nextBoundedInt(16);

            int block1 = chunk.getFullBlock(x1, y1 ,z1);
            int block2 = chunk.getFullBlock(x2, y2 ,z2);
            chunk.setFullBlockId(x1, y1 ,z1, block2);
            chunk.setFullBlockId(x2, y2 ,z2, block1);
        }
    }
}
