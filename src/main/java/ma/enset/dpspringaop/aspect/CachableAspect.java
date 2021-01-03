package ma.enset.dpspringaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
@EnableAspectJAutoProxy
public class CachableAspect {
    @Autowired
    private CacheService cacheService;

    @Around("@annotation(Cachable)")
    public Object cacheTTL(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = getCurrentMethod(joinPoint);
        Object[] parameters = joinPoint.getArgs();
        String key = CacheService.generateKey(method.getName(),parameters);
        Object returnObject = cacheService.get(key, method.getReturnType());
        if (returnObject != null){
            return returnObject;
        }
        returnObject = joinPoint.proceed(parameters);
        cacheService.put(key, returnObject);

        return returnObject;
    }

    private Method getCurrentMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
