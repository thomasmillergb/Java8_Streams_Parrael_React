# Java8_Streams_Parrael_React
This is a home project testing new libraries and functions of Java 8.

The core part of the project is multi-threading and how java 8 handles parallel streams.

## Findings
- Multi thread is very bad if each thread is computing very simple logical task, this is due to Java taken more time spreading the task across all threads
and putting them back together again vs just do the simple logic processing on one core. So if there were 1millon thread it would still take much longer to compute
then one core
- The real befit of Multi threading is if it takes a long time to compute a function, this can cut down the time process by the amount of thread aviable. To simulate this i have put a sleep time on the transaction.
- There is one edge case with what i said above, if the object was to fail fast and it was likely to fail at a start of a operation rather then the end, then
the first statement becomes true again. This is because it has once again took more time to split the processing and reduce the findings then just doing the task on a
single thread.

## Summary
There is no one side fits all, as with everything with programing, so by guildlines for my self are if i have a very big list which needs computation on every item
i would only parallel it if it took longer then 10ns. The reason i say 10ns is because i found that you could speed the computing my 10X. If the compute task was sub 1ns
then parrallel would become extremely inefficient with more threads.