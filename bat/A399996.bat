rem This bat calculates a(1) through a(17) of A399996
rem It requires in input 100 files containing the free polyominoes of
rem sizes 1 through 17, in the INPUT folder, with names free1-17-x.txt
rem for x from 0 to 99. See A246521 for the format of the input.
rem It requires some adjustment according to local path conventions
rem The folder D:\Bisect (or whatever replaces it) must already exist.
rem The same goes for the "various" folder.
rem D:\Bisect may require about 400GB of free space.
rem Phase 1: output to disk all the composed polyominoes of size 2n
rem Phase 2: sort and merge the output files
rem Phase 3: perform individual counts
rem Phase 4: sum all the counts
rem
rem Note that Phase 1 counts separately polyominoes of size 2n formed from
rem a single asymmetrical polyomino of size n such that the 2 halves meet at
rem only one edge. These counts go into the optim files and so reduce the
rem amount of data that needs to be sorted.
rem
rem In Phase 1, the "Hundred" filter outputs separate files according to the 
rem last 2 digits of the representation of the composed polyomino. This
rem means that we have to work with a lot of medium sized files instead of
rem 1 enormous one.
rem
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-0.txt hundred:nolineno=true,prefix=D:\Bisect\out-0 INPUT\free1-17-0.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-1.txt hundred:nolineno=true,prefix=D:\Bisect\out-1 INPUT\free1-17-1.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-2.txt hundred:nolineno=true,prefix=D:\Bisect\out-2 INPUT\free1-17-2.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-3.txt hundred:nolineno=true,prefix=D:\Bisect\out-3 INPUT\free1-17-3.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-4.txt hundred:nolineno=true,prefix=D:\Bisect\out-4 INPUT\free1-17-4.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-5.txt hundred:nolineno=true,prefix=D:\Bisect\out-5 INPUT\free1-17-5.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-6.txt hundred:nolineno=true,prefix=D:\Bisect\out-6 INPUT\free1-17-6.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-7.txt hundred:nolineno=true,prefix=D:\Bisect\out-7 INPUT\free1-17-7.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-8.txt hundred:nolineno=true,prefix=D:\Bisect\out-8 INPUT\free1-17-8.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-9.txt hundred:nolineno=true,prefix=D:\Bisect\out-9 INPUT\free1-17-9.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-10.txt hundred:nolineno=true,prefix=D:\Bisect\out-10 INPUT\free1-17-10.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-11.txt hundred:nolineno=true,prefix=D:\Bisect\out-11 INPUT\free1-17-11.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-12.txt hundred:nolineno=true,prefix=D:\Bisect\out-12 INPUT\free1-17-12.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-13.txt hundred:nolineno=true,prefix=D:\Bisect\out-13 INPUT\free1-17-13.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-14.txt hundred:nolineno=true,prefix=D:\Bisect\out-14 INPUT\free1-17-14.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-15.txt hundred:nolineno=true,prefix=D:\Bisect\out-15 INPUT\free1-17-15.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-16.txt hundred:nolineno=true,prefix=D:\Bisect\out-16 INPUT\free1-17-16.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-17.txt hundred:nolineno=true,prefix=D:\Bisect\out-17 INPUT\free1-17-17.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-18.txt hundred:nolineno=true,prefix=D:\Bisect\out-18 INPUT\free1-17-18.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-19.txt hundred:nolineno=true,prefix=D:\Bisect\out-19 INPUT\free1-17-19.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-20.txt hundred:nolineno=true,prefix=D:\Bisect\out-20 INPUT\free1-17-20.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-21.txt hundred:nolineno=true,prefix=D:\Bisect\out-21 INPUT\free1-17-21.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-22.txt hundred:nolineno=true,prefix=D:\Bisect\out-22 INPUT\free1-17-22.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-23.txt hundred:nolineno=true,prefix=D:\Bisect\out-23 INPUT\free1-17-23.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-24.txt hundred:nolineno=true,prefix=D:\Bisect\out-24 INPUT\free1-17-24.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-25.txt hundred:nolineno=true,prefix=D:\Bisect\out-25 INPUT\free1-17-25.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-26.txt hundred:nolineno=true,prefix=D:\Bisect\out-26 INPUT\free1-17-26.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-27.txt hundred:nolineno=true,prefix=D:\Bisect\out-27 INPUT\free1-17-27.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-28.txt hundred:nolineno=true,prefix=D:\Bisect\out-28 INPUT\free1-17-28.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-29.txt hundred:nolineno=true,prefix=D:\Bisect\out-29 INPUT\free1-17-29.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-30.txt hundred:nolineno=true,prefix=D:\Bisect\out-30 INPUT\free1-17-30.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-31.txt hundred:nolineno=true,prefix=D:\Bisect\out-31 INPUT\free1-17-31.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-32.txt hundred:nolineno=true,prefix=D:\Bisect\out-32 INPUT\free1-17-32.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-33.txt hundred:nolineno=true,prefix=D:\Bisect\out-33 INPUT\free1-17-33.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-34.txt hundred:nolineno=true,prefix=D:\Bisect\out-34 INPUT\free1-17-34.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-35.txt hundred:nolineno=true,prefix=D:\Bisect\out-35 INPUT\free1-17-35.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-36.txt hundred:nolineno=true,prefix=D:\Bisect\out-36 INPUT\free1-17-36.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-37.txt hundred:nolineno=true,prefix=D:\Bisect\out-37 INPUT\free1-17-37.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-38.txt hundred:nolineno=true,prefix=D:\Bisect\out-38 INPUT\free1-17-38.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-39.txt hundred:nolineno=true,prefix=D:\Bisect\out-39 INPUT\free1-17-39.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-40.txt hundred:nolineno=true,prefix=D:\Bisect\out-40 INPUT\free1-17-40.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-41.txt hundred:nolineno=true,prefix=D:\Bisect\out-41 INPUT\free1-17-41.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-42.txt hundred:nolineno=true,prefix=D:\Bisect\out-42 INPUT\free1-17-42.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-43.txt hundred:nolineno=true,prefix=D:\Bisect\out-43 INPUT\free1-17-43.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-44.txt hundred:nolineno=true,prefix=D:\Bisect\out-44 INPUT\free1-17-44.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-45.txt hundred:nolineno=true,prefix=D:\Bisect\out-45 INPUT\free1-17-45.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-46.txt hundred:nolineno=true,prefix=D:\Bisect\out-46 INPUT\free1-17-46.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-47.txt hundred:nolineno=true,prefix=D:\Bisect\out-47 INPUT\free1-17-47.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-48.txt hundred:nolineno=true,prefix=D:\Bisect\out-48 INPUT\free1-17-48.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-49.txt hundred:nolineno=true,prefix=D:\Bisect\out-49 INPUT\free1-17-49.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-50.txt hundred:nolineno=true,prefix=D:\Bisect\out-50 INPUT\free1-17-50.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-51.txt hundred:nolineno=true,prefix=D:\Bisect\out-51 INPUT\free1-17-51.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-52.txt hundred:nolineno=true,prefix=D:\Bisect\out-52 INPUT\free1-17-52.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-53.txt hundred:nolineno=true,prefix=D:\Bisect\out-53 INPUT\free1-17-53.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-54.txt hundred:nolineno=true,prefix=D:\Bisect\out-54 INPUT\free1-17-54.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-55.txt hundred:nolineno=true,prefix=D:\Bisect\out-55 INPUT\free1-17-55.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-56.txt hundred:nolineno=true,prefix=D:\Bisect\out-56 INPUT\free1-17-56.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-57.txt hundred:nolineno=true,prefix=D:\Bisect\out-57 INPUT\free1-17-57.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-58.txt hundred:nolineno=true,prefix=D:\Bisect\out-58 INPUT\free1-17-58.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-59.txt hundred:nolineno=true,prefix=D:\Bisect\out-59 INPUT\free1-17-59.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-60.txt hundred:nolineno=true,prefix=D:\Bisect\out-60 INPUT\free1-17-60.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-61.txt hundred:nolineno=true,prefix=D:\Bisect\out-61 INPUT\free1-17-61.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-62.txt hundred:nolineno=true,prefix=D:\Bisect\out-62 INPUT\free1-17-62.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-63.txt hundred:nolineno=true,prefix=D:\Bisect\out-63 INPUT\free1-17-63.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-64.txt hundred:nolineno=true,prefix=D:\Bisect\out-64 INPUT\free1-17-64.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-65.txt hundred:nolineno=true,prefix=D:\Bisect\out-65 INPUT\free1-17-65.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-66.txt hundred:nolineno=true,prefix=D:\Bisect\out-66 INPUT\free1-17-66.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-67.txt hundred:nolineno=true,prefix=D:\Bisect\out-67 INPUT\free1-17-67.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-68.txt hundred:nolineno=true,prefix=D:\Bisect\out-68 INPUT\free1-17-68.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-69.txt hundred:nolineno=true,prefix=D:\Bisect\out-69 INPUT\free1-17-69.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-70.txt hundred:nolineno=true,prefix=D:\Bisect\out-70 INPUT\free1-17-70.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-71.txt hundred:nolineno=true,prefix=D:\Bisect\out-71 INPUT\free1-17-71.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-72.txt hundred:nolineno=true,prefix=D:\Bisect\out-72 INPUT\free1-17-72.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-73.txt hundred:nolineno=true,prefix=D:\Bisect\out-73 INPUT\free1-17-73.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-74.txt hundred:nolineno=true,prefix=D:\Bisect\out-74 INPUT\free1-17-74.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-75.txt hundred:nolineno=true,prefix=D:\Bisect\out-75 INPUT\free1-17-75.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-76.txt hundred:nolineno=true,prefix=D:\Bisect\out-76 INPUT\free1-17-76.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-77.txt hundred:nolineno=true,prefix=D:\Bisect\out-77 INPUT\free1-17-77.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-78.txt hundred:nolineno=true,prefix=D:\Bisect\out-78 INPUT\free1-17-78.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-79.txt hundred:nolineno=true,prefix=D:\Bisect\out-79 INPUT\free1-17-79.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-80.txt hundred:nolineno=true,prefix=D:\Bisect\out-80 INPUT\free1-17-80.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-81.txt hundred:nolineno=true,prefix=D:\Bisect\out-81 INPUT\free1-17-81.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-82.txt hundred:nolineno=true,prefix=D:\Bisect\out-82 INPUT\free1-17-82.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-83.txt hundred:nolineno=true,prefix=D:\Bisect\out-83 INPUT\free1-17-83.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-84.txt hundred:nolineno=true,prefix=D:\Bisect\out-84 INPUT\free1-17-84.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-85.txt hundred:nolineno=true,prefix=D:\Bisect\out-85 INPUT\free1-17-85.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-86.txt hundred:nolineno=true,prefix=D:\Bisect\out-86 INPUT\free1-17-86.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-87.txt hundred:nolineno=true,prefix=D:\Bisect\out-87 INPUT\free1-17-87.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-88.txt hundred:nolineno=true,prefix=D:\Bisect\out-88 INPUT\free1-17-88.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-89.txt hundred:nolineno=true,prefix=D:\Bisect\out-89 INPUT\free1-17-89.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-90.txt hundred:nolineno=true,prefix=D:\Bisect\out-90 INPUT\free1-17-90.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-91.txt hundred:nolineno=true,prefix=D:\Bisect\out-91 INPUT\free1-17-91.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-92.txt hundred:nolineno=true,prefix=D:\Bisect\out-92 INPUT\free1-17-92.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-93.txt hundred:nolineno=true,prefix=D:\Bisect\out-93 INPUT\free1-17-93.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-94.txt hundred:nolineno=true,prefix=D:\Bisect\out-94 INPUT\free1-17-94.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-95.txt hundred:nolineno=true,prefix=D:\Bisect\out-95 INPUT\free1-17-95.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-96.txt hundred:nolineno=true,prefix=D:\Bisect\out-96 INPUT\free1-17-96.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-97.txt hundred:nolineno=true,prefix=D:\Bisect\out-97 INPUT\free1-17-97.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-98.txt hundred:nolineno=true,prefix=D:\Bisect\out-98 INPUT\free1-17-98.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=17 biTrisectable:num=2,optim=true,usefixed=false,optimfile=various\optim-99.txt hundred:nolineno=true,prefix=D:\Bisect\out-99 INPUT\free1-17-99.txt

