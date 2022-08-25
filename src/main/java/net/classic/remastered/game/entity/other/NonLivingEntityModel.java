/*
 * 
 */
package net.classic.remastered.game.entity.other;

import net.classic.remastered.client.model.ModelPart;
import net.classic.remastered.client.model.TexturedQuad;
import net.classic.remastered.client.model.Vertex;
import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.tile.Block;

public class NonLivingEntityModel {
    private ModelPart model = new ModelPart(0, 0);

    public NonLivingEntityModel(int tex, int textureId) {
        float var3 = -2.0f;
        float var4 = -2.0f;
        float var15 = -2.0f;
        this.model.vertices = new Vertex[8];
        this.model.quads = new TexturedQuad[6];
        Vertex vertex1 = new Vertex(var15, var4, var3, 0.0f, 0.0f);
        Vertex vertex2 = new Vertex(2.0f, var4, var3, 0.0f, 8.0f);
        Vertex vertex3 = new Vertex(2.0f, 2.0f, var3, 8.0f, 8.0f);
        Vertex vertex4 = new Vertex(var15, 2.0f, var3, 8.0f, 0.0f);
        Vertex vertex5 = new Vertex(var15, var4, 2.0f, 0.0f, 0.0f);
        Vertex vertex6 = new Vertex(2.0f, var4, 2.0f, 0.0f, 8.0f);
        Vertex vertex7 = new Vertex(2.0f, 2.0f, 2.0f, 8.0f, 8.0f);
        Vertex vertex8 = new Vertex(var15, 2.0f, 2.0f, 8.0f, 0.0f);
        this.model.vertices[0] = vertex1;
        this.model.vertices[1] = vertex2;
        this.model.vertices[2] = vertex3;
        this.model.vertices[3] = vertex4;
        this.model.vertices[4] = vertex5;
        this.model.vertices[5] = vertex6;
        this.model.vertices[6] = vertex7;
        this.model.vertices[7] = vertex8;
        float u1 = ((float)(tex % 16) + 0.75f) / 16.0f;
        float v1 = ((float)(tex / 16) + 0.75f) / 16.0f;
        float u2 = ((float)(tex % 16) + 0.25f) / 16.0f;
        float v2 = ((float)(tex / 16) + 0.25f) / 16.0f;
        Vertex[] vertexes1 = new Vertex[]{vertex6, vertex2, vertex3, vertex7};
        Vertex[] vertexes2 = new Vertex[]{vertex1, vertex5, vertex8, vertex4};
        Vertex[] vertexes3 = new Vertex[]{vertex6, vertex5, vertex1, vertex2};
        Vertex[] vertexes4 = new Vertex[]{vertex3, vertex4, vertex8, vertex7};
        Vertex[] vertexes5 = new Vertex[]{vertex2, vertex1, vertex4, vertex3};
        Vertex[] vertexes6 = new Vertex[]{vertex5, vertex6, vertex7, vertex8};
        this.model.quads[0] = new TexturedQuad(vertexes1, u1, v1, u2, v2);
        this.model.quads[1] = new TexturedQuad(vertexes2, u1, v1, u2, v2);
        this.model.quads[2] = new TexturedQuad(vertexes3, u1, v1, u2, v2);
        this.model.quads[3] = new TexturedQuad(vertexes4, u1, v1, u2, v2);
        this.model.quads[4] = new TexturedQuad(vertexes5, u1, v1, u2, v2);
        this.model.quads[5] = new TexturedQuad(vertexes6, u1, v1, u2, v2);
    }

    public void generateList(Block block) {
        this.model.render(0.0625f);
    }
}

