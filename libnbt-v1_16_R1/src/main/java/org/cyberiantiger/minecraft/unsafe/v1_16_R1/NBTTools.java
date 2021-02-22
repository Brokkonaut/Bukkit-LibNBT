/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cyberiantiger.minecraft.unsafe.v1_16_R1;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_16_R1.BlockPosition;
import net.minecraft.server.v1_16_R1.IBlockData;
import net.minecraft.server.v1_16_R1.NBTBase;
import net.minecraft.server.v1_16_R1.NBTTagByte;
import net.minecraft.server.v1_16_R1.NBTTagByteArray;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagDouble;
import net.minecraft.server.v1_16_R1.NBTTagFloat;
import net.minecraft.server.v1_16_R1.NBTTagInt;
import net.minecraft.server.v1_16_R1.NBTTagIntArray;
import net.minecraft.server.v1_16_R1.NBTTagList;
import net.minecraft.server.v1_16_R1.NBTTagLong;
import net.minecraft.server.v1_16_R1.NBTTagLongArray;
import net.minecraft.server.v1_16_R1.NBTTagShort;
import net.minecraft.server.v1_16_R1.NBTTagString;
import net.minecraft.server.v1_16_R1.TileEntity;
import net.minecraft.server.v1_16_R1.WorldServer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.cyberiantiger.minecraft.nbt.ByteArrayTag;
import org.cyberiantiger.minecraft.nbt.ByteTag;
import org.cyberiantiger.minecraft.nbt.CompoundTag;
import org.cyberiantiger.minecraft.nbt.DoubleTag;
import org.cyberiantiger.minecraft.nbt.FloatTag;
import org.cyberiantiger.minecraft.nbt.IntArrayTag;
import org.cyberiantiger.minecraft.nbt.IntTag;
import org.cyberiantiger.minecraft.nbt.ListTag;
import org.cyberiantiger.minecraft.nbt.LongArrayTag;
import org.cyberiantiger.minecraft.nbt.LongTag;
import org.cyberiantiger.minecraft.nbt.ShortTag;
import org.cyberiantiger.minecraft.nbt.StringTag;
import org.cyberiantiger.minecraft.nbt.Tag;
import org.cyberiantiger.minecraft.nbt.TagType;

/**
 *
 * @author antony
 */
public final class NBTTools implements org.cyberiantiger.minecraft.unsafe.NBTTools {

    public NBTTagCompound toNativeCompound(CompoundTag tag) {
        NBTTagCompound result = new NBTTagCompound();
        for (@SuppressWarnings("rawtypes")
        Map.Entry<String, Tag> e : tag.getValue().entrySet()) {
            NBTBase base;
            String name = e.getKey();
            Tag<?> t = e.getValue();
            switch (t.getType()) {
                case BYTE:
                    base = NBTTagByte.a(((ByteTag) t).getRawValue());
                    break;
                case BYTE_ARRAY:
                    base = new NBTTagByteArray(((ByteArrayTag) t).getValue());
                    break;
                case COMPOUND:
                    base = toNativeCompound((CompoundTag) t);
                    break;
                case DOUBLE:
                    base = NBTTagDouble.a(((DoubleTag) t).getRawValue());
                    break;
                case FLOAT:
                    base = NBTTagFloat.a(((FloatTag) t).getRawValue());
                    break;
                case INT:
                    base = NBTTagInt.a(((IntTag) t).getRawValue());
                    break;
                case INT_ARRAY:
                    base = new NBTTagIntArray(((IntArrayTag) t).getValue());
                    break;
                case LIST:
                    base = toNativeList((ListTag) t);
                    break;
                case LONG:
                    base = NBTTagLong.a(((LongTag) t).getRawValue());
                    break;
                case SHORT:
                    base = NBTTagShort.a(((ShortTag) t).getRawValue());
                    break;
                case STRING:
                    base = NBTTagString.a(((StringTag) t).getValue());
                    break;
                case LONG_ARRAY:
                    base = new NBTTagLongArray(((LongArrayTag) t).getValue());
                    break;
                default:
                    // Can't be reached.
                    throw new IllegalArgumentException();
            }
            result.set(name, base);
        }
        return result;
    }

