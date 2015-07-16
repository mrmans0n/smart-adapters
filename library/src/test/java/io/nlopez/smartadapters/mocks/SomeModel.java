package io.nlopez.smartadapters.mocks;

/**
 * Created by mrm on 16/7/15.
 */
public class SomeModel {

    private int a;

    public SomeModel() {
        a = -1;
    }

    public SomeModel(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public static int staticZero() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SomeModel)) return false;

        SomeModel someModel = (SomeModel) o;

        return a == someModel.a;

    }

    @Override
    public int hashCode() {
        return a;
    }
}
