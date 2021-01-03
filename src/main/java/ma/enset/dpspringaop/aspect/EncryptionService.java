package ma.enset.dpspringaop.aspect;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class EncryptionService {
    private PooledPBEStringEncryptor encryptor;
    private EncryptionConfig config;
    public EncryptionService(EncryptionConfig config) {
        this.config=config;
        encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(config.getPoolSize());
        encryptor.setPassword(config.getPassword());
    }
    public String encrypt(String plainText){
        return encryptor.encrypt(plainText);
    }
    public void decrypt(Object returnObject, String values[]) throws Exception {
        Class<?> clazz = returnObject.getClass();
        for (String value : values) {
            Field field = clazz.getDeclaredField(value);
            field.setAccessible(true);
            Object fieldValue = field.get(returnObject);
            if(fieldValue != null)
                field.set(returnObject, decrypt(fieldValue.toString()));
        }
    }

    private String decrypt(String encryptedString) {
        try {
            return encryptor.decrypt(encryptedString);
        } catch (Exception ex) {
            return "==FAILED DECRYPT==";
        }
    }
}
