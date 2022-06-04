(KBDCHECK)
   @KBD
   D=M
   @value
   M=-1
   @PRINT
   D;JNE
   @value
   M=0
   @PRINT
   D;JEQ

(PRINT)
   @COUNT
   M=1
   @SCREEN
   D=A
   @address  
   M=D       //address = SCREEN (16384)

(SETVERTICAL)
   @256 
   D=A
   @verticalcount 
   M=D       //verticalcount = 256

(CHECKCOUNTER)       
   @verticalcount  
   D=M       // D hold the value of verticalcount
   @SETCOUNTER 
    D;JGT    // Jumps to progc if verticalcount > 0
   @CHANGEADDRESS
    0;JMP    //  Jumps to cont always

(SETCOUNTER)
   @8        // 8 registers horizontal for each block 
   D=A
   @horizontalcount // horizontalcount = 8
   M=D

(DRAW) // Draws Horizontal line 
   @value
   D =M
   @address  
   A                      =M
   M=D       //RAM[address] = -1
   @address
   M=M+1     //Incrementing value  of address by one
   @horizontalcount 
   MD=M-1    //Decrementing horizontalcount
   @DRAW
   D;JGT     //Go to DRAW if horizontalcount > 0

(NEXTBLOCK)
   @address //now the address will be at last register of block
   D=M
   @8       
   D=D+A    // Adding 8 to the address
   @address
   M=D      // address += 8 (first register of next block)
   @verticalcount 
   M=M-1   //Decrementing value of verticalcount
   @CHECKCOUNTER
   0;JEQ     //Jump to LOOP if verticalcount > 0 jump


(CHANGEADDRESS)
   @20488    // now screen address is 20488
   D=A
   @address  //address = 20488
   M=D
   @COUNT
   MD=M-1
   @SETVERTICAL
   D;JEQ
   @KBDCHECK
   0;JEQ
