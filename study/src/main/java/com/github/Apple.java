package com.github;


import java.lang.reflect.Field;

public class Apple {

    @FruitName("大苹果")
    private String name;
    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String color;
    @FruitProvider(name = "海南苹果", address = "海南三亚")
    private Provider provider;

    public class Provider {
        String name;
        String address;

        @Override
        public String toString() {
            return "Provider{" +
                    "name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }

    public Apple() {
        Class<Apple> appleClass = Apple.class;
        Field[] fields = appleClass.getDeclaredFields();
        for(Field field : fields) {
            if(field.isAnnotationPresent(FruitName.class)) {
                this.name = field.getAnnotation(FruitName.class).value();
            } else if(field.isAnnotationPresent(FruitColor.class)) {
                this.color = field.getAnnotation(FruitColor.class).fruitColor().toString();
            } else if(field.isAnnotationPresent(FruitProvider.class)) {
                Provider provider = new Provider();
                provider.name = field.getAnnotation(FruitProvider.class).name();
                provider.address = field.getAnnotation(FruitProvider.class).address();
                this.provider = provider;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(new Apple());
    }

    public Apple(String name, String color, Provider provider) {
        this.name = name;
        this.color = color;
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", " + provider +
                '}';
    }
}
