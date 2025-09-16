package org.josk.token

enum TokenType {
    IDENTIFIER,   // variables, func
    KEYWORD,      // loop, say etc
    INT,
    FLOAT,
    ASSIGN_OP,    // :=
    ADD_OP,       // + or -
    MULT_OP,      // * or /
    PAR_OP,       // (, ), {, }
    WS,           // whitespaces, tabulation
    NL,           // new line
    ERROR,        // lex analyze errors
    COMPARE_OP,
    BOOL,      //  boolean values yes or no
    STRING,
    POWER_OP   // (^)
}
