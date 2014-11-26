package ru.oxbao.sensor_test;

import java.util.Arrays;

public class TestData {
    public double[] XAxis;
    public double[] YAxis;
    public double[] ZAxis;
    public long[] TimeInMilliseconds;

    public TestData(int sizeArray) {
        this.XAxis = new double[sizeArray];
        this.YAxis = new double[sizeArray];
        this.ZAxis = new double[sizeArray];
        this.TimeInMilliseconds = new long[sizeArray];
    }

    @Override
    public String toString() {
        return "TestData{" +
                "XAxis=" + Arrays.toString(XAxis) +
                ", YAxis=" + Arrays.toString(YAxis) +
                ", ZAxis=" + Arrays.toString(ZAxis) +
                ", TimeInMilliseconds=" + Arrays.toString(TimeInMilliseconds) +
                '}';
    }
}
