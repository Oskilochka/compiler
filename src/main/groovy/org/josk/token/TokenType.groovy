package org.josk.token

enum TokenType {
    IDENTIFIER,   // variables, func
    KEYWORD,      // loop, say etc
    INT,
    FLOAT,
    BOOL,      //  boolean values yes or no
    STRING,
    ASSIGN_OP,    // :=
    ADD_OP,       // + or -
    MULT_OP,      // * or /
    POWER_OP,   // (^)
    PAR_OP,       // (, ), {, }
    WS,           // whitespaces, tabulation
    NL,           // new line
    COMPARE_OP,
    ERROR        // lex analyze errors
}
