package ma.enset.dpspringaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
@EnableAspectJAutoProxy
public class DecryptAspect {

    private EncryptionService encryption;

    public DecryptAspect(EncryptionService encryption) {
        this.encryption = encryption;
    }

    @Around(value = "@annotation(decCrypt)",argNames = "proceedingJoinPoint,decCrypt")
    public Object decrypt(ProceedingJoinPoint proceedingJoinPoint, DecCrypt decCrypt) throws Throwable {
        String values[] = decCrypt.values();
        Object returnObject = proceedingJoinPoint.proceed();

        if(returnObject instanceof List || returnObject instanceof ArrayList) {
            List<Object> list = (List<Object>)proceedingJoinPoint.proceed();
            for (Object o : list) {
                if(o != null)
                    encryption.decrypt(o, values);
            }
        } else {
            if(returnObject != null)
                encryption.decrypt(returnObject, values);
        }

        return returnObject;
    }
}
