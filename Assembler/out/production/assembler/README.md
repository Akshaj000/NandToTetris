## 21AIE112: Elements of Computing Part 21
### Assignment 1: Assembler

<pre>
src
.
├── asmfiles
│   ├── Add.asm
│   ├── GroupOne.asm
│   ├── Max.asm
│   └── MaxL.asm
├── converters
│   ├── Akshaj_21009_ConvertAInstruction.java
│   ├── Akshaj_21009_ConvertCInstruction.java
│   └── Constants.java
├── generatedoutputs
│   ├── Add
│   │   ├── Add_generate_a_instructions.asm
│   │   ├── Add_generate_c_instructions.asm
│   │   ├── Add_generated_instructions.asm
│   │   ├── Add_no_whitespace.asm
│   │   ├── Add_symbolsandvariables.asm
│   │   └── Add_symboltable.asm
│   ├── groupOne
│   │   ├── GroupOne_generate_a_instructions.asm
│   │   ├── GroupOne_generate_c_instructions.asm
│   │   ├── GroupOne_generated_instructions.asm
│   │   ├── GroupOne_no_whitespace.asm
│   │   ├── GroupOne_symbolsandvariables.asm
│   │   └── GroupOne_symboltable.asm
│   ├── max
│   │   ├── Max_generate_a_instructions.asm
│   │   ├── Max_generate_c_instructions.asm
│   │   ├── Max_generated_instructions.asm
│   │   ├── Max_no_whitespace.asm
│   │   ├── Max_symbolsandvariables.asm
│   │   └── Max_symboltable.asm
│   └── maxL
│       ├── MaxL_generate_a_instructions.asm
│       ├── MaxL_generate_c_instructions.asm
│       ├── MaxL_generated_instructions.asm
│       ├── MaxL_no_whitespace.asm
│       ├── MaxL_symbolsandvariables.asm
│       └── MaxL_symboltable.asm
├── handlers
│   ├── Akshaj_21009_IdentifyLabelsAndVariables.java
│   ├── Akshaj_21009_NoWhiteSpace.java
│   ├── Akshaj_21009_TableGenerator.java
│   └── FileHandler.java
├── main.java
└── README.md

</pre>

All asm files which are generated are pasted inside generatedoutputs directory. converters contains code used to convert A and C instruction. Handlers contains validators and file handlers. main.java is the driver class. Each files is generated based on the question.

Thank you.

Submitted by,  
Akshaj.S.R  
21009