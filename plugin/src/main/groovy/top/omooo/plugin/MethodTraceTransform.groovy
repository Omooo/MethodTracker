package top.omooo.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project

class MethodTraceTransform extends Transform {

    private Project project

    MethodTraceTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "MethodTrace"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        transformInvocation.inputs.each { input ->

            input.directoryInputs.each { directoryInput ->

                def outputDirFile = transformInvocation.outputProvider.getContentLocation(
                        directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY
                )

                def outputFilePath = outputDirFile.absolutePath
                def inputFilePath = directoryInput.file.absolutePath

                def allFiles = DirectoryUtils.getAllFiles(directoryInput.file)
                for (File file : allFiles) {
                    def outputFullPath = file.absolutePath.replace(inputFilePath, outputFilePath)
                    def outputFile = new File(outputFullPath)
                    if (!outputFile.parentFile.exists()) {
                        outputFile.parentFile.mkdirs()
                    }
                    MethodTraceUtil.traceFile(file, outputFile)
                }

            }
            input.jarInputs.each { jarInput ->

                def outputFile = transformInvocation.outputProvider.getContentLocation(
                        jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR
                )
                FileUtils.copyFile(jarInput.file, outputFile)

            }
        }
    }

}