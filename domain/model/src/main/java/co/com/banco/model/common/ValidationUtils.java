package co.com.banco.model.common;

import co.com.banco.model.common.ex.BusinessException;

import static co.com.banco.model.common.StringUtils.isEmpty;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void verify(BusinessException.Type error) {
        if (nonNull(error)) {
            throw new BusinessException(error);
        }
    }

    public static BusinessException.Type required(BusinessException.Type[] exceptions, Object[] data) {
        BusinessException.Type required = null;
        for (int i = 0; i < data.length && isNull(required); i++) {
            boolean isRequired = (data[i] instanceof String && isEmpty((String) data[i])) || isNull(data[i]);

            if (isRequired) {
                required = exceptions[i];
            }
        }
        return required;
    }
}
