package net.lingala.zip4j.tasks;

import java.io.File;
import java.io.IOException;
import java.util.List;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.headers.HeaderWriter;
import net.lingala.zip4j.model.Zip4jConfig;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.FileUtils;

/* JADX INFO: loaded from: classes6.dex */
public class AddFolderToZipTask extends AbstractAddFileToZipTask<AddFolderToZipTaskParameters> {
    public AddFolderToZipTask(ZipModel zipModel, char[] cArr, HeaderWriter headerWriter, AsyncZipTask.AsyncTaskParameters asyncTaskParameters) {
        super(zipModel, cArr, headerWriter, asyncTaskParameters);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public void executeTask(AddFolderToZipTaskParameters addFolderToZipTaskParameters, ProgressMonitor progressMonitor) throws IOException {
        List<File> filesToAdd = getFilesToAdd(addFolderToZipTaskParameters);
        setDefaultFolderPath(addFolderToZipTaskParameters);
        addFilesToZip(filesToAdd, progressMonitor, addFolderToZipTaskParameters.zipParameters, addFolderToZipTaskParameters.zip4jConfig);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.lingala.zip4j.tasks.AsyncZipTask
    public long calculateTotalWork(AddFolderToZipTaskParameters addFolderToZipTaskParameters) throws ZipException {
        List<File> filesInDirectoryRecursive = FileUtils.getFilesInDirectoryRecursive(addFolderToZipTaskParameters.folderToAdd, addFolderToZipTaskParameters.zipParameters.isReadHiddenFiles(), addFolderToZipTaskParameters.zipParameters.isReadHiddenFolders(), addFolderToZipTaskParameters.zipParameters.getExcludeFileFilter());
        if (addFolderToZipTaskParameters.zipParameters.isIncludeRootFolder()) {
            filesInDirectoryRecursive.add(addFolderToZipTaskParameters.folderToAdd);
        }
        return calculateWorkForFiles(filesInDirectoryRecursive, addFolderToZipTaskParameters.zipParameters);
    }

    private void setDefaultFolderPath(AddFolderToZipTaskParameters addFolderToZipTaskParameters) throws IOException {
        String canonicalPath;
        File file = addFolderToZipTaskParameters.folderToAdd;
        if (!addFolderToZipTaskParameters.zipParameters.isIncludeRootFolder() || file.getCanonicalFile().getParentFile() == null) {
            canonicalPath = file.getCanonicalPath();
        } else {
            canonicalPath = file.getCanonicalFile().getParentFile().getCanonicalPath();
        }
        addFolderToZipTaskParameters.zipParameters.setDefaultFolderPath(canonicalPath);
    }

    private List<File> getFilesToAdd(AddFolderToZipTaskParameters addFolderToZipTaskParameters) throws ZipException {
        List<File> filesInDirectoryRecursive = FileUtils.getFilesInDirectoryRecursive(addFolderToZipTaskParameters.folderToAdd, addFolderToZipTaskParameters.zipParameters.isReadHiddenFiles(), addFolderToZipTaskParameters.zipParameters.isReadHiddenFolders(), addFolderToZipTaskParameters.zipParameters.getExcludeFileFilter());
        if (addFolderToZipTaskParameters.zipParameters.isIncludeRootFolder()) {
            filesInDirectoryRecursive.add(addFolderToZipTaskParameters.folderToAdd);
        }
        return filesInDirectoryRecursive;
    }

    public static class AddFolderToZipTaskParameters extends AbstractZipTaskParameters {
        private final File folderToAdd;
        private final ZipParameters zipParameters;

        public AddFolderToZipTaskParameters(File file, ZipParameters zipParameters, Zip4jConfig zip4jConfig) {
            super(zip4jConfig);
            this.folderToAdd = file;
            this.zipParameters = zipParameters;
        }
    }
}