    public NBTTagList toNativeList(ListTag tag) {
        NBTTagList result = new NBTTagList();

        switch (tag.getListType()) {
            case BYTE:
                for (ByteTag t : (ByteTag[]) tag.getValue()) {
                    result.add(NBTTagByte.a(t.getRawValue()));
                }
                break;
            case BYTE_ARRAY:
                for (ByteArrayTag t : (ByteArrayTag[]) tag.getValue()) {
                    result.add(new NBTTagByteArray(t.getValue()));
                }
                break;
            case COMPOUND:
                for (CompoundTag t : (CompoundTag[]) tag.getValue()) {
                    result.add(toNativeCompound(t));
                }
                break;
            case DOUBLE:
                for (DoubleTag t : (DoubleTag[]) tag.getValue()) {
                    result.add(NBTTagDouble.a(t.getRawValue()));
                }
                break;
            case FLOAT:
                for (FloatTag t : (FloatTag[]) tag.getValue()) {
                    result.add(NBTTagFloat.a(t.getRawValue()));
                }
                break;
            case INT:
                for (IntTag t : (IntTag[]) tag.getValue()) {
                    result.add(NBTTagInt.a(t.getRawValue()));
                }
                break;
            case INT_ARRAY:
                for (IntArrayTag t : (IntArrayTag[]) tag.getValue()) {
                    result.add(new NBTTagIntArray(t.getValue()));
                }
                break;
            case LIST:
                for (ListTag t : (ListTag[]) tag.getValue()) {
                    result.add(toNativeList(t));
                }
            case LONG:
                for (LongTag t : (LongTag[]) tag.getValue()) {
                    result.add(NBTTagLong.a(t.getRawValue()));
                }
                break;
            case SHORT:
                for (ShortTag t : (ShortTag[]) tag.getValue()) {
                    result.add(NBTTagShort.a(t.getRawValue()));
                }
                break;
            case STRING:
                for (StringTag t : (StringTag[]) tag.getValue()) {
                    result.add(NBTTagString.a(t.getValue()));
                }
                break;
            case LONG_ARRAY:
                for (LongArrayTag t : (LongArrayTag[]) tag.getValue()) {
                    result.add(new NBTTagLongArray(t.getValue()));
                }
                break;
            case END:
                break;
        }

        return result;
    }

