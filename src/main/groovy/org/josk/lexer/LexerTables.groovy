package org.josk.lexer

import org.josk.token.TokenType

class LexerTables {
    static String classOfChar(String ch) {
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) return 'Letter'
        if (ch >= '0' && ch <= '9') return 'Digit'
        if (ch == '.') return 'Dot'
        if (ch == ' ' || ch == '\t') return 'WS'
        if (ch == '\n') return 'NL'
        if (SINGLE_CHAR_TOKENS.containsKey(ch)) return ch.toString()
        return 'Other'
    }

    static final Set<String> KEYWORDS = [
            'loop','do','when','otherwise','show','ask',
//            'yes','no',
            'pin','task','give'
    ] as Set

    static final Map<String, TokenType> SINGLE_CHAR_TOKENS = [
            '+': TokenType.ADD_OP,
            '-': TokenType.ADD_OP,
            '*': TokenType.MULT_OP,
            '/': TokenType.MULT_OP,
            '(': TokenType.PAR_OP,
            ')': TokenType.PAR_OP,
            '{': TokenType.PAR_OP,
            '}': TokenType.PAR_OP,
            '>': TokenType.COMPARE_OP,
            '<': TokenType.COMPARE_OP,
            '=': TokenType.COMPARE_OP,
            '^': TokenType.POWER_OP
    ]
}
