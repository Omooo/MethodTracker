package top.omooo.plugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import top.omooo.annotation.MethodTrack

class TraceClassVisitor extends ClassVisitor {

    private String className

    TraceClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        def methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        return new TraceMethodVisitor(api, methodVisitor, access, name, descriptor, className)
    }

    class TraceMethodVisitor extends AdviceAdapter {

        private String className
        private String methodName
        private boolean isNeedTrace
        private int timeLocalIndex = 0

        TraceMethodVisitor(int api,
                           MethodVisitor methodVisitor,
                           int access,
                           String name,
                           String descriptor,
                           String className) {
            super(api, methodVisitor, access, name, descriptor)
            String[] path = className.split("/")
            this.className = path[path.length - 1]
            this.methodName = name
        }

        @Override
        AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            if (Type.getDescriptor(MethodTrack.class) == descriptor) {
                isNeedTrace = true
            }
            return super.visitAnnotation(descriptor, visible)
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter()
            if (isNeedTrace) {
                timeLocalIndex = newLocal(Type.LONG_TYPE)
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                mv.visitVarInsn(LSTORE, timeLocalIndex)
            }
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode)
            if (isNeedTrace) {
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                mv.visitVarInsn(LLOAD, timeLocalIndex)
                mv.visitInsn(LSUB)
                mv.visitVarInsn(LSTORE, timeLocalIndex)

                mv.visitLdcInsn(className)
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
                mv.visitInsn(DUP)
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
                mv.visitLdcInsn("⇢ ")
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                mv.visitLdcInsn(methodName + methodDesc + ": ")
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                mv.visitVarInsn(LLOAD, timeLocalIndex)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false)
                mv.visitLdcInsn("ms")
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
                mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false)
                mv.visitInsn(POP)
            }
        }

    }
}