    public CompoundTag fromNativeCompound(NBTTagCompound tag) {
        try {
            @SuppressWarnings("rawtypes")
            Map<String, Tag> result = new HashMap<>();
            for (String name : tag.getKeys()) {
                NBTBase b = tag.get(name);
                switch (TagType.values()[b.getTypeId()]) {
                    case BYTE:
                        result.put(name, new ByteTag(((NBTTagByte) b).asByte()));
                        break;
                    case BYTE_ARRAY:
                        result.put(name, new ByteArrayTag(((NBTTagByteArray) b).getBytes()));
                        break;
                    case COMPOUND:
                        result.put(name, fromNativeCompound((NBTTagCompound) b));
                        break;
                    case DOUBLE:
                        result.put(name, new DoubleTag(((NBTTagDouble) b).asDouble()));
                        break;
                    case FLOAT:
                        result.put(name, new FloatTag(((NBTTagFloat) b).asFloat()));
                        break;
                    case INT:
                        result.put(name, new IntTag(((NBTTagInt) b).asInt()));
                        break;
                    case INT_ARRAY:
                        result.put(name, new IntArrayTag(((NBTTagIntArray) b).getInts()));
                        break;
                    case LIST:
                        result.put(name, fromNativeList((NBTTagList) b));
                        break;
                    case LONG:
                        result.put(name, new LongTag(((NBTTagLong) b).asLong()));
                        break;
                    case SHORT:
                        result.put(name, new ShortTag(((NBTTagShort) b).asShort()));
                        break;
                    case STRING:
                        result.put(name, new StringTag(((NBTTagString) b).asString()));
                        break;
                    case LONG_ARRAY:
                        result.put(name, new LongArrayTag(((NBTTagLongArray) b).getLongs()));
                        break;
                    case END:
                        break;
                }
            }
            return new CompoundTag(result);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NBTTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ListTag fromNativeList(NBTTagList tag) {
        try {
            TagType type = TagType.values()[tag.d_()];
            if (type == TagType.END) {
                type = TagType.BYTE;
            }
            @SuppressWarnings("rawtypes")
            Tag[] t = (Tag[]) Array.newInstance(type.getTagClass(), tag.size());
            switch (type) {
                case BYTE:
                case END:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new ByteTag(((NBTTagByte) tag.get(i)).asByte());
                    }
                    break;
                case BYTE_ARRAY:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new ByteArrayTag(((NBTTagByteArray) tag.get(i)).getBytes());
                    }
                    break;
                case COMPOUND:
                    for (int i = 0; i < tag.size(); i++) {
                        if (tag.get(i) == null) {
                            t[i] = new CompoundTag();
                        } else {
                            t[i] = fromNativeCompound((NBTTagCompound) tag.get(i));
                        }
                    }
                    break;
                case DOUBLE:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new DoubleTag(((NBTTagDouble) tag.get(i)).asDouble());
                    }
                    break;
                case FLOAT:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new FloatTag(((NBTTagFloat) tag.get(i)).asFloat());
                    }
                    break;
                case INT:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new IntTag(((NBTTagInt) tag.get(i)).asInt());
                    }
                    break;
                case INT_ARRAY:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new IntArrayTag(((NBTTagIntArray) tag.get(i)).getInts());
                    }
                    break;
                case LIST:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = fromNativeList((NBTTagList) tag.get(i));
                    }
                    break;
                case LONG:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new LongTag(((NBTTagLong) tag.get(i)).asLong());
                    }
                    break;
                case SHORT:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new ShortTag(((NBTTagShort) tag.get(i)).asShort());
                    }
                    break;
                case STRING:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new StringTag(((NBTTagString) tag.get(i)).asString());
                    }
                    break;
                case LONG_ARRAY:
                    for (int i = 0; i < tag.size(); i++) {
                        t[i] = new LongArrayTag(((NBTTagLongArray) tag.get(i)).getLongs());
                    }
                    break;
            }
            return new ListTag(type, t);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NBTTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void writeTileEntity(Block block, CompoundTag tag) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        TileEntity tileEntity = craftWorld.getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        if (tileEntity == null) {
            return;
        }
        BlockPosition pos = new BlockPosition(block.getX(), block.getY(), block.getZ());
        WorldServer handle = craftWorld.getHandle();
        IBlockData blockData = handle.getType(pos);
        tileEntity.load(blockData, toNativeCompound(tag));
        tileEntity.update();
        craftWorld.getHandle().notify(new BlockPosition(block.getX(), block.getY(), block.getZ()), blockData, blockData, 3);
    }

    @Override
    public CompoundTag readTileEntity(Block block) {
        CraftWorld craftWorld = (CraftWorld) block.getWorld();
        BlockPosition pos = new BlockPosition(block.getX(), block.getY(), block.getZ());
        TileEntity tileEntity = craftWorld.getHandle().getTileEntity(pos);
        if (tileEntity == null) {
            return null;
        }
        NBTTagCompound tag = new NBTTagCompound();
        tileEntity.save(tag);
        return fromNativeCompound(tag);
    }

    @Override
    public CompoundTag readItemStack(ItemStack stack) {
        net.minecraft.server.v1_16_R1.ItemStack nativeStack = CraftItemStack.asNMSCopy(stack);
        if (nativeStack == null) {
            return null;
        }
        NBTTagCompound compound = new NBTTagCompound();
        nativeStack.save(compound);
        return fromNativeCompound(compound);
    }

    @Override
    public ItemStack createItemStack(CompoundTag tag) {
        net.minecraft.server.v1_16_R1.ItemStack nativeStack = net.minecraft.server.v1_16_R1.ItemStack.a(toNativeCompound(tag));
        return CraftItemStack.asCraftMirror(nativeStack);
    }

    @Override
    public CompoundTag readEntity(Entity e) {
        net.minecraft.server.v1_16_R1.Entity handle = ((CraftEntity) e).getHandle();
        NBTTagCompound compound = new NBTTagCompound();
        handle.save(compound);
        return fromNativeCompound(compound);
    }

    @Override
    public void updateEntity(Entity entity, CompoundTag tag) {
        CraftEntity craftEntity = (CraftEntity) entity;
        craftEntity.getHandle().load(toNativeCompound(tag));
    }

    @Override
    public Entity getEntityByUUID(World world, UUID id) {
        CraftWorld craftWorld = (CraftWorld) world;
        net.minecraft.server.v1_16_R1.Entity entity = craftWorld.getHandle().getEntity(id);
        if (entity == null) {
            return null;
        }
        return entity.getBukkitEntity();
    }

    @Override
    public boolean isEntityByIdSupported() {
        return false;
    }

    @Override
    public boolean isEntityByUuidSupported() {
        return true;
    }

    @Override
    public Entity getEntityById(World world, int id) {
        throw new UnsupportedOperationException("Not supported");
    }
}
