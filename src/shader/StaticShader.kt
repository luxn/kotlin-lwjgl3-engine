package shader




class StaticShader: ShaderProgram {



    constructor() : super("src/shader/vertexShader.glsl", "src/shader/fragmentShader.glsl") {

    }

    override fun bindAttributes() {
        super.bindAttribute(0, "position");
    }
}