type D:\Bisect\out-*-0.txt | sort /uniq /o D:\Bisect\sorted-0.txt
type D:\Bisect\out-*-1.txt | sort /uniq /o D:\Bisect\sorted-1.txt
type D:\Bisect\out-*-2.txt | sort /uniq /o D:\Bisect\sorted-2.txt
type D:\Bisect\out-*-3.txt | sort /uniq /o D:\Bisect\sorted-3.txt
type D:\Bisect\out-*-4.txt | sort /uniq /o D:\Bisect\sorted-4.txt
type D:\Bisect\out-*-5.txt | sort /uniq /o D:\Bisect\sorted-5.txt
type D:\Bisect\out-*-6.txt | sort /uniq /o D:\Bisect\sorted-6.txt
type D:\Bisect\out-*-7.txt | sort /uniq /o D:\Bisect\sorted-7.txt
type D:\Bisect\out-*-8.txt | sort /uniq /o D:\Bisect\sorted-8.txt
type D:\Bisect\out-*-9.txt | sort /uniq /o D:\Bisect\sorted-9.txt
type D:\Bisect\out-*-10.txt | sort /uniq /o D:\Bisect\sorted-10.txt
type D:\Bisect\out-*-11.txt | sort /uniq /o D:\Bisect\sorted-11.txt
type D:\Bisect\out-*-12.txt | sort /uniq /o D:\Bisect\sorted-12.txt
type D:\Bisect\out-*-13.txt | sort /uniq /o D:\Bisect\sorted-13.txt
type D:\Bisect\out-*-14.txt | sort /uniq /o D:\Bisect\sorted-14.txt
type D:\Bisect\out-*-15.txt | sort /uniq /o D:\Bisect\sorted-15.txt
type D:\Bisect\out-*-16.txt | sort /uniq /o D:\Bisect\sorted-16.txt
type D:\Bisect\out-*-17.txt | sort /uniq /o D:\Bisect\sorted-17.txt
type D:\Bisect\out-*-18.txt | sort /uniq /o D:\Bisect\sorted-18.txt
type D:\Bisect\out-*-19.txt | sort /uniq /o D:\Bisect\sorted-19.txt
type D:\Bisect\out-*-20.txt | sort /uniq /o D:\Bisect\sorted-20.txt
type D:\Bisect\out-*-21.txt | sort /uniq /o D:\Bisect\sorted-21.txt
type D:\Bisect\out-*-22.txt | sort /uniq /o D:\Bisect\sorted-22.txt
type D:\Bisect\out-*-23.txt | sort /uniq /o D:\Bisect\sorted-23.txt
type D:\Bisect\out-*-24.txt | sort /uniq /o D:\Bisect\sorted-24.txt
type D:\Bisect\out-*-25.txt | sort /uniq /o D:\Bisect\sorted-25.txt
type D:\Bisect\out-*-26.txt | sort /uniq /o D:\Bisect\sorted-26.txt
type D:\Bisect\out-*-27.txt | sort /uniq /o D:\Bisect\sorted-27.txt
type D:\Bisect\out-*-28.txt | sort /uniq /o D:\Bisect\sorted-28.txt
type D:\Bisect\out-*-29.txt | sort /uniq /o D:\Bisect\sorted-29.txt
type D:\Bisect\out-*-30.txt | sort /uniq /o D:\Bisect\sorted-30.txt
type D:\Bisect\out-*-31.txt | sort /uniq /o D:\Bisect\sorted-31.txt
type D:\Bisect\out-*-32.txt | sort /uniq /o D:\Bisect\sorted-32.txt
type D:\Bisect\out-*-33.txt | sort /uniq /o D:\Bisect\sorted-33.txt
type D:\Bisect\out-*-34.txt | sort /uniq /o D:\Bisect\sorted-34.txt
type D:\Bisect\out-*-35.txt | sort /uniq /o D:\Bisect\sorted-35.txt
type D:\Bisect\out-*-36.txt | sort /uniq /o D:\Bisect\sorted-36.txt
type D:\Bisect\out-*-37.txt | sort /uniq /o D:\Bisect\sorted-37.txt
type D:\Bisect\out-*-38.txt | sort /uniq /o D:\Bisect\sorted-38.txt
type D:\Bisect\out-*-39.txt | sort /uniq /o D:\Bisect\sorted-39.txt
type D:\Bisect\out-*-40.txt | sort /uniq /o D:\Bisect\sorted-40.txt
type D:\Bisect\out-*-41.txt | sort /uniq /o D:\Bisect\sorted-41.txt
type D:\Bisect\out-*-42.txt | sort /uniq /o D:\Bisect\sorted-42.txt
type D:\Bisect\out-*-43.txt | sort /uniq /o D:\Bisect\sorted-43.txt
type D:\Bisect\out-*-44.txt | sort /uniq /o D:\Bisect\sorted-44.txt
type D:\Bisect\out-*-45.txt | sort /uniq /o D:\Bisect\sorted-45.txt
type D:\Bisect\out-*-46.txt | sort /uniq /o D:\Bisect\sorted-46.txt
type D:\Bisect\out-*-47.txt | sort /uniq /o D:\Bisect\sorted-47.txt
type D:\Bisect\out-*-48.txt | sort /uniq /o D:\Bisect\sorted-48.txt
type D:\Bisect\out-*-49.txt | sort /uniq /o D:\Bisect\sorted-49.txt
type D:\Bisect\out-*-50.txt | sort /uniq /o D:\Bisect\sorted-50.txt
type D:\Bisect\out-*-51.txt | sort /uniq /o D:\Bisect\sorted-51.txt
type D:\Bisect\out-*-52.txt | sort /uniq /o D:\Bisect\sorted-52.txt
type D:\Bisect\out-*-53.txt | sort /uniq /o D:\Bisect\sorted-53.txt
type D:\Bisect\out-*-54.txt | sort /uniq /o D:\Bisect\sorted-54.txt
type D:\Bisect\out-*-55.txt | sort /uniq /o D:\Bisect\sorted-55.txt
type D:\Bisect\out-*-56.txt | sort /uniq /o D:\Bisect\sorted-56.txt
type D:\Bisect\out-*-57.txt | sort /uniq /o D:\Bisect\sorted-57.txt
type D:\Bisect\out-*-58.txt | sort /uniq /o D:\Bisect\sorted-58.txt
type D:\Bisect\out-*-59.txt | sort /uniq /o D:\Bisect\sorted-59.txt
type D:\Bisect\out-*-60.txt | sort /uniq /o D:\Bisect\sorted-60.txt
type D:\Bisect\out-*-61.txt | sort /uniq /o D:\Bisect\sorted-61.txt
type D:\Bisect\out-*-62.txt | sort /uniq /o D:\Bisect\sorted-62.txt
type D:\Bisect\out-*-63.txt | sort /uniq /o D:\Bisect\sorted-63.txt
type D:\Bisect\out-*-64.txt | sort /uniq /o D:\Bisect\sorted-64.txt
type D:\Bisect\out-*-65.txt | sort /uniq /o D:\Bisect\sorted-65.txt
type D:\Bisect\out-*-66.txt | sort /uniq /o D:\Bisect\sorted-66.txt
type D:\Bisect\out-*-67.txt | sort /uniq /o D:\Bisect\sorted-67.txt
type D:\Bisect\out-*-68.txt | sort /uniq /o D:\Bisect\sorted-68.txt
type D:\Bisect\out-*-69.txt | sort /uniq /o D:\Bisect\sorted-69.txt
type D:\Bisect\out-*-70.txt | sort /uniq /o D:\Bisect\sorted-70.txt
type D:\Bisect\out-*-71.txt | sort /uniq /o D:\Bisect\sorted-71.txt
type D:\Bisect\out-*-72.txt | sort /uniq /o D:\Bisect\sorted-72.txt
type D:\Bisect\out-*-73.txt | sort /uniq /o D:\Bisect\sorted-73.txt
type D:\Bisect\out-*-74.txt | sort /uniq /o D:\Bisect\sorted-74.txt
type D:\Bisect\out-*-75.txt | sort /uniq /o D:\Bisect\sorted-75.txt
type D:\Bisect\out-*-76.txt | sort /uniq /o D:\Bisect\sorted-76.txt
type D:\Bisect\out-*-77.txt | sort /uniq /o D:\Bisect\sorted-77.txt
type D:\Bisect\out-*-78.txt | sort /uniq /o D:\Bisect\sorted-78.txt
type D:\Bisect\out-*-79.txt | sort /uniq /o D:\Bisect\sorted-79.txt
type D:\Bisect\out-*-80.txt | sort /uniq /o D:\Bisect\sorted-80.txt
type D:\Bisect\out-*-81.txt | sort /uniq /o D:\Bisect\sorted-81.txt
type D:\Bisect\out-*-82.txt | sort /uniq /o D:\Bisect\sorted-82.txt
type D:\Bisect\out-*-83.txt | sort /uniq /o D:\Bisect\sorted-83.txt
type D:\Bisect\out-*-84.txt | sort /uniq /o D:\Bisect\sorted-84.txt
type D:\Bisect\out-*-85.txt | sort /uniq /o D:\Bisect\sorted-85.txt
type D:\Bisect\out-*-86.txt | sort /uniq /o D:\Bisect\sorted-86.txt
type D:\Bisect\out-*-87.txt | sort /uniq /o D:\Bisect\sorted-87.txt
type D:\Bisect\out-*-88.txt | sort /uniq /o D:\Bisect\sorted-88.txt
type D:\Bisect\out-*-89.txt | sort /uniq /o D:\Bisect\sorted-89.txt
type D:\Bisect\out-*-90.txt | sort /uniq /o D:\Bisect\sorted-90.txt
type D:\Bisect\out-*-91.txt | sort /uniq /o D:\Bisect\sorted-91.txt
type D:\Bisect\out-*-92.txt | sort /uniq /o D:\Bisect\sorted-92.txt
type D:\Bisect\out-*-93.txt | sort /uniq /o D:\Bisect\sorted-93.txt
type D:\Bisect\out-*-94.txt | sort /uniq /o D:\Bisect\sorted-94.txt
type D:\Bisect\out-*-95.txt | sort /uniq /o D:\Bisect\sorted-95.txt
type D:\Bisect\out-*-96.txt | sort /uniq /o D:\Bisect\sorted-96.txt
type D:\Bisect\out-*-97.txt | sort /uniq /o D:\Bisect\sorted-97.txt
type D:\Bisect\out-*-98.txt | sort /uniq /o D:\Bisect\sorted-98.txt
type D:\Bisect\out-*-99.txt | sort /uniq /o D:\Bisect\sorted-99.txt

