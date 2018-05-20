# javasist 总结

## 性能对比 http://javatar.iteye.com/blog/814426

1. ASM和JAVAASSIST字节码生成方式不相上下，都很快，是CGLIB的5倍。 
2. CGLIB次之，是JDK自带的两倍。 
3. JDK自带的再次之，因JDK1.6对动态代理做了优化，如果用低版本JDK更慢，要注意的是JDK也是通过字节码生成来实现动态代理的，而不是反射。 
4. JAVAASSIST提供者动态代理接口最慢，比JDK自带的还慢。 
(这也是为什么网上有人说JAVAASSIST比JDK还慢的原因，用JAVAASSIST最好别用它提供的动态代理接口，而可以考虑用它的字节码生成方式) 


## 结论
1. 动态代理首选javassist的字节码生成实现。
2. 其次用CGLIB。
3. 不建议用JDK和javassist的动态代理了。
