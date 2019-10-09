package top.omooo.plugin

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class ASMUtils {

    static ClassWriter insertByteCode(File inputFile) {
        def inputStream = new FileInputStream(inputFile)
        def classReader = new ClassReader(inputStream)
        def classWrite = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        def classVisitor = new TraceClassVisitor(classWrite)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        return classWrite
    }

}