java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-0.txt > various\count-0.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-1.txt > various\count-1.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-2.txt > various\count-2.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-3.txt > various\count-3.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-4.txt > various\count-4.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-5.txt > various\count-5.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-6.txt > various\count-6.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-7.txt > various\count-7.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-8.txt > various\count-8.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-9.txt > various\count-9.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-10.txt > various\count-10.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-11.txt > various\count-11.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-12.txt > various\count-12.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-13.txt > various\count-13.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-14.txt > various\count-14.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-15.txt > various\count-15.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-16.txt > various\count-16.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-17.txt > various\count-17.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-18.txt > various\count-18.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-19.txt > various\count-19.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-20.txt > various\count-20.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-21.txt > various\count-21.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-22.txt > various\count-22.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-23.txt > various\count-23.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-24.txt > various\count-24.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-25.txt > various\count-25.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-26.txt > various\count-26.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-27.txt > various\count-27.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-28.txt > various\count-28.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-29.txt > various\count-29.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-30.txt > various\count-30.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-31.txt > various\count-31.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-32.txt > various\count-32.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-33.txt > various\count-33.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-34.txt > various\count-34.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-35.txt > various\count-35.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-36.txt > various\count-36.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-37.txt > various\count-37.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-38.txt > various\count-38.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-39.txt > various\count-39.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-40.txt > various\count-40.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-41.txt > various\count-41.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-42.txt > various\count-42.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-43.txt > various\count-43.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-44.txt > various\count-44.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-45.txt > various\count-45.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-46.txt > various\count-46.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-47.txt > various\count-47.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-48.txt > various\count-48.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-49.txt > various\count-49.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-50.txt > various\count-50.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-51.txt > various\count-51.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-52.txt > various\count-52.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-53.txt > various\count-53.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-54.txt > various\count-54.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-55.txt > various\count-55.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-56.txt > various\count-56.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-57.txt > various\count-57.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-58.txt > various\count-58.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-59.txt > various\count-59.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-60.txt > various\count-60.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-61.txt > various\count-61.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-62.txt > various\count-62.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-63.txt > various\count-63.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-64.txt > various\count-64.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-65.txt > various\count-65.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-66.txt > various\count-66.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-67.txt > various\count-67.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-68.txt > various\count-68.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-69.txt > various\count-69.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-70.txt > various\count-70.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-71.txt > various\count-71.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-72.txt > various\count-72.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-73.txt > various\count-73.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-74.txt > various\count-74.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-75.txt > various\count-75.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-76.txt > various\count-76.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-77.txt > various\count-77.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-78.txt > various\count-78.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-79.txt > various\count-79.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-80.txt > various\count-80.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-81.txt > various\count-81.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-82.txt > various\count-82.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-83.txt > various\count-83.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-84.txt > various\count-84.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-85.txt > various\count-85.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-86.txt > various\count-86.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-87.txt > various\count-87.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-88.txt > various\count-88.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-89.txt > various\count-89.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-90.txt > various\count-90.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-91.txt > various\count-91.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-92.txt > various\count-92.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-93.txt > various\count-93.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-94.txt > various\count-94.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-95.txt > various\count-95.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-96.txt > various\count-96.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-97.txt > various\count-97.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-98.txt > various\count-98.txt
java -jar "dist\PolyUtilGH.jar" stdinToRep:nolineno=true  repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout D:\Bisect\sorted-99.txt > various\count-99.txt

