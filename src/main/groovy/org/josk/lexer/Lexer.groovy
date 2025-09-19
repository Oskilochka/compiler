package org.josk.lexer

import org.josk.token.Token
import org.josk.token.TokenType

import static org.josk.lexer.LexerTables.classOfChar

class Lexer {
    String code
    int pos = 0
    int lineNumber = 1
    List<Token> tokens = []
    boolean skipWhitespace = true

    Lexer(String code, boolean skipWhitespace = true) {
        this.code = code
        this.skipWhitespace = skipWhitespace
    }

    List<Token> lex() {
        while (pos < code.length()) {
            String ch = code[pos]

            if (pos + 1 < code.length() && ch == ':' && code[pos+1] == '=') {
                addToken(TokenType.ASSIGN_OP, ":=")
                pos += 2
                continue
            }

            if (ch == ':') {
                addToken(TokenType.COLON, ':')
                pos++
                continue
            }

            if (ch == '"') {
                String str = readString()
                addToken(TokenType.STRING, str)
                continue
            }

            if (ch in ['!', '=', '>', '<']) {
                addMultiCharOperator(ch)
                continue
            }

            switch(classOfChar(ch)) {
                case 'Letter':
                    String word = readWhileClosure { c -> classOfChar(c) in ['Letter','Digit'] }
                    if (word in ['yes','no']) {
                        addToken(TokenType.BOOL, word)
                    }  else if (LexerTables.TYPES.contains(word)) {
                        addToken(TokenType.TYPE, word)
                    } else if (LexerTables.KEYWORDS.contains(word)) {
                        addToken(TokenType.KEYWORD, word)
                    } else {
                        addToken(TokenType.IDENTIFIER, word)
                    }
                    break

                case 'Digit':
                    String num = readNumber()
                    addToken(num.contains('.') || num.toLowerCase().contains('e') ? TokenType.FLOAT : TokenType.INT, num)
                    break

                case 'WS':
                    if (!skipWhitespace)  addToken(TokenType.WS, ch)
                    pos++
                    break

                case 'NL':
                    if (!skipWhitespace)  addToken(TokenType.NL, "\\n")
                    lineNumber++
                    pos++
                    break

                case '/':
                    if (pos + 1 < code.length()) {
                        if (code[pos+1] == '/') { // inline comment
                            pos += 2
                            while (pos < code.length() && code[pos] != '\n') pos++
                            lineNumber++
                            pos++
                            break
                        } else if (code[pos+1] == '*') { // multi-line comment
                            pos += 2
                            while (pos + 1 < code.length() && !(code[pos] == '*' && code[pos+1] == '/')) {
                                if (code[pos] == '\n') lineNumber++
                                pos++
                            }
                            pos += 2
                            break
                        } else {
                            addToken(LexerTables.SINGLE_CHAR_TOKENS['/'], '/')
                            pos++
                            break
                        }
                    }
                    break

                case '^':
                    addToken(TokenType.POWER_OP, '^')
                    pos++
                    break

                case ',':
                    addToken(TokenType.COMMA, ',')
                    pos++
                    break

                default:
                    if (LexerTables.SINGLE_CHAR_TOKENS.containsKey(ch)) {
                        addToken(LexerTables.SINGLE_CHAR_TOKENS[ch], ch)
                        pos++
                    } else {
                        addToken(TokenType.ERROR, ch)
                        pos++
                    }
                    break
            }
        }
        return tokens
    }

    private void addToken(TokenType type, String value) {
        tokens << new Token(value, type, lineNumber)
    }

    private void addMultiCharOperator(String ch) {
        if (pos + 1 < code.length()) {
            String next = code[pos+1]
            switch(ch) {
                case '=':
                    if (next == '=') { addToken(TokenType.COMPARE_OP, '=='); pos += 2; return }
                    break
                case '!':
                    if (next == '=') { addToken(TokenType.COMPARE_OP, '!='); pos += 2; return }
                    break
                case '>':
                    if (next == '=') { addToken(TokenType.COMPARE_OP, '>='); pos += 2; return }
                    break
                case '<':
                    if (next == '=') { addToken(TokenType.COMPARE_OP, '<='); pos += 2; return }
                    break
            }
        }
        // if not a multi-character operator, add it as a single-character operator
        addToken(LexerTables.SINGLE_CHAR_TOKENS[ch], ch)
        pos++
    }

    private String readNumber() {
        StringBuilder buffer = new StringBuilder()
        buffer << readWhileClosure { c -> classOfChar(c) in ['Digit','Dot'] }
        if (pos < code.length() && (code[pos] == 'e' || code[pos] == 'E')) {
            buffer << code[pos++]  // add 'e' or 'E'
            if (pos < code.length() && (code[pos] == '+' || code[pos] == '-')) {
                buffer << code[pos++]
            }
            buffer << readWhileClosure { c -> classOfChar(c) == 'Digit' }
        }
        return buffer.toString()
    }

    private String readString() {
        pos++ // skip the opening "
        StringBuilder buffer = new StringBuilder()
        while (pos < code.length() && code[pos] != '"') {
            if (code[pos] == '\n') lineNumber++
            buffer << code[pos++]
        }
        pos++ // skip the closing "
        return buffer.toString()
    }

    // flexible reading of character sequences using a Closure
    private String readWhileClosure(Closure<Boolean> predicate) {
        StringBuilder buffer = new StringBuilder()
        while (pos < code.length() && predicate(code[pos])) {
            buffer << code[pos++]
        }
        return buffer.toString()
    }
}
