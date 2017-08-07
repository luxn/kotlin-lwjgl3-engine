import renderEngine.DisplayManager
import renderEngine.Loader
import renderEngine.Renderer
import shader.StaticShader


fun main(args: Array<String>) {
    DisplayManager.createDisplay()

    val loader = Loader()
    val renderer = Renderer()
    val shader = StaticShader()

    val vertices = floatArrayOf(
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f
    )
    val indices = intArrayOf(
            0,1,3,
            3,1,2
    )
    val model = loader.loadToVAO(vertices, indices)

    while (!DisplayManager.isCloseRequested()) {
        //game logic

        //render
        renderer.prepare()
        shader.start()
        renderer.render(model)
        shader.stop()
        DisplayManager.updateDisplay()
    }

    shader.cleanUp()
    loader.cleanUp()
    DisplayManager.closeDisplay()
}