java -jar "dist\PolyUtilGH.jar" stdinToRep add:file=various\optim-0.txt,divisor=2 add:file=various\count-1.txt add:file=various\optim-1.txt,divisor=2 add:file=various\count-2.txt add:file=various\optim-2.txt,divisor=2 add:file=various\count-3.txt add:file=various\optim-3.txt,divisor=2 add:file=various\count-4.txt add:file=various\optim-4.txt,divisor=2 add:file=various\count-5.txt add:file=various\optim-5.txt,divisor=2 add:file=various\count-6.txt add:file=various\optim-6.txt,divisor=2 add:file=various\count-7.txt add:file=various\optim-7.txt,divisor=2 add:file=various\count-8.txt add:file=various\optim-8.txt,divisor=2 add:file=various\count-9.txt add:file=various\optim-9.txt,divisor=2 add:file=various\count-10.txt add:file=various\optim-10.txt,divisor=2 add:file=various\count-11.txt add:file=various\optim-11.txt,divisor=2 add:file=various\count-12.txt add:file=various\optim-12.txt,divisor=2 add:file=various\count-13.txt add:file=various\optim-13.txt,divisor=2 add:file=various\count-14.txt add:file=various\optim-14.txt,divisor=2 add:file=various\count-15.txt add:file=various\optim-15.txt,divisor=2 add:file=various\count-16.txt add:file=various\optim-16.txt,divisor=2 add:file=various\count-17.txt add:file=various\optim-17.txt,divisor=2 add:file=various\count-18.txt add:file=various\optim-18.txt,divisor=2 add:file=various\count-19.txt add:file=various\optim-19.txt,divisor=2 add:file=various\count-20.txt add:file=various\optim-20.txt,divisor=2 add:file=various\count-21.txt add:file=various\optim-21.txt,divisor=2 add:file=various\count-22.txt add:file=various\optim-22.txt,divisor=2 add:file=various\count-23.txt add:file=various\optim-23.txt,divisor=2 add:file=various\count-24.txt add:file=various\optim-24.txt,divisor=2 add:file=various\count-25.txt add:file=various\optim-25.txt,divisor=2 add:file=various\count-26.txt add:file=various\optim-26.txt,divisor=2 add:file=various\count-27.txt add:file=various\optim-27.txt,divisor=2 add:file=various\count-28.txt add:file=various\optim-28.txt,divisor=2 add:file=various\count-29.txt add:file=various\optim-29.txt,divisor=2 add:file=various\count-30.txt add:file=various\optim-30.txt,divisor=2 add:file=various\count-31.txt add:file=various\optim-31.txt,divisor=2 add:file=various\count-32.txt add:file=various\optim-32.txt,divisor=2 add:file=various\count-33.txt add:file=various\optim-33.txt,divisor=2 add:file=various\count-34.txt add:file=various\optim-34.txt,divisor=2 add:file=various\count-35.txt add:file=various\optim-35.txt,divisor=2 add:file=various\count-36.txt add:file=various\optim-36.txt,divisor=2 add:file=various\count-37.txt add:file=various\optim-37.txt,divisor=2 add:file=various\count-38.txt add:file=various\optim-38.txt,divisor=2 add:file=various\count-39.txt add:file=various\optim-39.txt,divisor=2 add:file=various\count-40.txt add:file=various\optim-40.txt,divisor=2 add:file=various\count-41.txt add:file=various\optim-41.txt,divisor=2 add:file=various\count-42.txt add:file=various\optim-42.txt,divisor=2 add:file=various\count-43.txt add:file=various\optim-43.txt,divisor=2 add:file=various\count-44.txt add:file=various\optim-44.txt,divisor=2 add:file=various\count-45.txt add:file=various\optim-45.txt,divisor=2 add:file=various\count-46.txt add:file=various\optim-46.txt,divisor=2 add:file=various\count-47.txt add:file=various\optim-47.txt,divisor=2 add:file=various\count-48.txt add:file=various\optim-48.txt,divisor=2 add:file=various\count-49.txt add:file=various\optim-49.txt,divisor=2 add:file=various\count-50.txt add:file=various\optim-50.txt,divisor=2 add:file=various\count-51.txt add:file=various\optim-51.txt,divisor=2 add:file=various\count-52.txt add:file=various\optim-52.txt,divisor=2 add:file=various\count-53.txt add:file=various\optim-53.txt,divisor=2 add:file=various\count-54.txt add:file=various\optim-54.txt,divisor=2 add:file=various\count-55.txt add:file=various\optim-55.txt,divisor=2 add:file=various\count-56.txt add:file=various\optim-56.txt,divisor=2 add:file=various\count-57.txt add:file=various\optim-57.txt,divisor=2 add:file=various\count-58.txt add:file=various\optim-58.txt,divisor=2 add:file=various\count-59.txt add:file=various\optim-59.txt,divisor=2 add:file=various\count-60.txt add:file=various\optim-60.txt,divisor=2 add:file=various\count-61.txt add:file=various\optim-61.txt,divisor=2 add:file=various\count-62.txt add:file=various\optim-62.txt,divisor=2 add:file=various\count-63.txt add:file=various\optim-63.txt,divisor=2 add:file=various\count-64.txt add:file=various\optim-64.txt,divisor=2 add:file=various\count-65.txt add:file=various\optim-65.txt,divisor=2 add:file=various\count-66.txt add:file=various\optim-66.txt,divisor=2 add:file=various\count-67.txt add:file=various\optim-67.txt,divisor=2 add:file=various\count-68.txt add:file=various\optim-68.txt,divisor=2 add:file=various\count-69.txt add:file=various\optim-69.txt,divisor=2 add:file=various\count-70.txt add:file=various\optim-70.txt,divisor=2 add:file=various\count-71.txt add:file=various\optim-71.txt,divisor=2 add:file=various\count-72.txt add:file=various\optim-72.txt,divisor=2 add:file=various\count-73.txt add:file=various\optim-73.txt,divisor=2 add:file=various\count-74.txt add:file=various\optim-74.txt,divisor=2 add:file=various\count-75.txt add:file=various\optim-75.txt,divisor=2 add:file=various\count-76.txt add:file=various\optim-76.txt,divisor=2 add:file=various\count-77.txt add:file=various\optim-77.txt,divisor=2 add:file=various\count-78.txt add:file=various\optim-78.txt,divisor=2 add:file=various\count-79.txt add:file=various\optim-79.txt,divisor=2 add:file=various\count-80.txt add:file=various\optim-80.txt,divisor=2 add:file=various\count-81.txt add:file=various\optim-81.txt,divisor=2 add:file=various\count-82.txt add:file=various\optim-82.txt,divisor=2 add:file=various\count-83.txt add:file=various\optim-83.txt,divisor=2 add:file=various\count-84.txt add:file=various\optim-84.txt,divisor=2 add:file=various\count-85.txt add:file=various\optim-85.txt,divisor=2 add:file=various\count-86.txt add:file=various\optim-86.txt,divisor=2 add:file=various\count-87.txt add:file=various\optim-87.txt,divisor=2 add:file=various\count-88.txt add:file=various\optim-88.txt,divisor=2 add:file=various\count-89.txt add:file=various\optim-89.txt,divisor=2 add:file=various\count-90.txt add:file=various\optim-90.txt,divisor=2 add:file=various\count-91.txt add:file=various\optim-91.txt,divisor=2 add:file=various\count-92.txt add:file=various\optim-92.txt,divisor=2 add:file=various\count-93.txt add:file=various\optim-93.txt,divisor=2 add:file=various\count-94.txt add:file=various\optim-94.txt,divisor=2 add:file=various\count-95.txt add:file=various\optim-95.txt,divisor=2 add:file=various\count-96.txt add:file=various\optim-96.txt,divisor=2 add:file=various\count-97.txt add:file=various\optim-97.txt,divisor=2 add:file=various\count-98.txt add:file=various\optim-98.txt,divisor=2 add:file=various\count-99.txt add:file=various\optim-99.txt,divisor=2 stdout various\count-0.txt 
pause



