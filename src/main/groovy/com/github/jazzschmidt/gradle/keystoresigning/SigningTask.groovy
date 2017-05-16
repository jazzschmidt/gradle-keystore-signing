package com.github.jazzschmidt.gradle.keystoresigning

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

class SigningTask extends DefaultTask implements KeystoreSigningConfiguration {

    @InputFiles
    @SkipWhenEmpty
    Set<File> archives = new HashSet<>()

    @OutputFiles
    Set<File> signedArchives = new HashSet<>()

    @TaskAction
    void signArchives() {
        if(!keystore || !keystore.exists()) {
            throw new GradleException("Keystore could not be found")
        }

        logger.info('Signing archives...')
    }

}
