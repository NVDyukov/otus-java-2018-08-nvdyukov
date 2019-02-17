package ru.otus.reflect.types;

public enum ValidClassType {
    INT {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(int.class) || clazz.isAssignableFrom(Integer.class);
        }
    },
    LONG {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(long.class) || clazz.isAssignableFrom(Long.class);
        }
    },
    SHORT {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(short.class) || clazz.isAssignableFrom(Short.class);
        }
    },
    FLOAT {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(float.class) || clazz.isAssignableFrom(Float.class);
        }
    },
    DOUBLE {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(double.class) || clazz.isAssignableFrom(Double.class);
        }
    },
    BYTE {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(byte.class) || clazz.isAssignableFrom(Byte.class);
        }
    },
    BOOLEAN {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class);
        }
    },
    CHARACTER {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(char.class) || clazz.isAssignableFrom(Character.class);
        }
    },
    STRING {
        @Override
        public boolean isType(Class<?> clazz) {
            return clazz.isAssignableFrom(String.class);
        }
    };

    public abstract boolean isType(Class<?> clazz);

    public static boolean isContainsType(Class<?> clazz) {
        ValidClassType[] values = values();
        for (ValidClassType e : values) {
            if (e.isType(clazz))
                return true;
        }
        return false;
    }

    public static boolean isCharacterOrString(Object object) {
        return STRING.isType(object.getClass()) || CHARACTER.isType(object.getClass());
    }
}
