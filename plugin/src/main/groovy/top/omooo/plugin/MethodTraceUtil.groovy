package top.omooo.plugin

import com.android.utils.FileUtils

class MethodTraceUtil {

    public static void traceFile(File inputFile, File outputFile) {
        if (isNeedTraceClass(inputFile)) {
            def classWrite = ASMUtils.insertByteCode(inputFile)
            def outStream = new FileOutputStream(outputFile)
            outStream.write(classWrite.toByteArray())
            outStream.close()
        } else {
            FileUtils.copyFile(inputFile, outputFile)
        }
    }

    private static boolean isNeedTraceClass(File file) {
        def name = file.name
        if (!name.endsWith(".class")
                || name.startsWith("R.class")
                || name.startsWith('R$')) {
            return false
        }
        return true
    }
}