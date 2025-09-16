package org.josk.token

enum TokenType {
    IDENTIFIER,   // назви змінних, функцій
    KEYWORD,      // зарезервовані слова Joovy: loop, do, if, say, ...
    INT,          // цілі числа, наприклад 123
    FLOAT,        // дійсні числа, наприклад 3.14
    ASSIGN_OP,    // оператор присвоєння :=
    ADD_OP,       // + та -
    MULT_OP,      // * та /
    PAR_OP,       // дужки: (, ), {, }
    WS,           // пробіли та табуляції
    NL,           // новий рядок
    ERROR,         // помилки лексичного аналізу
    COMPARE_OP,
    BOOL,      // логічні значення yes/no
    STRING,    // рядковий тип
    POWER_OP   // піднесення до степеня (^)
}
