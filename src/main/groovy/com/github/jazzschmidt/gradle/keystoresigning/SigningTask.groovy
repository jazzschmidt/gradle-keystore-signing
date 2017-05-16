package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

class SigningTask extends DefaultTask {

    @InputFiles
    @SkipWhenEmpty
    Set<File> archives = new HashSet<>()

    @OutputFiles
    Set<File> signedArchives = new HashSet<>()

    @TaskAction
    void signArchives() {
        logger.info('Signing archives...')
    }
}
