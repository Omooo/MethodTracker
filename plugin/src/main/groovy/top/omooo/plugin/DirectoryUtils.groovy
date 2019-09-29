package top.omooo.plugin

class DirectoryUtils {

    static List<File> getAllFiles(File dir) {
        def res = new ArrayList<File>()
        if (!dir.isDirectory()) {
            res.add(dir)
            return res
        }
        def childFiles = dir.listFiles()
        if (childFiles == null || childFiles.length == 0) {
            return res
        }
        for (File file : childFiles) {
            if (file.isDirectory()) {
                res.addAll(getAllFiles(file))
            } else {
                res.add(file)
            }
        }
        return res
    }

}