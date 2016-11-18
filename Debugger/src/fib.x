 program { int x
   int fib(int n, int m) { 
       if (n <= 1) then
          { return 1 }
       else
           { if (n == 2) then
                { return 1 }
             else
                { return fib(n-2,1) + fib(n-1,1) }
           }
   }
   int k    x = 5
   k = write(fib(read(),read()))
   { int x
     x = 7
     x = 8
   }
 }
