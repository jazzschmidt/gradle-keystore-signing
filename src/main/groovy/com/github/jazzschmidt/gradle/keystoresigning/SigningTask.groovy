package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

class SigningTask extends DefaultTask implements KeystoreSigningConfiguration {

    @InputFiles
    @SkipWhenEmpty
    final Set<File> archives = new HashSet<>()

    @OutputFiles
    final Set<File> signedArchives = new HashSet<>()

    @TaskAction
    void signArchives() {
        validate()

        logger.info('Signing archives...')
    }

    private void validate() {
        if (!keystore) {
            throw new InvalidUserDataException('No keystore is configured')
        } else if (!keystore.exists()) {
            throw new InvalidUserDataException("Keystore does not exist: ${keystore.absolutePath}")
        }
    }

    void sign(Configuration configuration) {
        archives.addAll(configuration.artifacts.files)
    }

    void sign(Task task) {
        dependsOn.add(task)
        archives.addAll(task.outputs.files)
    }

    void sign(FileCollection files) {
        archives.addAll(files)
    }

    void sign(File file) {
        archives.add(file)
    }

}
