package com.simibubi.create.foundation.utility.render.instancing;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.foundation.utility.ColorHelper;
import com.simibubi.create.foundation.utility.render.SafeDirectBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.math.BlockPos;

import static com.simibubi.create.foundation.utility.render.instancing.VertexAttribute.*;

public class BasicData<D extends BasicData<D>> extends InstanceData {
    public static final VertexFormat FORMAT = new VertexFormat(RGB, POSITION, LIGHT);

    private byte r;
    private byte g;
    private byte b;
    private float x;
    private float y;
    private float z;
    private float blockLight;
    private float skyLight;

    public D setBlockLight(int blockLight) {
        this.blockLight = blockLight / 15f;
        return (D) this;
    }

    public D setSkyLight(int skyLight) {
        this.skyLight = skyLight / 15f;
        return (D) this;
    }

    public D setPosition(Vector3f pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        return (D) this;
    }

    public D setTileEntity(KineticTileEntity te) {
        setPosition(te.getPos());
        if (te.hasSource()) {
            int color = ColorHelper.colorFromLong(te.network);
            r = (byte) ((color >> 16) & 0xFF);
            g = (byte) ((color >> 8) & 0xFF);
            b = (byte) (color & 0xFF);
        }
        else {
            r = (byte) 0xFF;
            g = (byte) 0xFF;
            b = (byte) 0x00;
        }
        return (D) this;
    }

    public D setPosition(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        return (D) this;
    }

    @Override
    public void write(SafeDirectBuffer buf) {
        buf.put(r);
        buf.put(g);
        buf.put(b);

        putVec3(buf, x, y, z);

        putVec2(buf, blockLight, skyLight);
    }
}
