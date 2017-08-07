package renderEngine

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

object DisplayManager {

    private val WIDTH = 1280
    private val HEIGHT = 720
    private val TITLE = "Kotlin OpenGL"

    private var WINDOW_HANDLE: Long = 0L

    fun createDisplay() {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)


        WINDOW_HANDLE = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, MemoryUtil.NULL, MemoryUtil.NULL)
        if (WINDOW_HANDLE === org.lwjgl.system.MemoryUtil.NULL)
            throw RuntimeException("Failed to create the GLFW window")


        GLFW.glfwSetKeyCallback(WINDOW_HANDLE) { window, key, scancode, action, mods ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
                GLFW.glfwSetWindowShouldClose(window, true)
        }

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)


            GLFW.glfwGetWindowSize(WINDOW_HANDLE, pWidth, pHeight)

            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

            GLFW.glfwSetWindowPos(
                    WINDOW_HANDLE,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            )
        }

        GLFW.glfwMakeContextCurrent(WINDOW_HANDLE)
        //GLFW.glfwSwapInterval(1)

        GLFW.glfwShowWindow(WINDOW_HANDLE)

        GL.createCapabilities()

        GL11.glViewport(0,0, WIDTH, HEIGHT)

    }

    fun updateDisplay() {

        GLFW.glfwSwapBuffers(WINDOW_HANDLE)
        GLFW.glfwPollEvents()
    }


    fun closeDisplay() {
        Callbacks.glfwFreeCallbacks(WINDOW_HANDLE)
        GLFW.glfwDestroyWindow(WINDOW_HANDLE)

        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null).free()
    }

    fun isCloseRequested(): Boolean {
        return GLFW.glfwWindowShouldClose(WINDOW_HANDLE)
    }

}