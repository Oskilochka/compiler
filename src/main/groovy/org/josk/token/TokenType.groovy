package org.josk.token

enum TokenType {
    IDENTIFIER,   // variables, func
    KEYWORD,      // loop, say etc
    TYPE,         // INT, FLOAT, BOOL, STRING
    INT,
    FLOAT,
    BOOL,         // yes/no (boolean literals)
    STRING,
    ASSIGN_OP,    // :=
    ADD_OP,       // +, -
    MULT_OP,      // *, /
    POWER_OP,     // ^
    PAR_OP,       // (, ), {, }
    COMMA,        // ,
    COLON,        // :
    WS,           // whitespaces, tab
    NL,           // new line
    COMPARE_OP,   // ==, !=, >, <, >=, <=
    ERROR         // lex analyze errors
}
