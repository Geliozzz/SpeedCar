package ru.oxbao.sensor_test;

import java.util.Arrays;

public class TestData {
    private float[] axisX;
    private float[] axisY;
    private float[] axisZ;
    private long[] time;
    public TestData(int cnt) {
        axisX = new float[cnt];
        axisY = new float[cnt];
        axisZ = new float[cnt];
        time = new long[cnt];
    }

    public void setAxisX(float axisX, int i) {
        this.axisX[i] = axisX;
    }

    public void setAxisY(float axisY, int i) {
        this.axisY[i] = axisY;
    }

    public void setAxisZ(float axisZ, int i) {
        this.axisZ[i] = axisZ;
    }

    public void setTime(long time, int i) {
        this.time[i] = time;
    }

    public TestActivity.resultTest analyse(){
        return TestActivity.resultTest.GOOD;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "axisX=" + Arrays.toString(axisX) +
                ", axisY=" + Arrays.toString(axisY) +
                ", axisZ=" + Arrays.toString(axisZ) +
                ", time=" + Arrays.toString(time) +
                '}';
    }
}
