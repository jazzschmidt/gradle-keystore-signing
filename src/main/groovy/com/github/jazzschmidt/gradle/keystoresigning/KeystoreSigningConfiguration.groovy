package com.github.jazzschmidt.gradle.keystoresigning

trait KeystoreSigningConfiguration {
    File keystore
    String alias
    String password
}
