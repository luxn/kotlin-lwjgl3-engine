package shader

import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL11
import java.io.IOException
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.FileReader
import java.io.BufferedReader



open abstract class ShaderProgram {
    private val programID: Int
    private val vertexShaderID: Int
    private val fragmentShaderID: Int

    protected constructor(vertexFile: String, fragmentFile: String) {
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER)
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER)
        programID = GL20.glCreateProgram()
        GL20.glAttachShader(programID, vertexShaderID)
        GL20.glAttachShader(programID, fragmentShaderID)
        bindAttributes()
        GL20.glLinkProgram(programID)
        GL20.glValidateProgram(programID)
    }

    fun start() {
        GL20.glUseProgram(programID)
    }

    fun stop() {
        GL20.glUseProgram(0)
    }

    fun cleanUp() {
        stop()
        GL20.glDetachShader(programID, vertexShaderID)
        GL20.glDetachShader(programID, fragmentShaderID)
        GL20.glDeleteShader(vertexShaderID)
        GL20.glDeleteShader(fragmentShaderID)
        GL20.glDeleteProgram(programID)



    }



    protected abstract fun bindAttributes()

    protected fun bindAttribute(attribute: Int, variableName: String) {
        GL20.glBindAttribLocation(programID, attribute, variableName)
    }

    private fun loadShader(file: String, type: Int): Int {
        val shaderSource = StringBuilder()
        try {
            val reader = BufferedReader(FileReader(file))
            var line: String?
            line = reader.readLine()
            while (line != null) {
                shaderSource.append(line).append("//\n")
                line = reader.readLine()
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
            System.exit(-1)
        }

        val shaderID = GL20.glCreateShader(type)
        GL20.glShaderSource(shaderID, shaderSource)
        GL20.glCompileShader(shaderID)
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            println(GL20.glGetShaderInfoLog(shaderID, 500))
            System.err.println("Could not compile shader!")
            System.exit(-1)
        }
        return shaderID
    }
}