package ma.enset.dpspringaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Aspect
@EnableAspectJAutoProxy
public class LogAspect {
    @Around("@annotation(MyLog)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result;
        Date d1 = new Date();
        System.out.println("Before ...." + d1);
        result = joinPoint.proceed();
        Date d2 = new Date();
        System.out.println("After .... " + d2);
        System.out.println("Execution Duration : "+(d2.getTime()-d1.getTime()));
        return result;
    }

}
