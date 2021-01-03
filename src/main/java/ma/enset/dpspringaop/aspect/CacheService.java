package ma.enset.dpspringaop.aspect;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {
    private Map<String,Object> cache=new HashMap<>();
    public void put(String key,Object object){
        cache.put(key,object);
    }
    public void clear(){
        cache.clear();
    }
    public <T> T get(String key, Class<T> type) {
        return (T) cache.get(key);
    }

    public static String generateKey(String methodName , Object... params) {
        if (params.length == 0) {
            return new Integer(methodName.hashCode()).toString();
        }
        Object[] paramList = new Object[params.length+1];
        paramList[0] = methodName;
        System.arraycopy(params, 0, paramList, 1, params.length);
        int hashCode = Arrays.deepHashCode(paramList);
        return new Integer(hashCode).toString();
    }
}
