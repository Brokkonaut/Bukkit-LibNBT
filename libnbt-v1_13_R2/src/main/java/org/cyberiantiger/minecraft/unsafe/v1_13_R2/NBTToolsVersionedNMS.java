package org.cyberiantiger.minecraft.unsafe.v1_13_R2;

import net.minecraft.server.v1_13_R2.NBTTagByte;
import org.cyberiantiger.minecraft.unsafe.VersionedNMS;

public class NBTToolsVersionedNMS implements VersionedNMS {
    @Override
    public String getTargetVersion() {
        try {
            NBTTagByte.class.getMethod("g");
            return "v1_13_R2";
        } catch (ReflectiveOperationException ex) {
            // ignored;
        }
        return "v1_13_R2_1";
    }

}
