package menus.swingchoosers

import RETRO_ARCH_LIST_EXTENSION
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.filechooser.FileSystemView

/**
 * Just a regular Folder chooser.
 *
 * @param title Title to show on top of the window.
 * @param currentDir Default directory to use.
 * @param onFolderSelected The callback invoked when folder is selected.
 */
fun folderSwingChooser(title: String, currentDir: File = File(""), onFolderSelected: (File) -> Unit) {
    val fileChooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)
    fileChooser.dialogTitle = title
    fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    fileChooser.currentDirectory = currentDir
    val result = fileChooser.showOpenDialog(null)
    if (result == JFileChooser.APPROVE_OPTION) {
        onFolderSelected(fileChooser.selectedFile)
    }
}

/**
 * File save window.
 *
 * @param title Title to display on top of the window.
 * @param currentDir Default directory to use.
 * @param initialFileName Default filename to use.
 * @param onFileConfirm The callback invoked when user has pressed save button.
 * @param overwriteMessage In case file exist, we show this message.
 * @param overwriteTitle In case overwrite message is shown, window has this title.
 */
fun saveFileSwingChooser(
    title: String,
    currentDir: File = File(""),
    initialFileName: String,
    onFileConfirm: (File) -> Unit,
    fileNameExtensionFilter: FileNameExtensionFilter,
    overwriteMessage: String,
    overwriteTitle: String,
) {
    val fileChooser = JFileChooser(FileSystemView.getFileSystemView().homeDirectory)

    fileChooser.dialogTitle = title
    fileChooser.fileSelectionMode = JFileChooser.FILES_ONLY
    fileChooser.currentDirectory = currentDir
    fileChooser.fileFilter = fileNameExtensionFilter
    fileChooser.selectedFile = File(initialFileName)

    val result = fileChooser.showSaveDialog(null)
    if (result == JFileChooser.APPROVE_OPTION) {
        val selectedFileStringWithExtension = "${fileChooser.selectedFile}.$RETRO_ARCH_LIST_EXTENSION"
        val selectedFile = fileChooser.selectedFile
        val selectedFileWithExtension = File(selectedFileStringWithExtension)
        if (selectedFile.exists() || selectedFileWithExtension.exists()) {
            val confirmResult = JOptionPane.showConfirmDialog(
                null,
                overwriteMessage,
                overwriteTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            )
            if (confirmResult == JOptionPane.NO_OPTION) {
                return
            } else if (confirmResult == JOptionPane.YES_OPTION) {
                onFileConfirm(fileChooser.selectedFile)
            }
        } else {
            onFileConfirm(fileChooser.selectedFile)
        }
    }
}

/**
 * Just RetroArch specific implementation of the file save used to save RetroArch playlist.
 */
fun saveRetroArchListSwingChooser(
    currentDir: File = File(""),
    initialFileName: String,
    onFileConfirm: (File) -> Unit,
    title: String,
    overwriteMessage: String,
    overwriteTitle: String,
) {
    saveFileSwingChooser(
        title = title,
        currentDir = currentDir,
        initialFileName = initialFileName,
        onFileConfirm = onFileConfirm,
        fileNameExtensionFilter = FileNameExtensionFilter(
            "RetroArch playlist (.$RETRO_ARCH_LIST_EXTENSION)", RETRO_ARCH_LIST_EXTENSION
        ),
        overwriteMessage = overwriteMessage,
        overwriteTitle = overwriteTitle
    )
}

/**
 * Just RetroArch specific implementation of the folder chooser.
 */
fun retroArchSwingChooser(currentDir: File, onFolderSelected: (File) -> Unit, title: String) {
    folderSwingChooser(
        title = title,
        currentDir = currentDir,
        onFolderSelected = onFolderSelected
    )
}