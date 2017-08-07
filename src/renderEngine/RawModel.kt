package renderEngine

class RawModel {
    val vaoID: Int
    val vertexCount: Int

    constructor(vaoID: Int, vertexCount: Int) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }


}