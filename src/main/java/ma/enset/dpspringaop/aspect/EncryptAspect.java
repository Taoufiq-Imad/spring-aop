package ma.enset.dpspringaop.aspect;

import ma.enset.dpspringaop.Contrat;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;


@Component
@Aspect
@EnableAspectJAutoProxy
public class EncryptAspect {
    private EncryptionService encryption;

    public EncryptAspect(EncryptionService encryption) {
        this.encryption = encryption;
    }

    @Before(value = "@annotation(encCrypt)",argNames = "pjp,encCrypt")
    public Object decrypt(JoinPoint pjp, EncCrypt encCrypt) throws Throwable {
        String values[] = encCrypt.fields();
        MethodSignature signature = (MethodSignature) pjp.getSignature();

        // encrypt first param only
        Class<?> clazz = signature.getParameterTypes()[0];
        Object param = pjp.getArgs()[0];

        for (String value : values) {
            Field field = clazz.getDeclaredField(value);
            field.setAccessible(true);
            Object fieldValue = field.get(param);
            if(fieldValue != null)
                field.set(param, encryption.encrypt(fieldValue.toString()));
        }

        return param;
    }
}
