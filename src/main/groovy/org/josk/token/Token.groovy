package org.josk.token

class Token {
    String text
    TokenType type
    int lineNumber

    Token(String text, TokenType type, int lineNumber) {
        this.text = text
        this.type = type
        this.lineNumber = lineNumber
    }

    String toString() {
        return "${lineNumber} ${text} ${type}"
    }
}
