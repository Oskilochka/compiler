package org.josk

import org.josk.lexer.Lexer
import org.josk.token.Token

class Main {
    static void main(String[] args) {
        File programFile = new File('src/main/groovy/testPrograms/NestedProgram.jv')

        if (!programFile.exists()) {
            println "Error: File not found: ${programFile.absolutePath}"
            System.exit(1)
        }

        String code = programFile.text

        Lexer lexer = new Lexer(code)
        List<Token> tokens = lexer.lex()

        println("=== Tokens ===")
        tokens.each { println(it) }
    }
}
