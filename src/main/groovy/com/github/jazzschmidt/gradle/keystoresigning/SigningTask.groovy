package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

class SigningTask extends DefaultTask {

    @InputFiles
    Set<File> archives

    @OutputFiles
    Set<File> signedArchives

    @TaskAction
    void signArchives() {
        logger.info('Signing archives...')
    }
}
