/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cyberiantiger.minecraft.unsafe.v1_13_R2;

import java.io.IOException;
import java.util.function.Consumer;

import net.minecraft.server.v1_13_R2.Chunk;
import net.minecraft.server.v1_13_R2.ChunkRegionLoader;
import net.minecraft.server.v1_13_R2.ExceptionWorldConflict;
import net.minecraft.server.v1_13_R2.GeneratorAccess;
import net.minecraft.server.v1_13_R2.IAsyncChunkSaver;
import net.minecraft.server.v1_13_R2.IChunkAccess;
import net.minecraft.server.v1_13_R2.IChunkLoader;
import net.minecraft.server.v1_13_R2.ProtoChunk;

// Safe not to extend ChunkRegionLoader - CB does not cast to ChunkRegionLoader anywhere.

public final class InstanceChunkLoader implements IChunkLoader, IAsyncChunkSaver {
    private final ChunkRegionLoader loadLoader;
    private final ChunkRegionLoader saveLoader;

    public InstanceChunkLoader(ChunkRegionLoader loadLoader, ChunkRegionLoader saveLoader) {
        this.loadLoader = loadLoader;
        this.saveLoader = saveLoader;
    }

    @Override
    public Chunk a(GeneratorAccess arg0, int i, int j, Consumer<Chunk> arg3) throws IOException {
        // if (saveLoader.chunkExists(i, j)) {
        // return saveLoader.a(arg0, i, j, arg3);
        // }
        return loadLoader.a(arg0, i, j, arg3);
    }

    @Override
    public ProtoChunk b(GeneratorAccess arg0, int i, int j, Consumer<IChunkAccess> arg3) throws IOException {
        // if (saveLoader.chunkExists(i, j)) {
        // return saveLoader.b(arg0, i, j, arg3);
        // }
        return loadLoader.b(arg0, i, j, arg3);
    }

    @Override
    public void saveChunk(net.minecraft.server.v1_13_R2.World world, IChunkAccess chunk) throws IOException, ExceptionWorldConflict {
        saveLoader.saveChunk(world, chunk);
    }

    // @Override
    // public void a(net.minecraft.server.v1_13_R2.IBlockAccess world, Chunk chunk) throws IOException {
    // // Assume this is supposed to be some sort of save operation.
    // // Can't tell from NMS - empty method.
    // saveLoader.a(world, chunk);
    // }

    // @Override
    // public void c() {
    // // XXX: Can't guess if this is a save or load operation.
    // }

    @Override
    public void b() {
        // XXX: Can't guess if this is a save or load operation.
    }

    @Override
    public boolean a() {
        // Looks like a flush() / sync() method.
        return saveLoader.a();
    }

    // @Override
    // public boolean chunkExists(int arg0, int arg1) {
    // // maybe
    // return loadLoader.chunkExists(arg0, arg1);
    // }
}
