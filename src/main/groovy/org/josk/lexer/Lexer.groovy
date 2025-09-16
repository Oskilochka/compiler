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
            if (pos + 1 < code.length() && code[pos] == ':' && code[pos+1] == '=') {
                addToken(TokenType.ASSIGN_OP, ":=")
                pos += 2
                continue
            }

            String ch = code[pos]
            String cls = classOfChar(ch)

            switch(cls) {
                case 'Letter':
                    String word = readWhile(['Letter', 'Digit'])
                    addToken(isKeyword(word) ? TokenType.KEYWORD : TokenType.IDENTIFIER, word)
                    break

                case 'Digit':
                    String num = readWhile(['Digit','Dot'])
                    addToken(num.contains('.') ? TokenType.FLOAT : TokenType.INT, num)
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

                case ':':
                    if (pos + 1 < code.length() && code[pos+1] == '=') {
                        addToken(TokenType.ASSIGN_OP, ":=")
                        pos += 2
                    } else {
                        addToken(TokenType.ERROR, ":")
                        pos++
                    }
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

    private String readWhile(List<String> validClasses) {
        StringBuilder buffer = new StringBuilder()
        while (pos < code.length() && classOfChar(code[pos]) in validClasses) {
            buffer << code[pos++]
        }
        return buffer.toString()
    }

    static boolean isKeyword(String word) {
        LexerTables.KEYWORDS.contains(word)
    }
}
